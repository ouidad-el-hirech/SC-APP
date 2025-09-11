//classe de gestion de base de donnée
package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.security.User;

public interface UserRepository extends JpaRepository<User, Long> {
 User findByEmail(String email);
}
