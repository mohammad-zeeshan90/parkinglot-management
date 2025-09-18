package com.example.parkinglot.dto;

import com.example.parkinglot.model.enums.VehicleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleEntryRequest {
    private String plateNo;
    @JsonProperty("type")
    private VehicleType vehicleType;
    private Long entryGateId;
}