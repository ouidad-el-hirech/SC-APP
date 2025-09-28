package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Prestation;
import com.example.demo.entity.ProduitUtilise;

import DTO.RendezVousEmployeStatDTO;
@Repository

public interface ProduitUtiliseRepository extends JpaRepository<ProduitUtilise ,Long> {
	

}
