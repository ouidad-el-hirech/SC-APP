package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employe;
import com.example.demo.repository.EmployeRepository;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    public Optional<Employe> getEmployeById(Long id) {
        return employeRepository.findById(id);
    }

    public Employe createEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    public Optional<Employe> updateEmploye(Long id, Employe employeDetails) {
        return employeRepository.findById(id).map(employe -> {
            employe.setNom(employeDetails.getNom());
            employe.setPrenom(employeDetails.getPrenom());
            employe.setPoste(employeDetails.getPoste());
            employe.setStatus(employeDetails.getStatus());
            employe.setImage(employeDetails.getImage());
            return employeRepository.save(employe);
        });
    }

    public boolean deleteEmploye(Long id) {
        if (employeRepository.existsById(id)) {
            employeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Optional<Employe> activerEmploye(Long id) {
        return employeRepository.findById(id).map(employe -> {
            employe.setStatut(true);
            return employeRepository.save(employe);
        });
    }

    public Optional<Employe> desactiverEmploye(Long id) {
        return employeRepository.findById(id).map(employe -> {
            employe.setStatut(false);
            return employeRepository.save(employe);
        });
    }
}