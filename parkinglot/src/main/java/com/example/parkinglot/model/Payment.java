package com.example.parkinglot.model;


import com.example.parkinglot.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


// one-to-one with ticket: one payment per exit (for assignment simplicity)
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "ticket_id", nullable = false, unique = true)
private Ticket ticket;


@Column(nullable = false)
private BigDecimal amount;


@Enumerated(EnumType.STRING)
@Column(nullable = false)
private PaymentStatus status;


private LocalDateTime timestamp;


// mock transaction/reference id
private String transactionRef;
}