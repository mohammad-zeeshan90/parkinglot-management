package com.example.parkinglot.controller;

import com.example.parkinglot.dto.*;
import com.example.parkinglot.model.ExitInfo;
import com.example.parkinglot.model.Ticket;
import com.example.parkinglot.model.enums.VehicleType;
import com.example.parkinglot.service.SlotService;
import com.example.parkinglot.service.ticket.TicketService;
import com.example.parkinglot.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class ParkingController {

    private final TicketService ticketService;
    private final PaymentService paymentService;
    private final SlotService slotService;
    // Vehicle Entry
    @PostMapping("/entry")
    //@PreAuthorize("hasRole('USER')") // Uncomment when OAuth2 roles are added
    public ResponseEntity<TicketResponse> parkVehicle(@RequestBody VehicleEntryRequest request) {
        Ticket ticket = ticketService.createEntryTicket(
                request.getPlateNo(),
                request.getVehicleType(),
                request.getEntryGateId()
        );
        TicketResponse response = TicketResponse.builder()
                .ticketId(ticket.getId())
                .plateNo(ticket.getVehicle().getPlateNo())
                .slotId(ticket.getSlot().getId())
                .entryTime(ticket.getEntryTime())
                .status(ticket.getStatus())
                .build();
        return ResponseEntity.ok(response);
    }

    // Vehicle Exit (prepare)
    @PostMapping("/exit/{ticketId}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<VehicleExitResponse> prepareExit(@PathVariable Long ticketId) {
        ExitInfo exitInfo = ticketService.prepareExit(ticketId);
        VehicleExitResponse response = VehicleExitResponse.builder()
                .ticketId(exitInfo.getTicket().getId())
                .plateNo(exitInfo.getTicket().getVehicle().getPlateNo())
                .slotId(exitInfo.getTicket().getSlot().getSlotNumber())
                .exitTime(exitInfo.getTicket().getExitTime())
                .amountDue(exitInfo.getFee())
                .status(exitInfo.getTicket().getStatus())
                .build();
        return ResponseEntity.ok(response);
    }

    // Payment
    @PostMapping("/pay/{ticketId}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentResponse> payTicket(@PathVariable Long ticketId,
                                                     @RequestBody PaymentRequest request) {
        var payment = paymentService.payForTicket(ticketId, request.getAmount());
        PaymentResponse response = PaymentResponse.builder()
                .paymentId(payment.getId())
                .ticketId(payment.getTicket().getId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .timestamp(payment.getTimestamp())
                .build();
        return ResponseEntity.ok(response);
    }

    // Get Ticket Info
    @GetMapping("/tickets/{ticketId}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<TicketResponse> getTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        TicketResponse response = TicketResponse.builder()
                .ticketId(ticket.getId())
                .plateNo(ticket.getVehicle().getPlateNo())
                .slotId(ticket.getSlot().getId())
                .entryTime(ticket.getEntryTime())
                .exitTime(ticket.getExitTime() != null ? ticket.getExitTime() : null)
                .status(ticket.getStatus())
                .build();
        return ResponseEntity.ok(response);
    }

    // Get Slot Availability
    @GetMapping("/slots/availability")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<AvailabilityResponse>> getAvailability() {
        var availability = slotService.getSlotAvailability()
                .stream()
                .map(slot -> AvailabilityResponse.builder()
                        .vehicleType(slot.getVehicleType())
                        .availableSlots(slot.getAvailableCount())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(availability);
    }
}