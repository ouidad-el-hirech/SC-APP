package com.example.demo.service;

import com.example.demo.entity.RendezVous;
import com.example.demo.repository.RendezvousRepository;

import DTO.ChiffreAffairesMensuelDTO;
import DTO.RendezVousEmployeStatDTO;
import DTO.TopRendezvous;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RendezvousService {

    @Autowired
    private RendezvousRepository rendezvousRepository;

    public List<RendezVous> getAllRendezVous() {
        return rendezvousRepository.findAll();
    }

    public RendezVous getRendezVousById(Long id) {
        return rendezvousRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous avec ID " + id + " non trouvé"));
    }

    public RendezVous addRendezVous(RendezVous rendezVous) {
        // 1. Vérifier les champs obligatoires
        if (rendezVous.getDate() == null) {
            throw new IllegalArgumentException("La date est obligatoire");
        }
        if (rendezVous.getHeure() == null) {
            throw new IllegalArgumentException("L'heure est obligatoire");
        }
        if (rendezVous.getStatut() == null || rendezVous.getStatut().isBlank()) {
            throw new IllegalArgumentException("Le statut est obligatoire");
        }
        if (rendezVous.getClient() == null) {
            throw new IllegalArgumentException("Le client est obligatoire");
        }
        if (rendezVous.getEmploye() == null) {
            throw new IllegalArgumentException("L'employe est obligatoire");
        }
        if (rendezVous.getPrestation() == null) {
            throw new IllegalArgumentException("La prestation est obligatoire");
        }

        // 2. Vérifier disponibilité du créneau 
        List<RendezVous> existingRDV = rendezvousRepository.findByDateHeureEmploye(
            rendezVous.getDate(),
            rendezVous.getHeure(),
            rendezVous.getEmploye().getIdEmploye()
        );

        if (!existingRDV.isEmpty()) {
            throw new IllegalArgumentException("Ce créneau est déjà occupé (même employe, même date, même heure)");
        }

  
        return rendezvousRepository.save(rendezVous);
    }

    public RendezVous updateRendezVous(RendezVous updatedRendezVous) {
        if (updatedRendezVous.getIdRDV() == null) {
            throw new IllegalArgumentException("L'ID du rendez-vous est obligatoire pour la mise à jour.");
        }

        RendezVous existingRendezVous = rendezvousRepository.findById(updatedRendezVous.getIdRDV())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rendez-vous avec ID " + updatedRendezVous.getIdRDV() + " non trouvé"
                ));

        if (updatedRendezVous.getDate() != null) existingRendezVous.setDate(updatedRendezVous.getDate());
        if (updatedRendezVous.getHeure() != null) existingRendezVous.setHeure(updatedRendezVous.getHeure());
        if (updatedRendezVous.getStatut() != null) existingRendezVous.setStatut(updatedRendezVous.getStatut());
        if (updatedRendezVous.getClient() != null) existingRendezVous.setClient(updatedRendezVous.getClient());
        if (updatedRendezVous.getEmploye() != null) existingRendezVous.setEmploye(updatedRendezVous.getEmploye());
        if (updatedRendezVous.getPrestation() != null) existingRendezVous.setPrestation(updatedRendezVous.getPrestation());

        return rendezvousRepository.save(existingRendezVous);
    }

    public void deleteRendezVous(Long id) {
        RendezVous existingRendezVous = rendezvousRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous avec ID " + id + " non trouvé"));
        rendezvousRepository.delete(existingRendezVous);
    }

    public List<ChiffreAffairesMensuelDTO> getChiffreAffairesMensuel(int year) {
        return rendezvousRepository.getChiffreAffairesMensuel(year);
    }

    public List<RendezVousEmployeStatDTO> getStatsRendezVousParEmploye(int year) {
        return rendezvousRepository.getStatsRendezVousParEmploye(year);
    }

    public List<TopRendezvous> getTopRendezvous(int year) {
        return rendezvousRepository.getTopRendezvous(year);
    }
}
