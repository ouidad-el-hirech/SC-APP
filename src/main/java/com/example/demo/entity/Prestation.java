package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prestations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrestation;

    private String nom;
    private int duree;
    private double tarif;


    public Long getIdPrestation() {
        return idPrestation;
    }

    public void setIdPrestation(Long idPrestation) {
        this.idPrestation = idPrestation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }
}
