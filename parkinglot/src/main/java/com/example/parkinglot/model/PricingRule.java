package com.example.parkinglot.model;


import com.example.parkinglot.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;


@Entity
@Table(name = "pricing_rule", uniqueConstraints = {@UniqueConstraint(columnNames = {"vehicle_type"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingRule {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Enumerated(EnumType.STRING)
@Column(name = "vehicle_type", nullable = false)
private VehicleType vehicleType;


// e.g. first N minutes free
private Integer freeMinutes;


// hourly rate after free period
private BigDecimal hourlyRate;


// optional cap on charges
private BigDecimal maxCharge;


private String note;
}