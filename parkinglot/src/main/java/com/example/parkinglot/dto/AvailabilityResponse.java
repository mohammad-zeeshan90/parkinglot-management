package com.example.parkinglot.dto;

import com.example.parkinglot.model.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityResponse {
    private VehicleType vehicleType;
    private Long availableSlots;
}