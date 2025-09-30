package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class LignePanier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Panier panier;

    @ManyToOne
    private Produit produit;

    private int quantite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

