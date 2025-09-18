package com.example.parkinglot.model;


import com.example.parkinglot.model.enums.SlotStatus;
import com.example.parkinglot.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "parking_slot",
uniqueConstraints = {@UniqueConstraint(columnNames = {"floor_id", "slot_number"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSlot {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Column(name = "slot_number", nullable = false)
private String slotNumber;


@Enumerated(EnumType.STRING)
@Column(nullable = false)
private VehicleType type;


@Enumerated(EnumType.STRING)
@Column(nullable = false)
private SlotStatus status;


// additional metadata like size or priority can be added later


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "floor_id")
private Floor floor;


// optimistic lock version (helps for optimistic concurrency control)
@Version
private Long version;
}