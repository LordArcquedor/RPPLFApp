package rp.plf;

public class MatchRP {
    String auteur, nomMatch, lien;

    public MatchRP() {
    }

    public MatchRP(String auteur, String nomMatch, String lien) {
        this.auteur = auteur;
        this.nomMatch = nomMatch;
        this.lien = lien;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getNomMatch() {
        return nomMatch;
    }

    public void setNomMatch(String nomMatch) {
        this.nomMatch = nomMatch;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }
}
