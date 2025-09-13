package com.example.demo.repository;

import com.example.demo.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendezvousRepository extends JpaRepository<RendezVous,Long> {
}
