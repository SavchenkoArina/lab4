package com.example.lab3.service;

import com.example.lab3.model.UtilityCharge;
import com.example.lab3.repository.UtilityChargeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UtilityChargeService {
    private final UtilityChargeRepository chargeRepository;

    public UtilityChargeService(UtilityChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    public List<UtilityCharge> getAll() { return chargeRepository.findAll(); }
    public Optional<UtilityCharge> getById(Long id) { return chargeRepository.findById(id); }
    public UtilityCharge create(UtilityCharge charge) { return chargeRepository.save(charge); }

    public UtilityCharge update(Long id, UtilityCharge updated) {
        return chargeRepository.findById(id).map(charge -> {
            charge.setPremise(updated.getPremise());
            charge.setMeter(updated.getMeter());
            charge.setPeriod(updated.getPeriod());
            charge.setVolume(updated.getVolume());
            charge.setAmount(updated.getAmount());
            charge.setStatus(updated.getStatus());
            return chargeRepository.save(charge);
        }).orElseThrow(() -> new RuntimeException("UtilityCharge not found"));
    }

    public void delete(Long id) { chargeRepository.deleteById(id); }
}