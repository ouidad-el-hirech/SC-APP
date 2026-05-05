package com.example.demo.service;

import com.example.demo.entity.Client;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.Role;
import com.example.demo.repository.ClientRepository;
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
    private final ClientRepository clientRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, ClientRepository clientRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> login(User user) {
        // Champs obligatoires
        if (user.getEmail() == null || user.getEmail().isBlank())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'email est obligatoire");
        if (user.getPassword() == null || user.getPassword().isBlank())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le mot de passe est obligatoire");

        // Format email
        if (!user.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format de l'email invalide");

        User u = userRepository.findByEmail(user.getEmail());

        if (u != null && u.getPassword().equals(user.getPassword())) {
            String token = jwtUtil.generateToken(u.getEmail(), u.getRole());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", u.getEmail());
            response.put("nom", u.getNom());
            response.put("prenom", u.getPrenom());
            response.put("telephone", u.getTelephone());
            response.put("role", u.getRole());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
    }

    public ResponseEntity<?> register(User user) {
        // Champs obligatoires
        if (user.getEmail() == null || user.getEmail().isBlank())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'email est obligatoire");
        if (user.getPassword() == null || user.getPassword().isBlank())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le mot de passe est obligatoire");
        if (user.getNom() == null || user.getNom().isBlank())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le nom est obligatoire");
        if (user.getPrenom() == null || user.getPrenom().isBlank())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le prénom est obligatoire");
        if (user.getTelephone() == null || user.getTelephone().isBlank())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le téléphone est obligatoire");

        // Format email
        if (!user.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format de l'email invalide");

        // Longueur mot de passe
        if (user.getPassword().length() < 6)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le mot de passe doit contenir au moins 6 caractères");

        // Email déjà existant
        if (userRepository.findByEmail(user.getEmail()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Un compte avec cet email existe déjà");

        // Sauvegarde
        user.setRole(Role.CLIENT);
        User savedUser = userRepository.save(user);

        if (savedUser.getRole() == Role.CLIENT) {
            Client client = new Client();
            client.setNom(savedUser.getNom());
            client.setPrenom(savedUser.getPrenom());
            client.setEmail(savedUser.getEmail());
            client.setTelephone(savedUser.getTelephone());
            client.setUser(savedUser);
            clientRepository.save(client);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}