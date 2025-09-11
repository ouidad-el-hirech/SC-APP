package com.example.demo.model;

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
}
