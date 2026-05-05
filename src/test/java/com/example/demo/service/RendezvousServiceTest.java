package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.RendezvousRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RendezvousServiceTest {

    @Mock
    private RendezvousRepository rendezvousRepository;

    @InjectMocks
    private RendezvousService rendezvousService;

    private RendezVous rdv;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        client.setIdClient(2L);

        Employe employe = new Employe();
        employe.setIdEmploye(1L);

        Prestation prestation = new Prestation();
        prestation.setIdPrestation(1L);

        rdv = new RendezVous();
        rdv.setIdRDV(1L);
        rdv.setDate(LocalDate.of(2031, 9, 24));
        rdv.setHeure(LocalTime.of(15, 17, 30));
        rdv.setStatut("CONFIRME");
        rdv.setClient(client);
        rdv.setEmploye(employe);
        rdv.setPrestation(prestation);
    }

    // ─── GET ALL ───────────────────────────────────────────

    @Test
    void testGetAllRendezVous_RetourneListe() {
        when(rendezvousRepository.findAll()).thenReturn(List.of(rdv));

        List<RendezVous> result = rendezvousService.getAllRendezVous();

        assertEquals(1, result.size());
        assertEquals("CONFIRME", result.get(0).getStatut());
        verify(rendezvousRepository, times(1)).findAll();
    }

    @Test
    void testGetAllRendezVous_ListeVide() {
        when(rendezvousRepository.findAll()).thenReturn(List.of());

        List<RendezVous> result = rendezvousService.getAllRendezVous();

        assertEquals(0, result.size());
    }

    // ─── GET BY ID ─────────────────────────────────────────

    @Test
    void testGetRendezVousById_Trouve() {
        when(rendezvousRepository.findById(1L)).thenReturn(Optional.of(rdv));

        RendezVous result = rendezvousService.getRendezVousById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdRDV());
    }

    @Test
    void testGetRendezVousById_NonTrouve() {
        when(rendezvousRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            rendezvousService.getRendezVousById(99L);
        });
    }

    // ─── ADD ───────────────────────────────────────────────

    @Test
    void testAddRendezVous_Succes() {
        when(rendezvousRepository.findByDateHeureEmploye(any(), any(), any()))
                .thenReturn(List.of());
        when(rendezvousRepository.save(rdv)).thenReturn(rdv);

        RendezVous result = rendezvousService.addRendezVous(rdv);

        assertNotNull(result);
        assertEquals("CONFIRME", result.getStatut());
        verify(rendezvousRepository, times(1)).save(rdv);
    }

    @Test
    void testAddRendezVous_DateNull() {
        rdv.setDate(null);

        assertThrows(IllegalArgumentException.class, () -> {
            rendezvousService.addRendezVous(rdv);
        });
    }

    @Test
    void testAddRendezVous_HeureNull() {
        rdv.setHeure(null);

        assertThrows(IllegalArgumentException.class, () -> {
            rendezvousService.addRendezVous(rdv);
        });
    }

    @Test
    void testAddRendezVous_StatutVide() {
        rdv.setStatut("");

        assertThrows(IllegalArgumentException.class, () -> {
            rendezvousService.addRendezVous(rdv);
        });
    }

    @Test
    void testAddRendezVous_ClientNull() {
        rdv.setClient(null);

        assertThrows(IllegalArgumentException.class, () -> {
            rendezvousService.addRendezVous(rdv);
        });
    }

    @Test
    void testAddRendezVous_CreneauOccupe() {
        when(rendezvousRepository.findByDateHeureEmploye(any(), any(), any()))
                .thenReturn(List.of(rdv));

        assertThrows(IllegalArgumentException.class, () -> {
            rendezvousService.addRendezVous(rdv);
        });
    }

    // ─── UPDATE ────────────────────────────────────────────

    @Test
    void testUpdateRendezVous_Succes() {
        when(rendezvousRepository.findById(1L)).thenReturn(Optional.of(rdv));
        when(rendezvousRepository.save(any())).thenReturn(rdv);

        rdv.setStatut("ANNULE");
        RendezVous result = rendezvousService.updateRendezVous(rdv);

        assertEquals("ANNULE", result.getStatut());
        verify(rendezvousRepository, times(1)).save(any());
    }

    @Test
    void testUpdateRendezVous_IdNull() {
        rdv.setIdRDV(null);

        assertThrows(IllegalArgumentException.class, () -> {
            rendezvousService.updateRendezVous(rdv);
        });
    }

    @Test
    void testUpdateRendezVous_NonTrouve() {
        rdv.setIdRDV(99L);
        when(rendezvousRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            rendezvousService.updateRendezVous(rdv);
        });
    }

    // ─── DELETE ────────────────────────────────────────────

    @Test
    void testDeleteRendezVous_Succes() {
        when(rendezvousRepository.findById(1L)).thenReturn(Optional.of(rdv));
        doNothing().when(rendezvousRepository).delete(rdv);

        assertDoesNotThrow(() -> rendezvousService.deleteRendezVous(1L));
        verify(rendezvousRepository, times(1)).delete(rdv);
    }

    @Test
    void testDeleteRendezVous_NonTrouve() {
        when(rendezvousRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            rendezvousService.deleteRendezVous(99L);
        });
    }
}