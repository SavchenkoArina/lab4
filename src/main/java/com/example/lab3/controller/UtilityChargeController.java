package com.example.lab3.controller;

import com.example.lab3.model.UtilityCharge;
import com.example.lab3.service.UtilityChargeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utility-charges")
public class UtilityChargeController {
    private final UtilityChargeService chargeService;

    public UtilityChargeController(UtilityChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @GetMapping
    public ResponseEntity<List<UtilityCharge>> getAll() {
        return ResponseEntity.ok(chargeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilityCharge> getById(@PathVariable Long id) {
        return chargeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<UtilityCharge> create(@RequestBody UtilityCharge charge) {
        UtilityCharge created = chargeService.create(charge);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UtilityCharge> update(@PathVariable Long id, @RequestBody UtilityCharge charge) {
        UtilityCharge updated = chargeService.update(id, charge);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}