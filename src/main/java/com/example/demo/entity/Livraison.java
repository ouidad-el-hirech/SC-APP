package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Livraison {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Commande commande;

    private String adresseLivraison;

    private LocalDateTime dateLivraison;

    private String statut; // EN_PREPARATION, EN_COURS, LIVREE

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public LocalDateTime getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
