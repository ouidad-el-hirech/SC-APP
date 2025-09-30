package com.example.demo.service;

import com.example.demo.entity.Commande;
import com.example.demo.repository.CommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;

    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> findById(Long id) {
        return commandeRepository.findById(id);
    }

    public Commande save(Commande commande) {
        return commandeRepository.save(commande);
    }

    public void deleteById(Long id) {
        commandeRepository.deleteById(id);
    }
}
