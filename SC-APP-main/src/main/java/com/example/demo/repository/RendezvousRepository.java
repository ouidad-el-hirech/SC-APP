package com.example.demo.repository;

import com.example.demo.entity.Client;
import com.example.demo.entity.Employe;
import com.example.demo.entity.RendezVous;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RendezvousRepository extends JpaRepository<RendezVous, Long> {

    // Récupérer les RDV d'un employé sur un jour (utile pour vérifier les chevauchements)
    List<RendezVous> findByEmployeAndDate(Employe employe, LocalDate date);

    // Verrouille la journée pour un employé (évite les insertions concurrentes sur la même ressource)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from RendezVous r where r.employe = :employe and r.date = :date")
    List<RendezVous> lockByEmployeAndDate(@Param("employe") Employe employe, @Param("date") LocalDate date);

    // Verrouille la journée pour un client (garantit la limite 2 RDV/jour même en concurrence)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from RendezVous r where r.client = :client and r.date = :date")
    List<RendezVous> lockByClientAndDate(@Param("client") Client client, @Param("date") LocalDate date);

    // Compte rapide des RDV d'un client sur un jour (optionnel, pratique pour des checks simples)
    long countByClientAndDate(Client client, LocalDate date);
}
