package com.example.parkinglot.service;

import com.example.parkinglot.dto.PricingRuleRequest;
import com.example.parkinglot.model.PricingRule;
import com.example.parkinglot.model.enums.VehicleType;
import com.example.parkinglot.repository.PricingRuleRepository;
import com.example.parkinglot.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final PricingRuleRepository pricingRuleRepository;

    @Override
    @Transactional
    public PricingRule addPricingRule(PricingRuleRequest request) {
        VehicleType vehicleType = request.getVehicleType();

        Optional<PricingRule> existingRule = pricingRuleRepository.findByVehicleType(vehicleType);
        if (existingRule.isPresent()) {
            throw new RuntimeException("Pricing rule already exists for vehicle type: " + vehicleType);
        }

        PricingRule newRule = PricingRule.builder()
                .vehicleType(vehicleType)
                .freeMinutes(request.getFreeMinutes())
                .hourlyRate(request.getHourlyRate())
                .maxCharge(request.getMaxCharge())
                .note(request.getNote())
                .build();

        return pricingRuleRepository.save(newRule);
    }

    @Override
    @Transactional
    public PricingRule updatePricingRule(Long id, PricingRuleRequest request) {
        PricingRule rule = pricingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pricing rule not found with id: " + id));
        rule.setVehicleType(request.getVehicleType());
        rule.setFreeMinutes(request.getFreeMinutes());
        rule.setHourlyRate(request.getHourlyRate());
        rule.setMaxCharge(request.getMaxCharge());
        rule.setNote(request.getNote());
        return pricingRuleRepository.save(rule);
    }

    @Override
    @Transactional
    public void deletePricingRule(Long id) {
        PricingRule rule = pricingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pricing rule not found with id: " + id));
        pricingRuleRepository.delete(rule);
    }
}