package com.example.parkinglot.service;

import com.example.parkinglot.dto.AddSlotRequest;
import com.example.parkinglot.model.Floor;
import com.example.parkinglot.model.ParkingSlot;
import com.example.parkinglot.model.SlotAvailability;
import com.example.parkinglot.model.enums.SlotStatus;
import com.example.parkinglot.model.enums.VehicleType;
import com.example.parkinglot.repository.FloorRepository;
import com.example.parkinglot.repository.ParkingSlotRepository;
import com.example.parkinglot.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final FloorRepository floorRepository;

    @Override
    public List<SlotAvailability> getSlotAvailability() {
        return parkingSlotRepository.findAll().stream()
                .filter(slot -> slot.getStatus() == SlotStatus.FREE)
                .collect(Collectors.groupingBy(ParkingSlot::getType, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> SlotAvailability.builder()
                        .vehicleType(entry.getKey())
                        .availableCount(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ParkingSlot addSlot(AddSlotRequest request) {
        Floor floor = floorRepository.findById(request.getFloorId())
                .orElseThrow(() -> new RuntimeException("Floor not found"));
        ParkingSlot slot = ParkingSlot.builder()
                .floor(floor)
                .slotNumber(request.getSlotNumber())
                .status(SlotStatus.FREE)
                .type(request.getVehicleType())
                .build();
        return parkingSlotRepository.save(slot);
    }

    @Override
    public void removeSlot(Long slotId) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        parkingSlotRepository.delete(slot);
    }
}
