package com.example.parkinglot.repository;


import com.example.parkinglot.model.PricingRule;
import com.example.parkinglot.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {
Optional<PricingRule> findByVehicleType(VehicleType vehicleType);
}