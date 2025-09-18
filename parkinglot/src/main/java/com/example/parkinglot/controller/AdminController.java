package com.example.parkinglot.controller;

import com.example.parkinglot.dto.*;
import com.example.parkinglot.model.ParkingSlot;
import com.example.parkinglot.model.PricingRule;
import com.example.parkinglot.service.PricingService;
import com.example.parkinglot.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final SlotService slotService;
    private final PricingService pricingService;

    // Add parking slot
    @PostMapping("/slots")
    public ResponseEntity<AdminResponse> addSlot(@RequestBody AddSlotRequest request) {
        ParkingSlot slot = slotService.addSlot(request);
        return ResponseEntity.ok(AdminResponse.builder()
                .message("Slot added successfully: " + slot.getSlotNumber())
                .success(true)
                .build());
    }

    // Remove parking slot
    @DeleteMapping("/slots/{slotId}")
    public ResponseEntity<AdminResponse> removeSlot(@PathVariable Long slotId) {
        slotService.removeSlot(slotId);
        return ResponseEntity.ok(AdminResponse.builder()
                .message("Slot removed successfully")
                .success(true)
                .build());
    }

    // Add pricing rule
    @PostMapping("/pricing")
    public ResponseEntity<AdminResponse> addPricingRule(@RequestBody PricingRuleRequest request) {
        try {
            PricingRule rule = pricingService.addPricingRule(request);
            return ResponseEntity.ok(
                    AdminResponse.builder()
                            .message("Pricing rule added successfully for " + rule.getVehicleType())
                            .success(true)
                            .build()
            );
        } catch (RuntimeException ex) {
            // If rule already exists or any other runtime exception occurs
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(AdminResponse.builder()
                            .message(ex.getMessage())
                            .success(false)
                            .build());
        }
    }

    // Update pricing rule
    @PutMapping("/pricing/{id}")
    public ResponseEntity<AdminResponse> updatePricingRule(@PathVariable Long id, @RequestBody PricingRuleRequest request) {
        PricingRule rule = pricingService.updatePricingRule(id, request);
        return ResponseEntity.ok(AdminResponse.builder()
                .message("Pricing rule updated successfully for " + rule.getVehicleType())
                .success(true)
                .build());
    }

    // Delete pricing rule
    @DeleteMapping("/pricing/{id}")
    public ResponseEntity<AdminResponse> deletePricingRule(@PathVariable Long id) {
        pricingService.deletePricingRule(id);
        return ResponseEntity.ok(AdminResponse.builder()
                .message("Pricing rule deleted successfully")
                .success(true)
                .build());
    }
}