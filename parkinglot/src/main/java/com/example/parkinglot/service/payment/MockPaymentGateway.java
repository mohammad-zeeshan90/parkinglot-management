package com.example.parkinglot.service.payment;


import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.UUID;


/**
* Mock gateway: for assignment purposes it always returns success.
* You can extend this to simulate failures.
*/
@Component
public class MockPaymentGateway implements PaymentGateway {
@Override
public boolean charge(String transactionRef, BigDecimal amount) {
// For assignment keep it deterministic success. If you want to simulate failure,
// change logic here (e.g., based on amount or random generator).
return true;
}
}