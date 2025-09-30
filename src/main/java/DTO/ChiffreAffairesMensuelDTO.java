package DTO;

public class ChiffreAffairesMensuelDTO {
    private int mois;
    private double totalChiffreAffaires;

    public ChiffreAffairesMensuelDTO(int mois, double totalChiffreAffaires) {
        this.mois = mois;
        this.totalChiffreAffaires = totalChiffreAffaires;
    }

    public int getMois() {
        return mois;
    }

    public double getTotalChiffreAffaires() {
        return totalChiffreAffaires;
    }
}
