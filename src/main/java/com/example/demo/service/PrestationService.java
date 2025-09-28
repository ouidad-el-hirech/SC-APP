package com.example.demo.service;
import com.example.demo.entity.Prestation;
import com.example.demo.repository.PrestationRepository;
import com.example.demo.repository.RendezvousRepository;

import DTO.PrestationStatDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestationService {

    private final PrestationRepository prestationRepository;

    public PrestationService(PrestationRepository prestationRepository) {
        this.prestationRepository = prestationRepository;
    }

    // Create
    public Prestation createPrestation(Prestation prestation) {
        return prestationRepository.save(prestation);
    }

    // select all
    public List<Prestation> getAllPrestation() {
        return prestationRepository.findAll();
    }

    //select one
    public Prestation getPrestationById(Long id) {
        return prestationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("prestation introuvable avec id : " + id));
    }

    //Update
    public Prestation updatePrestation(Long id, Prestation prestDetails) {
        Prestation existingPrestation = getPrestationById(id);

        existingPrestation.setNom(prestDetails.getNom());
        existingPrestation.setTarif(prestDetails.getTarif());
        existingPrestation.setDuree(prestDetails.getDuree());
      
        return prestationRepository.save(existingPrestation);
    }

    //Delete
    public void deletePrestation(Long id) {
        Prestation prestation = getPrestationById(id);
        prestationRepository.delete(prestation);
    }
    @Autowired
    private RendezvousRepository rendezVousRepository;

    public List<PrestationStatDTO> getTopPrestations(LocalDate start, LocalDate end) {
        return rendezVousRepository.findTopPrestations(start, end);
    }

}
