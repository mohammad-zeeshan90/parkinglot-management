package com.example.parkinglot.repository;


import com.example.parkinglot.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
Optional<Vehicle> findByPlateNo(String plateNo);
}