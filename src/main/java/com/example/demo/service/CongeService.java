package com.example.demo.service;

import com.example.demo.entity.Conge;
import com.example.demo.repository.CongeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongeService {

    private final CongeRepository congeRepository;

    public CongeService(CongeRepository congeRepository) {
        this.congeRepository = congeRepository;
    }

    // Create
    public Conge createConge(Conge conge) {
        return congeRepository.save(conge);
    }

    // select all
    public List<Conge> getAllConges() {
        return congeRepository.findAll();
    }

    //select one
    public Conge getCongeById(Long id) {
        return congeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Congé introuvable avec id : " + id));
    }

    //Update
    public Conge updateConge(Long id, Conge congeDetails) {
        Conge existingConge = getCongeById(id);

        existingConge.setDateDebut(congeDetails.getDateDebut());
        existingConge.setDateFin(congeDetails.getDateFin());
        existingConge.setMotif(congeDetails.getMotif());
        existingConge.setEmploye(congeDetails.getEmploye());

        return congeRepository.save(existingConge);
    }

    //Delete
    public void deleteConge(Long id) {
        Conge conge = getCongeById(id);
        congeRepository.delete(conge);
    }
}
