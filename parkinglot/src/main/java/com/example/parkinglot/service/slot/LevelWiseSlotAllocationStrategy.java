package com.example.parkinglot.service.slot;


import com.example.parkinglot.model.EntryGate;
import com.example.parkinglot.model.ParkingSlot;
import com.example.parkinglot.model.enums.VehicleType;
import org.springframework.stereotype.Component;


/**
* Simple Level-wise strategy skeleton. For now, it delegates to first-available
* ordering by preferred floors — implementation can be extended later.
*/
@Component
public class LevelWiseSlotAllocationStrategy implements SlotAllocationStrategy {


private final FirstAvailableSlotAllocationStrategy delegate;


public LevelWiseSlotAllocationStrategy(FirstAvailableSlotAllocationStrategy delegate) {
this.delegate = delegate;
}


@Override
public ParkingSlot allocate(VehicleType vehicleType, EntryGate entryGate) {
// For MVP just reuse first-available which is ordered by floor asc
return delegate.allocate(vehicleType, entryGate);
}


@Override
public String name() {
return "LEVEL_WISE";
}
}