package com.example.demo.service;

import com.example.demo.entity.ProduitUtilise;
import com.example.demo.repository.ProduitUtiliseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProduitUtiliseService {

    private final ProduitUtiliseRepository produitUtiliseRepository;

    public ProduitUtiliseService(ProduitUtiliseRepository produitUtiliseRepository) {
        this.produitUtiliseRepository = produitUtiliseRepository;
    }

    // Create
    public ProduitUtilise createProduitUtilise(ProduitUtilise produitUtilise) {
        return produitUtiliseRepository.save(produitUtilise);
    }

    // select *
    public List<ProduitUtilise> getAllProduitUtilises() {
        return produitUtiliseRepository.findAll();
    }

    //select id
    public ProduitUtilise getProduitUtiliseById(Long id) {
        return produitUtiliseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit utilisé introuvable avec id : " + id));
    }

    // Update
    public ProduitUtilise updateProduitUtilise(Long id, ProduitUtilise produitUtiliseDetails) {
        ProduitUtilise existing = getProduitUtiliseById(id);

        existing.setQuantiteUtilisee(produitUtiliseDetails.getQuantiteUtilisee());
        existing.setPrestation(produitUtiliseDetails.getPrestation());
        existing.setProduit(produitUtiliseDetails.getProduit());

        return produitUtiliseRepository.save(existing);
    }

    // Delete
    public void deleteProduitUtilise(Long id) {
        ProduitUtilise existing = getProduitUtiliseById(id);
        produitUtiliseRepository.delete(existing);
    }
}
