package com.example.demo.service;

import com.example.demo.entity.RendezVous;
import com.example.demo.repository.RendezvousRepository;
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
}
