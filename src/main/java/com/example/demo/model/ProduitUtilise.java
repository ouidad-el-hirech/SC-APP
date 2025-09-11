package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produits_utilises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitUtilise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantiteUtilisee;

    @ManyToOne
    @JoinColumn(name = "idPrestation")
    private Prestation prestation;

    @ManyToOne
    @JoinColumn(name = "idProduit")
    private Produit produit;
}
