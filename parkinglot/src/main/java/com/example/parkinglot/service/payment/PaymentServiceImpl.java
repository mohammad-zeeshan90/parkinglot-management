package com.example.parkinglot.service.payment;


import com.example.parkinglot.exception.DomainExceptions.InvalidOperationException;
import com.example.parkinglot.exception.DomainExceptions.PaymentFailedException;
import com.example.parkinglot.model.*;
import com.example.parkinglot.model.enums.PaymentStatus;
import com.example.parkinglot.model.enums.SlotStatus;
import com.example.parkinglot.model.enums.TicketStatus;
import com.example.parkinglot.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);


private final TicketRepository ticketRepository;
private final PaymentRepository paymentRepository;
private final ParkingSlotRepository slotRepository;
private final PaymentGateway gateway;


@Override
@Transactional
public Payment payForTicket(Long ticketId, BigDecimal amount) {
Ticket ticket = ticketRepository.findById(ticketId)
.orElseThrow(() -> new InvalidOperationException("Ticket not found: " + ticketId));


if (ticket.getStatus() != TicketStatus.PENDING_PAYMENT) {
throw new InvalidOperationException("Ticket not ready for payment. Current status: " + ticket.getStatus());
}

// create payment record INITIATED
    Payment payment = Payment.builder()
            .ticket(ticket)
            .amount(amount)
            .status(PaymentStatus.INITIATED)
            .timestamp(LocalDateTime.now())
            .transactionRef(UUID.randomUUID().toString())
            .build();


    payment = paymentRepository.save(payment);


// call gateway (mock) - since it's local and fast we perform inside tx for assignment
    boolean success = gateway.charge(payment.getTransactionRef(), amount);


    if (!success) {
        payment.setStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);
        log.warn("Payment failed for ticket={}, tx={}", ticketId, payment.getTransactionRef());
        throw new PaymentFailedException("Payment gateway reported failure");
    }


// success: update payment, ticket, free slot
    payment.setStatus(PaymentStatus.SUCCESS);
    paymentRepository.save(payment);


    ticket.setStatus(TicketStatus.PAID);
    ticket.setExitTime(LocalDateTime.now());
    ticketRepository.save(ticket);


    ParkingSlot slot = ticket.getSlot();
    slot.setStatus(SlotStatus.FREE);
    slotRepository.save(slot);


    log.info("Payment successful tx={}, ticket={}, freed slot={}", payment.getTransactionRef(), ticketId, slot.getId());


    return payment;
}
}