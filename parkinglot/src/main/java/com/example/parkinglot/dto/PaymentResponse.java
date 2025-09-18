package com.example.parkinglot.dto;

import com.example.parkinglot.model.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long ticketId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime timestamp;
}