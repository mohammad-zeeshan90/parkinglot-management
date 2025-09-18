package com.example.parkinglot.model;


import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "floor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Floor {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Column(nullable = false)
private Integer floorNumber;


private Integer capacity;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "parking_lot_id")
private ParkingLot parkingLot;


@OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true)
@Builder.Default
private List<ParkingSlot> slots = new ArrayList<>();
}