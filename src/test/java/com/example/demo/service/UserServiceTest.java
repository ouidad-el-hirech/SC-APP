package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.enums.Role;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("JALILA@GMAIL.COM");
        user.setPassword("123456");
        user.setNom("jalila");
        user.setPrenom("ahmed");
        user.setTelephone("0600000000");
        user.setRole(Role.CLIENT);
    }

    // ─── LOGIN ─────────────────────────────────────────────

    @Test
    void testLogin_Succes() {
        when(userRepository.findByEmail("JALILA@GMAIL.COM")).thenReturn(user);
        when(jwtUtil.generateToken(any(), any())).thenReturn("fake-token");

        ResponseEntity<?> response = userService.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLogin_EmailVide() {
        user.setEmail("");

        ResponseEntity<?> response = userService.login(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("L'email est obligatoire", response.getBody());
    }

    @Test
    void testLogin_PasswordVide() {
        user.setPassword("");

        ResponseEntity<?> response = userService.login(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Le mot de passe est obligatoire", response.getBody());
    }

    @Test
    void testLogin_FormatEmailInvalide() {
        user.setEmail("emailinvalide");

        ResponseEntity<?> response = userService.login(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Format de l'email invalide", response.getBody());
    }

    @Test
    void testLogin_MotDePasseIncorrect() {
        user.setPassword("mauvais");
        User userDB = new User();
        userDB.setEmail("JALILA@GMAIL.COM");
        userDB.setPassword("123456");

        when(userRepository.findByEmail("JALILA@GMAIL.COM")).thenReturn(userDB);

        ResponseEntity<?> response = userService.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testLogin_UserInexistant() {
        when(userRepository.findByEmail("JALILA@GMAIL.COM")).thenReturn(null);

        ResponseEntity<?> response = userService.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    // ─── REGISTER ──────────────────────────────────────────

    @Test
    void testRegister_Succes() {
        when(userRepository.findByEmail("JALILA@GMAIL.COM")).thenReturn(null);
        when(userRepository.save(any())).thenReturn(user);

        ResponseEntity<?> response = userService.register(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(clientRepository, times(1)).save(any());
    }

    @Test
    void testRegister_EmailVide() {
        user.setEmail("");

        ResponseEntity<?> response = userService.register(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("L'email est obligatoire", response.getBody());
    }

   @Test
    void testRegister_PasswordTropCourt() {
        user.setPassword("123");

        ResponseEntity<?> response = userService.register(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Le mot de passe doit contenir au moins 6 caractères", response.getBody());
    }

    @Test
    void testRegister_EmailDejaExistant() {
        when(userRepository.findByEmail("JALILA@GMAIL.COM")).thenReturn(user);

        ResponseEntity<?> response = userService.register(user);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Un compte avec cet email existe déjà", response.getBody());
    }

    @Test
    void testRegister_NomVide() {
        user.setNom("");

        ResponseEntity<?> response = userService.register(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Le nom est obligatoire", response.getBody());
    }

    @Test
    void testRegister_TelephoneVide() {
        user.setTelephone("");

        ResponseEntity<?> response = userService.register(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Le téléphone est obligatoire", response.getBody());
    }

    @Test
    void testRegister_FormatEmailInvalide() {
        user.setEmail("pasunemail");

        ResponseEntity<?> response = userService.register(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Format de l'email invalide", response.getBody());
    }
}