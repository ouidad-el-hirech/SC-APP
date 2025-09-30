package com.example.demo.controller;

import com.example.demo.entity.Commande;
import com.example.demo.service.CommandeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public List<Commande> getAll() {
        return commandeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getById(@PathVariable Long id) {
        return commandeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Commande create(@RequestBody Commande commande) {
        return commandeService.save(commande);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> update(@PathVariable Long id, @RequestBody Commande commande) {
        return commandeService.findById(id)
                .map(existing -> {
                    commande.setId(id);
                    return ResponseEntity.ok(commandeService.save(commande));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commandeService.deleteById(id);
    }
}
