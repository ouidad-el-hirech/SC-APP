package com.example.demo.entity;

import com.example.demo.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // on évite "user" seul, mot réservé dans certaines DB
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Sauvegarde le rôle comme texte
    @Column(nullable = false)
    private Role role;
}
