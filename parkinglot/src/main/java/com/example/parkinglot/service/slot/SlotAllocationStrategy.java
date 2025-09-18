package com.example.parkinglot.service.slot;


import com.example.parkinglot.model.EntryGate;
import com.example.parkinglot.model.ParkingSlot;
import com.example.parkinglot.model.enums.VehicleType;


public interface SlotAllocationStrategy {
/**
* Try to allocate a slot for the given vehicle type and entry gate.
* This method should participate in a transaction and use proper locking.
* Returns the allocated ParkingSlot or throws if none available.
*/
ParkingSlot allocate(VehicleType vehicleType, EntryGate entryGate);


/**
* Strategy name (useful if multiple strategies are registered)
*/
String name();
}