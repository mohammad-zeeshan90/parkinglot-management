package com.example.parkinglot.dto;

import com.example.parkinglot.model.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class VehicleExitResponse {
    private Long ticketId;
    private String plateNo;
    private String slotId;
    private BigDecimal amountDue;
    private LocalDateTime exitTime;
    private TicketStatus status;
}