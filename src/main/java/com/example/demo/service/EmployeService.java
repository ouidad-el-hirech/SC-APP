package com.example.demo.service;

import com.example.demo.entity.Employe;
import com.example.demo.repository.EmployeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employé avec ID " + id + " non trouvé"));
    }

    public Employe addEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    public Employe updateEmploye(Employe updatedEmploye) {
        if (updatedEmploye.getIdEmploye() == null) {
            throw new IllegalArgumentException("L'ID de l'employé est obligatoire pour la mise à jour.");
        }

        Employe existingEmploye = employeRepository.findById(updatedEmploye.getIdEmploye())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Employé avec ID " + updatedEmploye.getIdEmploye() + " non trouvé"
                ));

        if (updatedEmploye.getNom() != null) existingEmploye.setNom(updatedEmploye.getNom());
        if (updatedEmploye.getPrenom() != null) existingEmploye.setPrenom(updatedEmploye.getPrenom());
        if (updatedEmploye.getPoste() != null) existingEmploye.setPoste(updatedEmploye.getPoste());

        return employeRepository.save(existingEmploye);
    }

    public void deleteEmploye(Long id) {
        Employe existingEmploye = employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employé avec ID " + id + " non trouvé"));
        employeRepository.delete(existingEmploye);
    }
}
