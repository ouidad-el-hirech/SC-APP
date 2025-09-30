package com.example.demo.service;

import com.example.demo.entity.LigneCommande;
import com.example.demo.repository.LigneCommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigneCommandeService {

    private final LigneCommandeRepository ligneCommandeRepository;

    public LigneCommandeService(LigneCommandeRepository ligneCommandeRepository) {
        this.ligneCommandeRepository = ligneCommandeRepository;
    }

    public List<LigneCommande> findAll() {
        return ligneCommandeRepository.findAll();
    }

    public Optional<LigneCommande> findById(Long id) {
        return ligneCommandeRepository.findById(id);
    }

    public LigneCommande save(LigneCommande ligneCommande) {
        return ligneCommandeRepository.save(ligneCommande);
    }

    public void deleteById(Long id) {
        ligneCommandeRepository.deleteById(id);
    }
}
