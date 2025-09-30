package com.example.demo.controller;

import com.example.demo.entity.Employe;
import com.example.demo.service.EmployeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/Employe")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    // GET /api/Employe/
    @GetMapping("/")
    public ResponseEntity<List<Employe>> getAllEmploye() {
        return ResponseEntity.ok(employeService.getAllEmployes());
    }

    // GET /api/Employe/id/{id}
    @GetMapping("/id/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
        // Si ton service renvoie Optional<Employe>, adapte en conséquence.
        Employe employe = employeService.getEmployeById(id)
                .orElse(null);
        return (employe != null) ? ResponseEntity.ok(employe) : ResponseEntity.notFound().build();
    }

    // POST /api/Employe/
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employe> createEmploye(
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("poste") String poste,
            @RequestParam(value = "statut", required = false) Boolean statut,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        try {
            Employe employe = new Employe();
            employe.setNom(nom);
            employe.setPrenom(prenom);
            employe.setPoste(poste);
            if (statut != null) employe.setStatut(statut);
            employe.setStatus(status);

            if (imageFile != null && !imageFile.isEmpty()) {
                employe.setImage(imageFile.getBytes());
                // Si tu as un champ content-type dans l’entité :
                if (hasImageContentType(employe)) {
                    employe.setImageContentType(imageFile.getContentType());
                }
            }

            Employe saved = employeService.createEmploye(employe);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT /api/Employe/{id}
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employe> updateEmploye(
            @PathVariable Long id,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("poste") String poste,
            @RequestParam(value = "statut", required = false) Boolean statut,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        try {
            // Récupérer l'existant (adaptation si ton service renvoie Optional)
            Employe existing = employeService.getEmployeById(id).orElse(null);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }

            existing.setNom(nom);
            existing.setPrenom(prenom);
            existing.setPoste(poste);
            if (statut != null) existing.setStatut(statut);
            existing.setStatus(status);

            // Si image fournie, on remplace ; sinon on conserve l'ancienne
            if (imageFile != null && !imageFile.isEmpty()) {
                existing.setImage(imageFile.getBytes());
                if (hasImageContentType(existing)) {
                    existing.setImageContentType(imageFile.getContentType());
                }
            }

            Employe updated = employeService.updateEmploye(id, existing)
                    .orElse(null); // si updateEmploye renvoie Optional
            if (updated == null) {
                // Fallback si updateEmploye renvoie l'entité directement :
                updated = existing;
            }

            return ResponseEntity.ok(updated);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE /api/Employe/id/{id}
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        boolean deleted = employeService.deleteEmploye(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // --- Helpers ---

    /**
     * Utilitaire pour éviter un appel à un getter inexistant si ton entité ne gère pas le content-type.
     * Supprime si ton entité n'a pas de champ imageContentType.
     */
    private boolean hasImageContentType(Employe e) {
        try {
            e.getClass().getDeclaredMethod("setImageContentType", String.class);
            return true;
        } catch (NoSuchMethodException ex) {
            return false;
        }
    }
}
