package com.example.parkinglot.config;

import com.example.parkinglot.model.*;
import com.example.parkinglot.model.enums.SlotStatus;
import com.example.parkinglot.model.enums.VehicleType;
import com.example.parkinglot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ParkingLotRepository parkingLotRepository;
    private final FloorRepository floorRepository;
    private final EntryGateRepository entryGateRepository;
    private final ParkingSlotRepository parkingSlotRepository;
    private final PricingRuleRepository pricingRuleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (parkingLotRepository.count() > 0) return; // do not double insert

        ParkingLot lot = ParkingLot.builder()
                .name("MainLot")
                .location("Test Street")
                .build();
        lot = parkingLotRepository.save(lot);

        // Create two floors
        Floor f1 = Floor.builder().floorNumber(1).capacity(10).parkingLot(lot).build();
        Floor f2 = Floor.builder().floorNumber(2).capacity(10).parkingLot(lot).build();
        f1 = floorRepository.save(f1);
        f2 = floorRepository.save(f2);

        // Entry gates: one at ground (floor1) and one lot-level
        EntryGate g1 = EntryGate.builder().name("Gate-1").floor(f1).locationTag("G1").build();
        EntryGate g2 = EntryGate.builder().name("Gate-Lot").floor(null).locationTag("GL").build();
        entryGateRepository.save(g1);
        entryGateRepository.save(g2);

        // Create slots on floors
        List<ParkingSlot> created = new ArrayList<>();
        // Floor 1: 3 Car, 2 Bike
        for (int i=1;i<=3;i++) {
            created.add(ParkingSlot.builder()
                    .slotNumber("F1-C" + i)
                    .type(VehicleType.CAR)
                    .status(SlotStatus.FREE)
                    .floor(f1)
                    .build());
        }
        for (int i=1;i<=2;i++) {
            created.add(ParkingSlot.builder()
                    .slotNumber("F1-B" + i)
                    .type(VehicleType.BIKE)
                    .status(SlotStatus.FREE)
                    .floor(f1)
                    .build());
        }

        // Floor 2: 2 Car, 1 Truck, 2 Bike
        for (int i=1;i<=2;i++) {
            created.add(ParkingSlot.builder()
                    .slotNumber("F2-C" + i)
                    .type(VehicleType.CAR)
                    .status(SlotStatus.FREE)
                    .floor(f2)
                    .build());
        }
        created.add(ParkingSlot.builder()
                .slotNumber("F2-T1")
                .type(VehicleType.TRUCK)
                .status(SlotStatus.FREE)
                .floor(f2)
                .build());
        for (int i=1;i<=2;i++) {
            created.add(ParkingSlot.builder()
                    .slotNumber("F2-B" + i)
                    .type(VehicleType.BIKE)
                    .status(SlotStatus.FREE)
                    .floor(f2)
                    .build());
        }

        parkingSlotRepository.saveAll(created);

        // Pricing rules (simple)
        pricingRuleRepository.save(PricingRule.builder()
                .vehicleType(VehicleType.BIKE)
                .freeMinutes(30)          // first 30 minutes free
                .hourlyRate(BigDecimal.valueOf(10)) // 10 per hour after
                .maxCharge(BigDecimal.valueOf(100))
                .build());

        pricingRuleRepository.save(PricingRule.builder()
                .vehicleType(VehicleType.CAR)
                .freeMinutes(30)
                .hourlyRate(BigDecimal.valueOf(50))
                .maxCharge(BigDecimal.valueOf(500))
                .build());

        pricingRuleRepository.save(PricingRule.builder()
                .vehicleType(VehicleType.TRUCK)
                .freeMinutes(0)
                .hourlyRate(BigDecimal.valueOf(120))
                .maxCharge(BigDecimal.valueOf(1200))
                .build());
    }
}