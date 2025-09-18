package com.example.parkinglot.service.slot;


import com.example.parkinglot.model.EntryGate;
import com.example.parkinglot.model.ParkingSlot;
import com.example.parkinglot.model.enums.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Map;


/**
* Facade for slot allocation. Chooses strategy based on configured name.
* For assignment, we default to FIRST_AVAILABLE.
*/
@Service
@RequiredArgsConstructor
public class SlotAllocationService {


private final Map<String, SlotAllocationStrategy> strategies;
private static final String DEFAULT = "FIRST_AVAILABLE";


public ParkingSlot allocateSlot(VehicleType vehicleType, EntryGate entryGate) {
SlotAllocationStrategy strat = strategies.getOrDefault(DEFAULT, strategies.values().stream().findFirst().orElseThrow());
return strat.allocate(vehicleType, entryGate);
}
}