package rp.plf;


import java.util.ArrayList;

public class Utilisateur {
    String email, pseudo, role, infoD, photo, banniere, badge, uId, status,argent;
    ArrayList<String> mesBannieres,mesAvatars;
    public Utilisateur(String pseudo, String email, String role, String infoD, String photo, String banniere, String badge, ArrayList<String> mesBannieres, ArrayList<String> mesAvatars, String uId, String status, String argent){
        this.pseudo = pseudo;
        this.email = email;
        this.role = role;
        this.photo = photo;
        this.banniere = banniere;
        this.infoD = infoD;
        this.badge = badge;
        this.mesBannieres = mesBannieres;
        this.mesAvatars = mesAvatars;
        this.uId = uId;
        this.status = status;
        this.argent = argent;
//        this.userId= userID;
    }

    public Utilisateur(){

    }

    public String getArgent() {
        return argent;
    }

    public void setArgent(String argent) {
        this.argent = argent;
    }

    public ArrayList<String> getMesBannieres() {
        return mesBannieres;
    }

    public void setMesBannieres(ArrayList<String> mesBannieres) {
        this.mesBannieres = mesBannieres;
    }

    public String getuId() {
        return uId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public ArrayList<String> getMesAvatars() {
        return mesAvatars;
    }

    public void setMesAvatars(ArrayList<String> mesAvatars) {
        this.mesAvatars = mesAvatars;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInfoD() {
        return infoD;
    }

    public void setInfoD(String infoD) {
        this.infoD = infoD;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBanniere() {
        return banniere;
    }

    public void setBanniere(String banniere) {
        this.banniere = banniere;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }
}
