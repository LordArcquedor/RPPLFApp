package rp.plf;

public class HOF {
    public String nom, lienCarte;
    public int saison;

    public HOF(String nom, String lienCarte, int saison) {
        this.nom = nom;
        this.lienCarte = lienCarte;
        this.saison = saison;
    }

    public HOF() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLienCarte() {
        return lienCarte;
    }

    public void setLienCarte(String lienCarte) {
        this.lienCarte = lienCarte;
    }

    public int getSaison() {
        return saison;
    }

    public void setSaison(int saison) {
        this.saison = saison;
    }
}
