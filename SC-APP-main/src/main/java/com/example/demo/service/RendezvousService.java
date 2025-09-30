package com.example.demo.service;

import com.example.demo.entity.RendezVous;
import com.example.demo.entity.Client;
import com.example.demo.entity.Employe;
import com.example.demo.entity.Prestation;
import com.example.demo.entity.enums.StatutRDV;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.EmployeRepository;
import com.example.demo.repository.PrestationRepository;
import com.example.demo.repository.RendezvousRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class RendezvousService {
    private final EmployeRepository employeRepo;
    private final ClientRepository clientRepo;
    private final PrestationRepository prestationRepo;

    @Autowired
    private RendezvousRepository rendezvousRepository;

    public RendezvousService(RendezvousRepository r, EmployeRepository e, ClientRepository c, PrestationRepository p) {
        this.rendezvousRepository = r; this.employeRepo = e; this.clientRepo = c; this.prestationRepo = p;
    }

    private static boolean overlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }

    @Transactional
    public RendezVous creerRdv(Long clientId, Long employeId, Long prestationId, LocalDate date, LocalTime heureDebut) {
        Employe employe = employeRepo.findById(employeId).orElseThrow(() -> new EntityNotFoundException("Employé introuvable"));
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client introuvable"));
        Prestation prestation = prestationRepo.findById(prestationId).orElseThrow(() -> new EntityNotFoundException("Prestation introuvable"));

        // 1) Verrouille la journée pour cet employé (évite chevauchements concurrents sur la même ressource)
        var dayRdvByEmployee = rendezvousRepository.lockByEmployeAndDate(employe, date);

        // 2) ► Verrouille aussi par client+date et vérifie la limite 2/jour
        var dayRdvByClient = rendezvousRepository.lockByClientAndDate(client, date);
        if (dayRdvByClient.size() >= 2) {
            throw new IllegalStateException("Limite atteinte : un client ne peut réserver que 2 rendez-vous par jour.");
        }

        // 3) Calcul des heures du RDV et contrôle de chevauchement côté employé
        var debut = heureDebut;
        var fin   = heureDebut.plusMinutes(prestation.getDuree());

        for (var r : dayRdvByEmployee) {
            var rs = r.getHeure();
            var rf = r.getHeureFin();
            if (overlap(debut, fin, rs, rf)) {
                throw new IllegalStateException("Créneau indisponible (chevauchement).");
            }
        }

        // 4) Création
        var rdv = new RendezVous();
        rdv.setClient(client);
        rdv.setEmploye(employe);
        rdv.setPrestation(prestation);
        rdv.setDate(date);
        rdv.setHeure(debut);
        rdv.setStatut(StatutRDV.PENDING);

        return rendezvousRepository.save(rdv);
    }

    public List<RendezVous> getAllRendezVous() {
        return rendezvousRepository.findAll();
    }

    public RendezVous getRendezVousById(Long id) {
        return rendezvousRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous avec ID " + id + " non trouvé"));
    }

    public RendezVous addRendezVous(RendezVous rendezVous) {
        return rendezvousRepository.save(rendezVous);
    }

    @Transactional
    public RendezVous updateRendezVous(RendezVous updatedRendezVous) {
        if (updatedRendezVous.getIdRDV() == null) {
            throw new IllegalArgumentException("L'ID du rendez-vous est obligatoire pour la mise à jour.");
        }

        RendezVous existing = rendezvousRepository.findById(updatedRendezVous.getIdRDV())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rendez-vous avec ID " + updatedRendezVous.getIdRDV() + " non trouvé"
                ));

        // Appliquer les champs modifiés
        if (updatedRendezVous.getDate() != null) existing.setDate(updatedRendezVous.getDate());
        if (updatedRendezVous.getHeure() != null) existing.setHeure(updatedRendezVous.getHeure());
        if (updatedRendezVous.getStatut() != null) existing.setStatut(updatedRendezVous.getStatut());
        if (updatedRendezVous.getClient() != null) existing.setClient(updatedRendezVous.getClient());
        if (updatedRendezVous.getEmploye() != null) existing.setEmploye(updatedRendezVous.getEmploye());
        if (updatedRendezVous.getPrestation() != null) existing.setPrestation(updatedRendezVous.getPrestation());

        // ► Re-valider la règle 2 RDV/jour après modification (client/date potentiellement changés)
        var lockedByClientDay = rendezvousRepository.lockByClientAndDate(existing.getClient(), existing.getDate());
        long countOthers = lockedByClientDay.stream()
                .filter(r -> !r.getIdRDV().equals(existing.getIdRDV()))
                .count();
        if (countOthers >= 2) {
            throw new IllegalStateException("Limite atteinte : un client ne peut réserver que 2 rendez-vous par jour.");
        }

        // (Optionnel) contrôler aussi le chevauchement côté employé si heure/date/employé ont changé
        var lockedByEmployeeDay = rendezvousRepository.lockByEmployeAndDate(existing.getEmploye(), existing.getDate());
        var debut = existing.getHeure();
        var fin   = debut.plusMinutes(existing.getPrestation().getDuree());
        for (var r : lockedByEmployeeDay) {
            if (r.getIdRDV().equals(existing.getIdRDV())) continue;
            if (overlap(debut, fin, r.getHeure(), r.getHeureFin())) {
                throw new IllegalStateException("Créneau indisponible (chevauchement).");
            }
        }

        return rendezvousRepository.save(existing);
    }

    public void deleteRendezVous(Long id) {
        RendezVous existingRendezVous = rendezvousRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous avec ID " + id + " non trouvé"));
        rendezvousRepository.delete(existingRendezVous);
    }
}
