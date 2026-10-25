package com.example.lab3.service;

import com.example.lab3.model.Meter;
import com.example.lab3.model.MeterReading;
import com.example.lab3.model.UtilityCharge;
import com.example.lab3.repository.MeterReadingRepository;
import com.example.lab3.repository.MeterRepository;
import com.example.lab3.repository.UtilityChargeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MeterReadingService {
    private final MeterReadingRepository readingRepository;
    private final MeterRepository meterRepository;
    private final UtilityChargeRepository utilityChargeRepository;

    public MeterReadingService(MeterReadingRepository readingRepository,
                               MeterRepository meterRepository,
                               UtilityChargeRepository utilityChargeRepository) {
        this.readingRepository = readingRepository;
        this.meterRepository = meterRepository;
        this.utilityChargeRepository = utilityChargeRepository;
    }

    public List<MeterReading> getAll() {
        return readingRepository.findAll();
    }

    public Optional<MeterReading> getById(Long id) {
        return readingRepository.findById(id);
    }

    public MeterReading create(MeterReading reading) {
        return readingRepository.save(reading);
    }

    public MeterReading update(Long id, MeterReading updated) {
        return readingRepository.findById(id).map(reading -> {
            reading.setMeter(updated.getMeter());
            reading.setPeriod(updated.getPeriod());
            reading.setValue(updated.getValue());
            reading.setVerificationStatus(updated.getVerificationStatus());
            return readingRepository.save(reading);
        }).orElseThrow(() -> new RuntimeException("MeterReading not found"));
    }

    public void delete(Long id) {
        readingRepository.deleteById(id);
    }

    // БИЗНЕС-ОПЕРАЦИЯ: Ввод показания счетчика с автоматическим расчетом начисления
    // Затрагивает 3 сущности одновременно: MeterReading, Meter, UtilityCharge
    @Transactional
    public UtilityCharge submitReadingAndCalculateCharge(Long meterId, Double value, String period) {
        // 1. Находим счетчик в базе
        Meter meter = meterRepository.findById(meterId)
                .orElseThrow(() -> new RuntimeException("Meter not found"));

        // Проверяем бизнес-правило: нельзя вносить показания по отключенному счетчику
        if ("DISCONNECTED".equals(meter.getStatus())) {
            throw new IllegalStateException("Cannot add reading to a disconnected meter.");
        }

        // 2. Создаем и сохраняем показание счетчика
        MeterReading reading = new MeterReading();
        reading.setMeter(meter);
        reading.setValue(value);
        reading.setPeriod(period);
        reading.setVerificationStatus("VERIFIED");
        readingRepository.save(reading);

        // 3. Автоматически рассчитываем сумму начисления (объем * тариф из счетчика)
        double amount = value * meter.getTariffPerUnit();

        // 4. Создаем и сохраняем запись о начислении
        UtilityCharge charge = new UtilityCharge();
        charge.setPremise(meter.getPremise());
        charge.setMeter(meter);
        charge.setPeriod(period);
        charge.setVolume(value);
        charge.setAmount(amount);
        charge.setStatus("UNPAID");

        return utilityChargeRepository.save(charge);
    }
}