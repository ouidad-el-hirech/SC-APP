package com.example.demo.controller;

import com.example.demo.entity.RendezVous;
import com.example.demo.service.RendezvousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<RendezVous> createRendezvous(@RequestBody RendezVous rendezVous) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rendezvousService.addRendezVous(rendezVous));
    }

    @PutMapping("/")
    public ResponseEntity<RendezVous> updateRendezvous(@RequestBody RendezVous updatedRendezVous) {
        return ResponseEntity.ok(rendezvousService.updateRendezVous(updatedRendezVous));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteRendezvous(@PathVariable Long id) {
        rendezvousService.deleteRendezVous(id);
        return ResponseEntity.noContent().build();
    }
}
