package rp.plf.Modele;

public class Banniere {
    String Lien,Prix;

    public Banniere() {
    }

    public Banniere(String lien, String prix) {
        Lien = lien;
        Prix = prix;
    }

    public String getLien() {
        return Lien;
    }

    public void setLien(String lien) {
        Lien = lien;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }
}
