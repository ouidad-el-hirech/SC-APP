package com.example.demo.service;

import com.example.demo.entity.LignePanier;
import com.example.demo.repository.LignePanierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LignePanierService {

    private final LignePanierRepository lignePanierRepository;

    public LignePanierService(LignePanierRepository lignePanierRepository) {
        this.lignePanierRepository = lignePanierRepository;
    }

    public List<LignePanier> findAll() {
        return lignePanierRepository.findAll();
    }

    public Optional<LignePanier> findById(Long id) {
        return lignePanierRepository.findById(id);
    }

    public LignePanier save(LignePanier lignePanier) {
        return lignePanierRepository.save(lignePanier);
    }

    public void deleteById(Long id) {
        lignePanierRepository.deleteById(id);
    }
}
