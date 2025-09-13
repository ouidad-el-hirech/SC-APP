package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "conges")
@Data
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
}
