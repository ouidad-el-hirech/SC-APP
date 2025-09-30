package com.example.demo.service;

import com.example.demo.entity.Panier;
import com.example.demo.repository.PanierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PanierService {

    private final PanierRepository panierRepository;

    public PanierService(PanierRepository panierRepository) {
        this.panierRepository = panierRepository;
    }

    public List<Panier> findAll() {
        return panierRepository.findAll();
    }

    public Optional<Panier> findById(Long id) {
        return panierRepository.findById(id);
    }

    public Panier save(Panier panier) {
        return panierRepository.save(panier);
    }

    public void deleteById(Long id) {
        panierRepository.deleteById(id);
    }
}
