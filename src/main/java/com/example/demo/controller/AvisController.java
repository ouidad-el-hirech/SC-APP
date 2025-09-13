package com.example.demo.controller;

import com.example.demo.entity.Avis;
import com.example.demo.service.AvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
public class AvisController {

    @Autowired
    private AvisService avisService;

    @GetMapping("/")
    public ResponseEntity<List<Avis>> getAllAvis() {
        return ResponseEntity.ok(avisService.getAllAvis());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Avis> getAvisById(@PathVariable Long id) {
        return ResponseEntity.ok(avisService.getAvisById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Avis> createAvis(@RequestBody Avis avis) {
        return ResponseEntity.status(HttpStatus.CREATED).body(avisService.addAvis(avis));
    }

    @PutMapping("/")
    public ResponseEntity<Avis> updateAvis(@RequestBody Avis updatedAvis) {
        return ResponseEntity.ok(avisService.updateAvis(updatedAvis));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteAvis(@PathVariable Long id) {
        avisService.deleteAvis(id);
        return ResponseEntity.noContent().build();
    }
}
