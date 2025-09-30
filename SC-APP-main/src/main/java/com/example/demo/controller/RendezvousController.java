package com.example.demo.controller;

import com.example.demo.entity.Employe;
import com.example.demo.entity.RendezVous;
import com.example.demo.service.RendezvousService;
import com.example.demo.repository.RendezvousRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/rendezvous")
@CrossOrigin(origins = "http://localhost:4200")
public class RendezvousController {

    private final RendezvousService service;
    private final RendezvousRepository repo;

    public RendezvousController(RendezvousService s, RendezvousRepository r) {
        this.service = s; this.repo = r;
    }

    // Création
    @PostMapping("/create")
    public RendezVous create(@RequestBody CreateRdvDto dto) {
        return service.creerRdv(dto.clientId, dto.employeId, dto.prestationId, dto.date, dto.heure);
    }

    // Occupation d'une journée (pour l'employé)
    @GetMapping("/occupation")
    public List<OccupationDto> occupation(@RequestParam Long employeId, @RequestParam String date) {
        var d = LocalDate.parse(date);
        var list = repo.findByEmployeAndDate(new Employe(){{
            setId(employeId);
        }}, d);

        var out = new ArrayList<OccupationDto>();
        for (var r : list) {
            var o = new OccupationDto();
            o.heureDebut = r.getHeure().toString();
            o.heureFin   = r.getHeureFin().toString();
            o.prestation = r.getPrestation().getNom();
            out.add(o);
        }
        return out;
    }

    public static class CreateRdvDto {
        public Long clientId;
        public Long employeId;
        public Long prestationId;
        public LocalDate date;
        public LocalTime heure;
    }

    public static class OccupationDto {
        public String heureDebut;
        public String heureFin;
        public String prestation;
    }
}
