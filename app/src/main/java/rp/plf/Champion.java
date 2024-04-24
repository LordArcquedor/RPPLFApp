package rp.plf;

import java.util.ArrayList;


public class Champion {
    String nomChampion,lienImage,type;
    ArrayList<String> quiMeDefitEnCeMoment;
    public Champion(String nomChampion, String lienImage, String type, ArrayList<String> quiMeDefitEnCeMoment){
        this.nomChampion = nomChampion;
        this.lienImage = lienImage;
        this.type = type;
        this.quiMeDefitEnCeMoment = quiMeDefitEnCeMoment;
    }

    public String getNomChampion() {
        return nomChampion;
    }

    public void setNomChampion(String nomChampion) {
        this.nomChampion = nomChampion;
    }

    public String getLienImage() {
        return lienImage;
    }

    public void setLienImage(String lienImage) {
        this.lienImage = lienImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getQuiMeDefitEnCeMoment() {
        return quiMeDefitEnCeMoment;
    }

    public void setQuiMeDefitEnCeMoment(ArrayList<String> quiMeDefitEnCeMoment) {
        this.quiMeDefitEnCeMoment = quiMeDefitEnCeMoment;
    }
}
