package rp.plf.Modele;

public class Match {
    String champion, adversaire, etat, date, heure;

    public Match(String champion, String adversaire, String etat, String date, String heure) {
        this.champion = champion;
        this.adversaire = adversaire;
        this.etat = etat;
        this.date = date;
        this.heure = heure;
    }

    public Match() {
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public String getAdversaire() {
        return adversaire;
    }

    public void setAdversaire(String adversaire) {
        this.adversaire = adversaire;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
}
