package com.example.parkinglot.dto;

import com.example.parkinglot.model.enums.VehicleType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingRuleRequest {
    private VehicleType vehicleType;
    private Integer freeMinutes;
    private BigDecimal hourlyRate;
    private BigDecimal maxCharge;
    private String note;
}