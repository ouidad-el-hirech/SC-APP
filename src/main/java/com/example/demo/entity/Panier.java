package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Panier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Client client;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL)
    private List<LignePanier> lignes;

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

    public List<LignePanier> getLignes() {
        return lignes;
    }

    public void setLignes(List<LignePanier> lignes) {
        this.lignes = lignes;
    }
}
