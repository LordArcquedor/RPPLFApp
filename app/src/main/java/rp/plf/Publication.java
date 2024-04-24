package rp.plf;

public class Publication {
    private String titre, auteur,contenu, image, type;
    String date;



    public Publication(String titre, String auteur, String contenu, String image, String type, String date) {
        this.auteur = auteur;
        this.titre = titre;
        this.contenu = contenu;
        this.image = image;
        this.type = type;
        this.date = date;
    }

    public Publication() {
        // Constructeur sans argument requis par Firebase Realtime Database
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
