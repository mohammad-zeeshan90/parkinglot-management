package com.example.parkinglot.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExitInfo {
    private Ticket ticket;
    private BigDecimal fee;
}