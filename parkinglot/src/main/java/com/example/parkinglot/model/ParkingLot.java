package com.example.parkinglot.model;


import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "parking_lot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLot {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Column(nullable = false)
private String name;


private String location;


@OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, orphanRemoval = true)
@Builder.Default
private List<Floor> floors = new ArrayList<>();
}