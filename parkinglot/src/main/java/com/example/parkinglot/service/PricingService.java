package com.example.parkinglot.service;

import com.example.parkinglot.dto.PricingRuleRequest;
import com.example.parkinglot.model.PricingRule;

public interface PricingService {
    PricingRule addPricingRule(PricingRuleRequest request);
    PricingRule updatePricingRule(Long id, PricingRuleRequest request);
    void deletePricingRule(Long id);
}