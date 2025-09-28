package DTO;

public class RendezVousEmployeStatDTO {
    private String nomEmploye;
    private Long totalRendezVous;

    public RendezVousEmployeStatDTO(String nomEmploye, Long totalRendezVous) {
        this.nomEmploye = nomEmploye;
        this.totalRendezVous = totalRendezVous;
    }

    public String getNomEmploye() {
        return nomEmploye;
    }

    public Long getTotalRendezVous() {
        return totalRendezVous;
    }
}
