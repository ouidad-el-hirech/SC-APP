package com.example.demo.controller;

import com.example.demo.entity.LignePanier;
import com.example.demo.service.LignePanierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lignes-paniers")
public class LignePanierController {

    private final LignePanierService lignePanierService;

    public LignePanierController(LignePanierService lignePanierService) {
        this.lignePanierService = lignePanierService;
    }

    @GetMapping
    public List<LignePanier> getAll() {
        return lignePanierService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LignePanier> getById(@PathVariable Long id) {
        return lignePanierService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public LignePanier create(@RequestBody LignePanier lignePanier) {
        return lignePanierService.save(lignePanier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LignePanier> update(@PathVariable Long id, @RequestBody LignePanier lignePanier) {
        return lignePanierService.findById(id)
                .map(existing -> {
                    lignePanier.setId(id);
                    return ResponseEntity.ok(lignePanierService.save(lignePanier));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lignePanierService.deleteById(id);
    }
}
