package com.example.parkinglot.service.slot;


import com.example.parkinglot.exception.DomainExceptions.LotFullException;
import com.example.parkinglot.model.EntryGate;
import com.example.parkinglot.model.Floor;
import com.example.parkinglot.model.ParkingSlot;
import com.example.parkinglot.model.enums.SlotStatus;
import com.example.parkinglot.model.enums.VehicleType;
import com.example.parkinglot.repository.ParkingSlotRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class FirstAvailableSlotAllocationStrategy implements SlotAllocationStrategy {


private final ParkingSlotRepository slotRepository;
private final EntityManager em;


@Override
@Transactional
public ParkingSlot allocate(VehicleType vehicleType, EntryGate entryGate) {
// Fetch candidate free slots eagerly (no lock yet)
List<ParkingSlot> candidates = slotRepository.findByTypeAndStatus(vehicleType, SlotStatus.FREE);


if (candidates.isEmpty()) {
throw new LotFullException("No free slots available for vehicle type: " + vehicleType);
}


// Sort by floor number asc, then slotNumber asc — functional style
candidates.sort(Comparator.comparing((ParkingSlot s) -> Optional.ofNullable(s.getFloor()).map(Floor::getFloorNumber).orElse(Integer.MAX_VALUE))
.thenComparing(ParkingSlot::getSlotNumber));


// Try to claim the first slot that we can lock and set to OCCUPIED.
for (ParkingSlot candidate : candidates) {
// Lock this row pessimistically
ParkingSlot locked = em.find(ParkingSlot.class, candidate.getId(), LockModeType.PESSIMISTIC_WRITE);
if (locked == null) continue; // should not happen but safe-guard


// double-check status
if (!Objects.equals(locked.getStatus(), SlotStatus.FREE)) {
continue; // someone else took it — try next
}


// claim it
locked.setStatus(SlotStatus.OCCUPIED);
// persist change (within tx) — JPA will flush on commit, but explicit save is okay
slotRepository.save(locked);
return locked;
}


// If we reached here, no slot could be locked/claimed
throw new LotFullException("No free slots could be claimed (concurrent allocation). Try again.");
}


@Override
public String name() {
return "FIRST_AVAILABLE";
}
}