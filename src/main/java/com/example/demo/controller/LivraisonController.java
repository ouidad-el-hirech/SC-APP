package com.example.demo.controller;

import com.example.demo.entity.Livraison;
import com.example.demo.service.LivraisonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livraisons")
public class LivraisonController {

    private final LivraisonService livraisonService;

    public LivraisonController(LivraisonService livraisonService) {
        this.livraisonService = livraisonService;
    }

    @GetMapping
    public List<Livraison> getAll() {
        return livraisonService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livraison> getById(@PathVariable Long id) {
        return livraisonService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Livraison create(@RequestBody Livraison livraison) {
        return livraisonService.save(livraison);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livraison> update(@PathVariable Long id, @RequestBody Livraison livraison) {
        return livraisonService.findById(id)
                .map(existing -> {
                    livraison.setId(id);
                    return ResponseEntity.ok(livraisonService.save(livraison));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        livraisonService.deleteById(id);
    }
}
