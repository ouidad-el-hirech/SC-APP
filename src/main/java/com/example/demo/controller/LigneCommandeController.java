package com.example.demo.controller;

import com.example.demo.entity.LigneCommande;
import com.example.demo.service.LigneCommandeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lignes-commandes")
public class LigneCommandeController {

    private final LigneCommandeService ligneCommandeService;

    public LigneCommandeController(LigneCommandeService ligneCommandeService) {
        this.ligneCommandeService = ligneCommandeService;
    }

    @GetMapping
    public List<LigneCommande> getAll() {
        return ligneCommandeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneCommande> getById(@PathVariable Long id) {
        return ligneCommandeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public LigneCommande create(@RequestBody LigneCommande ligneCommande) {
        return ligneCommandeService.save(ligneCommande);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigneCommande> update(@PathVariable Long id, @RequestBody LigneCommande ligneCommande) {
        return ligneCommandeService.findById(id)
                .map(existing -> {
                    ligneCommande.setId(id);
                    return ResponseEntity.ok(ligneCommandeService.save(ligneCommande));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ligneCommandeService.deleteById(id);
    }
}
