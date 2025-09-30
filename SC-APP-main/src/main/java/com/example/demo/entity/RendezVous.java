package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.entity.enums.StatutRDV;

@Entity
@Table(name = "rendez_vous")
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRDV;

    private LocalDate date;
    private LocalTime heure;
    @Enumerated(EnumType.STRING)
    private StatutRDV statut = StatutRDV.CONFIRMED;

    public LocalTime getHeureFin() {
        int duree = (prestation != null && prestation.getDuree() > 0) ? prestation.getDuree() : 30;
        return heure.plusMinutes(duree);
    }
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @JoinColumn(name = "prestation_id")
    private Prestation prestation;

    public Long getIdRDV() {
        return idRDV;
    }

    public void setIdRDV(Long idRDV) {
        this.idRDV = idRDV;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public StatutRDV  getStatut() {
        return statut;
    }

    public void setStatut(StatutRDV  statut) {
        this.statut = statut;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Prestation getPrestation() {
        return prestation;
    }

    public void setPrestation(Prestation prestation) {
        this.prestation = prestation;
    }
}
