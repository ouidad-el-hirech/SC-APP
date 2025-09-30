package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.entity.enums.Role;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Durée de validité du token : 24h
    private final long expiration = 1000 * 60 * 60 * 24;

    // Générer un token
    public String generateToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .claim("role", role.name())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    // Vérifier si un token est valide
    public String validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public Key getKey() {
        return key;
    }

}
