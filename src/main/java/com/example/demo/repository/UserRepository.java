//classe de gestion de base de donnée
package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.user;

public interface UserRepository extends JpaRepository<user, Long> {
 user findByEmail(String email);
}
