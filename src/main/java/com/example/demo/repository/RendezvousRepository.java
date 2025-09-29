package com.example.demo.repository;

import com.example.demo.entity.RendezVous;

import DTO.ChiffreAffairesMensuelDTO;
import DTO.PrestationStatDTO;
import DTO.RendezVousEmployeStatDTO;
import DTO.TopRendezvous;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RendezvousRepository extends JpaRepository<RendezVous,Long> {
	@Query("""
		    SELECT new DTO.PrestationStatDTO(p.nom, COUNT(r.idRDV))
		    FROM RendezVous r
		    JOIN r.prestation p
		    WHERE (:startDate IS NULL OR r.idRDV >= :startDate)
		      AND (:endDate IS NULL OR r.date <= :endDate)
		    GROUP BY p.nom
		    ORDER BY COUNT(r.idRDV) DESC
		""")
		List<PrestationStatDTO> findTopPrestations(
		    @Param("startDate") LocalDate startDate,
		    @Param("endDate") LocalDate endDate
		);
	 @Query(value = """
		        SELECT new DTO.ChiffreAffairesMensuelDTO(
		            MONTH(r.date),
		            SUM(p.tarif)
		        )
		        FROM RendezVous r
		        JOIN r.prestation p
		        WHERE YEAR(r.date) = :year
		          AND r.statut = 'CONFIRME'
		        GROUP BY MONTH(r.date)
		        ORDER BY MONTH(r.date)
		        """)
		    List<ChiffreAffairesMensuelDTO> getChiffreAffairesMensuel(@Param("year") int year);
	 
	 @Query("SELECT new DTO.RendezVousEmployeStatDTO(e.nom, COUNT(r.idRDV)) " +
	           "FROM RendezVous r " +
	           "JOIN r.employe e " +
	           "WHERE YEAR(r.date) = :annee " +
	           "GROUP BY e.nom " +
	           "ORDER BY COUNT(r.idRDV) DESC")
	    List<RendezVousEmployeStatDTO> getStatsRendezVousParEmploye(@Param("annee") int annee);
	 
	 @Query(value = """
		        SELECT new DTO.TopRendezvous(
		            MONTH(r.date),
		            COUNT(r.idRDV)
		        )
		        FROM RendezVous r
		        WHERE YEAR(r.date) = :year
		        GROUP BY MONTH(r.date)
		        ORDER BY MONTH(r.date)
		        """)
	 
		    List<TopRendezvous> getTopRendezvous(@Param("year") int year);
	 @Transactional
	 void deleteByClient_IdClient(Long clientId);
	   
}
