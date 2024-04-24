package rp.plf;



public class MatchFireBase {
    String champion, date,heure;

    public MatchFireBase(String champion, String date, String heure) {
        this.champion = champion;
        this.date = date;
        this.heure = heure;
    }

    public MatchFireBase() {
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
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
