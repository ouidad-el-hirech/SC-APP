package com.example.demo.controller;

import com.example.demo.entity.Prestation;
import com.example.demo.service.PrestationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Prestation")
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
    public ResponseEntity<Prestation> createPrestation(@RequestBody Prestation prestation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestationService.createPrestation(prestation));
    }

  //Update
    @PutMapping("/{id}")
    public ResponseEntity<Prestation> updatePrestation(@PathVariable Long id, @RequestBody Prestation prestation) {
        return ResponseEntity.ok(prestationService.updatePrestation(id, prestation));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deletePrestation(@PathVariable Long id) {
        prestationService.deletePrestation(id);
        return ResponseEntity.noContent().build();
    }
}
