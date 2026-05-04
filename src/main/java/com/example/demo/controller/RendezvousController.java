package com.example.demo.controller;

import com.example.demo.entity.RendezVous;
import com.example.demo.service.RendezvousService;

import DTO.ChiffreAffairesMensuelDTO;
import DTO.RendezVousEmployeStatDTO;
import DTO.TopRendezvous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/rendezvous")
public class RendezvousController {

    @Autowired
    private RendezvousService rendezvousService;

    @GetMapping("/")
    public ResponseEntity<List<RendezVous>> getAllRendezvous() {
        return ResponseEntity.ok(rendezvousService.getAllRendezVous());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RendezVous> getRendezvousById(@PathVariable Long id) {
        return ResponseEntity.ok(rendezvousService.getRendezVousById(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> createRendezvous(@Valid @RequestBody RendezVous rendezVous) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(rendezvousService.addRendezVous(rendezVous));
        } catch (IllegalArgumentException e) {
            // Champs invalides OU créneau occupé
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> updateRendezvous(@RequestBody RendezVous updatedRendezVous) {
        try {
            return ResponseEntity.ok(rendezvousService.updateRendezVous(updatedRendezVous));
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteRendezvous(@PathVariable Long id) {
        rendezvousService.deleteRendezVous(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ca-mensuel/{year}")
    public List<ChiffreAffairesMensuelDTO> getChiffreAffairesMensuel(@PathVariable int year) {
        return rendezvousService.getChiffreAffairesMensuel(year);
    }

    @GetMapping("/topemploye/{year}")
    public List<RendezVousEmployeStatDTO> getStatsRendezVousParEmploye(@PathVariable int year) {
        return rendezvousService.getStatsRendezVousParEmploye(year);
    }

    @GetMapping("/totalrdv/{year}")
    public List<TopRendezvous> getTopRendezvous(@PathVariable int year) {
        return rendezvousService.getTopRendezvous(year);
    }
}