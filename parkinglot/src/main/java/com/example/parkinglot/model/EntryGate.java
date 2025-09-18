package com.example.parkinglot.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "entry_gate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryGate {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Column(nullable = false)
private String name;


// optional: gate belongs to a specific floor; null means lot-level
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "floor_id")
private Floor floor;


// optional tag / coordinate string for 'nearest' strategy
private String locationTag;
}