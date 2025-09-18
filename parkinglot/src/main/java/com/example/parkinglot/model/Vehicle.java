package com.example.parkinglot.model;


import com.example.parkinglot.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "vehicle", indexes = {@Index(name = "idx_vehicle_plate", columnList = "plate_no")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Column(name = "plate_no", nullable = false, unique = true)
private String plateNo;


@Enumerated(EnumType.STRING)
@Column(nullable = false)
private VehicleType type;


private String ownerName;


private String ownerContact;
}