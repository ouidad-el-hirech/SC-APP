package com.example.demo.service;

import com.example.demo.entity.Avis;
import com.example.demo.repository.AvisRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvisService {

    @Autowired
    private AvisRepository avisRepository;

    public List<Avis> getAllAvis() {
        return avisRepository.findAll();
    }

    public Avis getAvisById(Long id) {
        return avisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avis avec ID " + id + " non trouvé"));
    }


    public Avis addAvis(Avis avis) {
        return avisRepository.save(avis);
    }

    public Avis updateAvis(Avis updatedAvis) {
        if (updatedAvis.getIdAvis() == null) {
            throw new IllegalArgumentException("L'ID de l'avis est obligatoire pour la mise à jour.");
        }

        Avis existingAvis = avisRepository.findById(updatedAvis.getIdAvis())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Avis avec ID " + updatedAvis.getIdAvis() + " non trouvé"
                ));

        if (updatedAvis.getNote() != 0) existingAvis.setNote(updatedAvis.getNote());
        if (updatedAvis.getCommentaire() != null) existingAvis.setCommentaire(updatedAvis.getCommentaire());
        if (updatedAvis.getDate() != null) existingAvis.setDate(updatedAvis.getDate());
        if (updatedAvis.getClient() != null) existingAvis.setClient(updatedAvis.getClient());
        if (updatedAvis.getPrestation() != null) existingAvis.setPrestation(updatedAvis.getPrestation());

        return avisRepository.save(existingAvis);
    }



    public void deleteAvis(Long id) {
        if (!avisRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : avis avec ID " + id + " non trouvé");
        }
        avisRepository.deleteById(id);
    }
}