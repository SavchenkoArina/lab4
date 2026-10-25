package com.example.lab3.controller;

import com.example.lab3.model.MeterReading;
import com.example.lab3.model.UtilityCharge;
import com.example.lab3.service.MeterReadingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meter-readings")
public class MeterReadingController {
    private final MeterReadingService readingService;

    public MeterReadingController(MeterReadingService readingService) {
        this.readingService = readingService;
    }

    @GetMapping
    public ResponseEntity<List<MeterReading>> getAll() {
        return ResponseEntity.ok(readingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeterReading> getById(@PathVariable Long id) {
        return readingService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<MeterReading> create(@RequestBody MeterReading reading) {
        MeterReading created = readingService.create(reading);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MeterReading> update(@PathVariable Long id, @RequestBody MeterReading reading) {
        MeterReading updated = readingService.update(id, reading);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        readingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Эндпоинт для нашей бизнес-операции (ввод показания + авторасчет начисления)
    @PostMapping("/submit-with-charge")
    public ResponseEntity<UtilityCharge> submitWithCharge(
            @RequestParam Long meterId,
            @RequestParam Double value,
            @RequestParam String period) {
        UtilityCharge charge = readingService.submitReadingAndCalculateCharge(meterId, value, period);
        return ResponseEntity.status(HttpStatus.CREATED).body(charge);
    }
}