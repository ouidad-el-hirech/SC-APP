package com.example.demo.controller;

import com.example.demo.entity.Employe;
import com.example.demo.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/employes")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @GetMapping("/")
    public ResponseEntity<List<Employe>> getAllEmployes() {
        return ResponseEntity.ok(employeService.getAllEmployes());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.getEmployeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Employe> createEmploye(@RequestBody Employe employe) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeService.addEmploye(employe));
    }

    @PutMapping("/")
    public ResponseEntity<Employe> updateEmploye(@RequestBody Employe updatedEmploye) {
        return ResponseEntity.ok(employeService.updateEmploye(updatedEmploye));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        employeService.deleteEmploye(id);
        return ResponseEntity.noContent().build();
    }
}
