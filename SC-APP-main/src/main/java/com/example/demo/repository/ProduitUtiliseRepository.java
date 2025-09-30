package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Prestation;
import com.example.demo.entity.ProduitUtilise;
@Repository

public interface ProduitUtiliseRepository extends JpaRepository<ProduitUtilise ,Long> {

}
