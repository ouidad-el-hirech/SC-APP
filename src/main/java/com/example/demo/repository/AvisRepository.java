package com.example.demo.repository;

import com.example.demo.entity.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AvisRepository extends JpaRepository<Avis,Long> {
	 @Transactional
	 void deleteByClient_IdClient(Long clientId);
}
