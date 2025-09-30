package com.example.demo.controller;

import com.example.demo.entity.ProduitUtilise;
import com.example.demo.service.ProduitUtiliseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produits-utilises")
public class ProduitUtiliseController {

    @Autowired
    private ProduitUtiliseService produitUtiliseService;

    // GET all
    @GetMapping("/")
    public ResponseEntity<List<ProduitUtilise>> getAllProduitUtilises() {
        return ResponseEntity.ok(produitUtiliseService.getAllProduitUtilises());
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProduitUtilise> getProduitUtiliseById(@PathVariable Long id) {
        return ResponseEntity.ok(produitUtiliseService.getProduitUtiliseById(id));
    }

    // CREATE
    @PostMapping("/")
    public ResponseEntity<ProduitUtilise> createProduitUtilise(@RequestBody ProduitUtilise produitUtilise) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produitUtiliseService.createProduitUtilise(produitUtilise));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ProduitUtilise> updateProduitUtilise(@PathVariable Long id, @RequestBody ProduitUtilise produitUtilise) {
        return ResponseEntity.ok(produitUtiliseService.updateProduitUtilise(id, produitUtilise));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduitUtilise(@PathVariable Long id) {
        produitUtiliseService.deleteProduitUtilise(id);
        return ResponseEntity.noContent().build();
    }
}
