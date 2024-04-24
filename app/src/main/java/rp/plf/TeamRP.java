package rp.plf;

public class TeamRP {
    String nomEquipe, lien, paste, auteur;
    public TeamRP(){
    }

    public TeamRP(String auteur, String paste, String lien, String nomEquipe) {
        this.auteur = auteur;
        this.nomEquipe = nomEquipe;
        this.lien = lien;
        this.paste = paste;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getPaste() {
        return paste;
    }

    public void setPaste(String paste) {
        this.paste = paste;
    }
}
