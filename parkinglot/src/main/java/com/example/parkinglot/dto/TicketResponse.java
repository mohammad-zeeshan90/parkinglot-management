package com.example.parkinglot.dto;

import com.example.parkinglot.model.enums.TicketStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
    private Long ticketId;
    private String plateNo;
    private Long slotId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private TicketStatus status;
}