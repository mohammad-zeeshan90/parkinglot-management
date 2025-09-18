package com.example.parkinglot.service.ticket;


import com.example.parkinglot.exception.DomainExceptions.DuplicateVehicleEntryException;
import com.example.parkinglot.exception.DomainExceptions.TicketNotFoundException;
import com.example.parkinglot.model.*;
import com.example.parkinglot.model.enums.TicketStatus;
import com.example.parkinglot.model.enums.VehicleType;
import com.example.parkinglot.repository.*;
import com.example.parkinglot.service.slot.SlotAllocationService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {


private final TicketRepository ticketRepository;
private final VehicleRepository vehicleRepository;
private final EntryGateRepository gateRepository;
private final SlotAllocationService allocationService;
private final ParkingSlotRepository slotRepository;
private final PricingRuleRepository pricingRuleRepository;
private final PaymentRepository paymentRepository;
private final EntityManager em;


    @Override
    @Transactional
    public Ticket createEntryTicket(String plateNo, VehicleType type, Long entryGateId) {
// 1. duplicate active ticket check
        ticketRepository.findByVehicle_PlateNoAndStatus(plateNo, TicketStatus.ACTIVE)
                .ifPresent(t -> { throw new
                        DuplicateVehicleEntryException("Vehicle already parked with ticket: " + t.getTicketNo()); });


// 2. ensure vehicle exists
        Vehicle vehicle = vehicleRepository.findByPlateNo(plateNo)
                .orElseGet(() -> vehicleRepository.save(Vehicle.builder()
                        .plateNo(plateNo)
                        .type(type)
                        .build()));


// 3. get entry gate
        EntryGate gate = gateRepository.findById(entryGateId).orElse(null);


// 4. allocate slot (this method will lock and mark slot OCCUPIED)
        ParkingSlot allocated = allocationService.allocateSlot(type, gate);


// 5. create ticket
        Ticket ticket = Ticket.builder()
                .ticketNo(UUID.randomUUID().toString())
                .vehicle(vehicle)
                .slot(allocated)
                .entryTime(LocalDateTime.now())
                .status(TicketStatus.ACTIVE)
                .createdByGate(gate)
                .build();


        ticket = ticketRepository.save(ticket);
// ensure the slot relation is maintained (slot already marked OCCUPIED)
        return ticket;
    }

    @Override
    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket not found: " + ticketId));
    }
    @Override
    @Transactional
    public ExitInfo  prepareExit(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket not found: " + ticketId));

        if (ticket.getStatus() == TicketStatus.PENDING_PAYMENT) {
            // Already prepared exit, return existing info
            return ExitInfo.builder()
                    .ticket(ticket)
                    .fee(ticket.getAmount())
                    .build();
        }
        if (ticket.getStatus() != TicketStatus.ACTIVE) {
            throw new IllegalStateException("Ticket is not active: " + ticket.getStatus());
        }


// calculate charge
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(ticket.getEntryTime(), now);
        long minutes = Math.max(0, duration.toMinutes());


        PricingRule rule = pricingRuleRepository.findByVehicleType(ticket.getVehicle().getType())
                .orElse(PricingRule.builder().freeMinutes(0).hourlyRate(BigDecimal.ZERO).build());


        long billableMinutes = Math.max(0, minutes - (rule.getFreeMinutes() == null ? 0 : rule.getFreeMinutes()));
// Round up hours
        long hours = (billableMinutes + 59) / 60;
        BigDecimal amount = rule.getHourlyRate() == null ? BigDecimal.ZERO : rule.getHourlyRate().multiply(BigDecimal.valueOf(hours));


        if (rule.getMaxCharge() != null && rule.getMaxCharge().compareTo(BigDecimal.ZERO) > 0) {
            amount = amount.min(rule.getMaxCharge());
        }


// set ticket to pending payment and record amount
        ticket.setAmount(amount);
        ticket.setStatus(TicketStatus.PENDING_PAYMENT);
        ticket.setExitTime(now);
        ticketRepository.save(ticket);


        return ExitInfo.builder()
                .ticket(ticket)
                .fee(amount)
                .build();
    }
}


