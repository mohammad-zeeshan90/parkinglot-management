package com.example.parkinglot.service;

import com.example.parkinglot.dto.AddSlotRequest;
import com.example.parkinglot.model.ParkingSlot;
import com.example.parkinglot.model.SlotAvailability;
import java.util.List;

public interface SlotService {
    List<SlotAvailability> getSlotAvailability();
    ParkingSlot addSlot(AddSlotRequest request);
    void removeSlot(Long slotId);
}