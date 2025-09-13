package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "conges")
@NoArgsConstructor
@AllArgsConstructor
public class Conge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConge;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String motif;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;


    public Long getIdConge() {
        return idConge;
    }

    public void setIdConge(Long idConge) {
        this.idConge = idConge;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
}
