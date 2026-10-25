package com.example.lab3.service;

import com.example.lab3.model.Meter;
import com.example.lab3.repository.MeterRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MeterService {
    private final MeterRepository meterRepository;

    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public List<Meter> getAll() { return meterRepository.findAll(); }
    public Optional<Meter> getById(Long id) { return meterRepository.findById(id); }
    public Meter create(Meter meter) { return meterRepository.save(meter); }

    public Meter update(Long id, Meter updated) {
        return meterRepository.findById(id).map(meter -> {
            meter.setPremise(updated.getPremise());
            meter.setResourceType(updated.getResourceType());
            meter.setSerialNumber(updated.getSerialNumber());
            meter.setTariffPerUnit(updated.getTariffPerUnit());
            meter.setStatus(updated.getStatus());
            return meterRepository.save(meter);
        }).orElseThrow(() -> new RuntimeException("Meter not found"));
    }

    public void delete(Long id) { meterRepository.deleteById(id); }
}