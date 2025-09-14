package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // LOGIN avec génération du token
    public ResponseEntity<?> login(User user) {
        User u = userRepository.findByEmail(user.getEmail());

        if (u != null && u.getPassword().equals(user.getPassword())) {
            // Générer un token
            String token = jwtUtil.generateToken(u.getEmail(),u.getRole());

            // Retourner un objet JSON avec token et infos utilisateur
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", u.getEmail());
            response.put("nom", u.getNom());
            response.put("telephone", u.getTelephone());
            response.put("prenom", u.getPrenom());
            response.put("role", u.getRole());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Email ou mot de passe incorrect");
    }

    // Inscription
    public User register(User user) {
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
