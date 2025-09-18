package com.example.parkinglot.service.payment;


import com.example.parkinglot.model.Payment;


import java.math.BigDecimal;


public interface PaymentService {
Payment payForTicket(Long ticketId, BigDecimal amount);
}