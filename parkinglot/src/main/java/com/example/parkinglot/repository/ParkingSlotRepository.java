package com.example.parkinglot.repository;


import com.example.parkinglot.model.Floor;
import com.example.parkinglot.model.ParkingSlot;
import com.example.parkinglot.model.enums.SlotStatus;
import com.example.parkinglot.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {


List<ParkingSlot> findByTypeAndStatus(VehicleType type, SlotStatus status);


// Try to find a slot by floor + slot number
Optional<ParkingSlot> findByFloorAndSlotNumber(Floor floor, String slotNumber);


// Convenience: find first by type & status ordered by floor.floorNumber asc then slotNumber
Optional<ParkingSlot> findFirstByTypeAndStatusOrderByFloorFloorNumberAscSlotNumberAsc(VehicleType type, SlotStatus status);
}