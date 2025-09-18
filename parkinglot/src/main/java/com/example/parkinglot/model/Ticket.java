package com.example.parkinglot.model;


import com.example.parkinglot.model.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


// human-friendly ticket number (UUID string generated at creation)
@Column(name = "ticket_no", nullable = false, unique = true)
private String ticketNo;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "vehicle_id", nullable = false)
private Vehicle vehicle;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "slot_id", nullable = false)
private ParkingSlot slot;


@Column(nullable = false)
private LocalDateTime entryTime;


private LocalDateTime exitTime;


@Enumerated(EnumType.STRING)
@Column(nullable = false)
private TicketStatus status;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "created_by_gate_id")
private EntryGate createdByGate;


// calculated amount (nullable until exit is requested)
private BigDecimal amount;


@Version
private Long version;
}