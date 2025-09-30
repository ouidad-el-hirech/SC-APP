package com.example.demo.controller;

import com.example.demo.entity.Conge;
import com.example.demo.service.CongeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conges")
@CrossOrigin(origins = "") 
public class CongeController {

    private final CongeService congeService;

    public CongeController(CongeService congeService) {
        this.congeService = congeService;
    }

    //Create
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Conge> createConge(@RequestBody Conge conge) {
        return ResponseEntity.ok(congeService.createConge(conge));
    }

    //Select ALL
    @GetMapping
    public ResponseEntity<List<Conge>> getAllConges() {
        return ResponseEntity.ok(congeService.getAllConges());
    }

    //select one
    @GetMapping("/{id}")
    public ResponseEntity<Conge> getCongeById(@PathVariable Long id) {
        return ResponseEntity.ok(congeService.getCongeById(id));
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Conge> updateConge(@PathVariable Long id, @RequestBody Conge conge) {
        return ResponseEntity.ok(congeService.updateConge(id, conge));
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConge(@PathVariable Long id) {
        congeService.deleteConge(id);
        return ResponseEntity.ok("Congé supprimé avec succès !");
    }
}
