package com.example.parkinglot.model;

import com.example.parkinglot.model.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotAvailability {
    private VehicleType vehicleType;
    private Long availableCount;
}