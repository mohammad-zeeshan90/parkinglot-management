package com.example.parkinglot.service.payment;


import java.math.BigDecimal;


/**
* Simple payment gateway contract for mocking/simulating payments.
*/
public interface PaymentGateway {
boolean charge(String transactionRef, BigDecimal amount);
}