package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "employes")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Le poste est obligatoire")
    private String poste;

    // Champ booléen existant
    private boolean statut = true; 

    // Nouveau champ String
    private String status; 

    // Nouveau champ image
    @Lob
    private byte[] image;

    private String imageContentType; // ex: "image/png" ou "image/jpeg"

    public String getImageContentType() { return imageContentType; }
    public void setImageContentType(String imageContentType) { this.imageContentType = imageContentType; }

    // Getters et Setters
    public Long getId() { return idEmploye; }
    public void setId(Long id) { this.idEmploye = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }

    public boolean isStatut() { return statut; }
    public void setStatut(boolean statut) { this.statut = statut; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
}

