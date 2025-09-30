package com.example.demo.controller;

import com.example.demo.entity.Prestation;
import com.example.demo.service.PrestationService;

import io.jsonwebtoken.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/Prestation")
@CrossOrigin(origins = "http://localhost:4200")
public class PrestationController {

    @Autowired
    private PrestationService prestationService;

    @GetMapping("/")
    public ResponseEntity<List<Prestation>> getAllPrestation() {
        return ResponseEntity.ok(prestationService.getAllPrestation());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Prestation> getPrestationById(@PathVariable Long id) {
        return ResponseEntity.ok(prestationService.getPrestationById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Prestation> createPrestation(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("duree") int duree,
            @RequestParam("tarif") double tarif,
            @RequestParam("typePrestation") String typePrestation,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws java.io.IOException {
        
        try {
            Prestation prestation = new Prestation();
            prestation.setNom(nom);
            prestation.setDescription(description);
            prestation.setDuree(duree);
            prestation.setTarif(tarif);
            prestation.setTypePrestation(typePrestation);
            
            // Traiter l'image si elle est présente
            if (imageFile != null && !imageFile.isEmpty()) {
                prestation.setImage(imageFile.getBytes());
            }
            
            Prestation savedPrestation = prestationService.createPrestation(prestation);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPrestation);
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prestation> updatePrestation(
            @PathVariable Long id,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("duree") int duree,
            @RequestParam("tarif") double tarif,
            @RequestParam("typePrestation") String typePrestation,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws java.io.IOException {
        
        try {
            Prestation existingPrestation = prestationService.getPrestationById(id);
            
            existingPrestation.setNom(nom);
            existingPrestation.setDescription(description);
            existingPrestation.setDuree(duree);
            existingPrestation.setTarif(tarif);
            existingPrestation.setTypePrestation(typePrestation);
            
            // Traiter l'image si elle est présente
            if (imageFile != null && !imageFile.isEmpty()) {
                existingPrestation.setImage(imageFile.getBytes());
            }
            // Si pas d'image fournie, on garde l'ancienne image
            
            Prestation updatedPrestation = prestationService.updatePrestation(id, existingPrestation);
            return ResponseEntity.ok(updatedPrestation);
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deletePrestation(@PathVariable Long id) {
        prestationService.deletePrestation(id);
        return ResponseEntity.noContent().build();
    }
}