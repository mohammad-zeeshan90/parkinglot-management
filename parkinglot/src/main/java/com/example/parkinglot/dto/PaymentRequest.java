package com.example.parkinglot.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private BigDecimal amount;
    private String paymentMethod; // e.g. CARD, CASH (for demo)
}