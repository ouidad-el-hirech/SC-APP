package com.example.demo.service;

import com.example.demo.entity.Livraison;
import com.example.demo.repository.LivraisonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivraisonService {

    private final LivraisonRepository livraisonRepository;

    public LivraisonService(LivraisonRepository livraisonRepository) {
        this.livraisonRepository = livraisonRepository;
    }

    public List<Livraison> findAll() {
        return livraisonRepository.findAll();
    }

    public Optional<Livraison> findById(Long id) {
        return livraisonRepository.findById(id);
    }

    public Livraison save(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }

    public void deleteById(Long id) {
        livraisonRepository.deleteById(id);
    }
}
