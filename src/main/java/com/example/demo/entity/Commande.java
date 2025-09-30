package com.example.demo.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Commande {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    private LocalDateTime dateCommande;

    private double montantTotal;

    private String statut; // ex: EN_COURS, PAYEE, ANNULEE

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommande> lignes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }
}
