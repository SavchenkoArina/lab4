package com.example.lab3.controller;

import com.example.lab3.model.Meter;
import com.example.lab3.service.MeterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meters")
public class MeterController {
    private final MeterService meterService;

    public MeterController(MeterService meterService) {
        this.meterService = meterService;
    }

    @GetMapping
    public ResponseEntity<List<Meter>> getAll() {
        return ResponseEntity.ok(meterService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meter> getById(@PathVariable Long id) {
        return meterService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Meter> create(@RequestBody Meter meter) {
        Meter created = meterService.create(meter);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Meter> update(@PathVariable Long id, @RequestBody Meter meter) {
        Meter updated = meterService.update(id, meter);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        meterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}