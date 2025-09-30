package com.example.demo.service;

import com.example.demo.entity.Produit;
import com.example.demo.repository.ProduitRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    // Create
    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    // Get all
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    // Get by ID
    public Produit getProduitById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec id : " + id));
    }

    // Update
    public Produit updateProduit(Long id, Produit produitDetails) {
        this.getProduitById(id);
        return produitRepository.save(produitDetails);
    }

    // Delete
    public void deleteProduit(Long id) {
        Produit produit = getProduitById(id);
        produitRepository.delete(produit);
    }
}
