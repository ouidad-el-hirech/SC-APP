package com.example.demo.controller;

import com.example.demo.entity.Panier;
import com.example.demo.entity.LignePanier;
import com.example.demo.service.PanierService;
import com.example.demo.service.LignePanierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paniers")
public class PanierController {

    private final PanierService panierService;
    private final LignePanierService lignePanierService;

    public PanierController(PanierService panierService, LignePanierService lignePanierService) {
        this.panierService = panierService;
        this.lignePanierService = lignePanierService;
    }

    // Récupérer tous les paniers
    @GetMapping
    public List<Panier> getAll() {
        return panierService.findAll();
    }

    // Récupérer un panier par son id
    @GetMapping("/{id}")
    public ResponseEntity<Panier> getById(@PathVariable Long id) {
        Optional<Panier> panier = panierService.findById(id);
        return panier.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un nouveau panier
    @PostMapping
    public Panier create(@RequestBody Panier panier) {
        return panierService.save(panier);
    }

    // Mettre à jour un panier existant
    @PutMapping("/{id}")
    public ResponseEntity<Panier> update(@PathVariable Long id, @RequestBody Panier panier) {
        return panierService.findById(id)
                .map(existing -> {
                    panier.setId(id);
                    return ResponseEntity.ok(panierService.save(panier));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un panier
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        panierService.deleteById(id);
    }

    // --- Gestion des lignes du panier ---

    // Récupérer toutes les lignes d’un panier
    @GetMapping("/{panierId}/lignes")
    public ResponseEntity<List<LignePanier>> getLignesByPanier(@PathVariable Long panierId) {
        Optional<Panier> panier = panierService.findById(panierId);
        if (panier.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<LignePanier> lignes = panier.get().getLignes();
        return ResponseEntity.ok(lignes);
    }

    // Ajouter une ligne au panier
    @PostMapping("/{panierId}/lignes")
    public ResponseEntity<LignePanier> addLigne(@PathVariable Long panierId, @RequestBody LignePanier lignePanier) {
        Optional<Panier> panierOpt = panierService.findById(panierId);
        if (panierOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        lignePanier.setPanier(panierOpt.get());
        LignePanier saved = lignePanierService.save(lignePanier);
        return ResponseEntity.ok(saved);
    }

    // Mettre à jour une ligne du panier
    @PutMapping("/{panierId}/lignes/{ligneId}")
    public ResponseEntity<LignePanier> updateLigne(@PathVariable Long panierId, @PathVariable Long ligneId, @RequestBody LignePanier lignePanier) {
        Optional<LignePanier> existingLigne = lignePanierService.findById(ligneId);
        if (existingLigne.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!existingLigne.get().getPanier().getId().equals(panierId)) {
            return ResponseEntity.badRequest().build();
        }
        lignePanier.setId(ligneId);
        lignePanier.setPanier(existingLigne.get().getPanier());
        LignePanier updated = lignePanierService.save(lignePanier);
        return ResponseEntity.ok(updated);
    }

    // Supprimer une ligne du panier
    @DeleteMapping("/{panierId}/lignes/{ligneId}")
    public ResponseEntity<Void> deleteLigne(@PathVariable Long panierId, @PathVariable Long ligneId) {
        Optional<LignePanier> existingLigne = lignePanierService.findById(ligneId);
        if (existingLigne.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!existingLigne.get().getPanier().getId().equals(panierId)) {
            return ResponseEntity.badRequest().build();
        }
        lignePanierService.deleteById(ligneId);
        return ResponseEntity.noContent().build();
    }
}
