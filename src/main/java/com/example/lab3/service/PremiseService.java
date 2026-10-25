package com.example.lab3.service;

import com.example.lab3.model.Premise;
import com.example.lab3.repository.PremiseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PremiseService {
    private final PremiseRepository premiseRepository;

    public PremiseService(PremiseRepository premiseRepository) {
        this.premiseRepository = premiseRepository;
    }

    public List<Premise> getAll() { return premiseRepository.findAll(); }
    public Optional<Premise> getById(Long id) { return premiseRepository.findById(id); }
    public Premise create(Premise premise) { return premiseRepository.save(premise); }

    public Premise update(Long id, Premise updated) {
        return premiseRepository.findById(id).map(premise -> {
            premise.setAddress(updated.getAddress());
            premise.setArea(updated.getArea());
            premise.setOwner(updated.getOwner());
            return premiseRepository.save(premise);
        }).orElseThrow(() -> new RuntimeException("Premise not found"));
    }

    public void delete(Long id) { premiseRepository.deleteById(id); }
}