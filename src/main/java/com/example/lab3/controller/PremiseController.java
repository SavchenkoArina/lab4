package com.example.lab3.controller;

import com.example.lab3.model.Premise;
import com.example.lab3.service.PremiseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/premises")
public class PremiseController {
    private final PremiseService premiseService;

    public PremiseController(PremiseService premiseService) {
        this.premiseService = premiseService;
    }

    @GetMapping
    public ResponseEntity<List<Premise>> getAll() {
        return ResponseEntity.ok(premiseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Premise> getById(@PathVariable Long id) {
        return premiseService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Premise> create(@RequestBody Premise premise) {
        Premise created = premiseService.create(premise);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Premise> update(@PathVariable Long id, @RequestBody Premise premise) {
        Premise updated = premiseService.update(id, premise);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        premiseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}