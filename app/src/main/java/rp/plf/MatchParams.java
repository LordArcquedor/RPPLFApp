package rp.plf;

public class MatchParams {
    private String team1;
    private String team2;
    private String vainqueur;

    public MatchParams(String team1, String team2, String vainqueur) {
        this.team1 = team1;
        this.team2 = team2;
        this.vainqueur = vainqueur;
    }

    public MatchParams(){

    }
    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getvainqueur() {
        return vainqueur;
    }

    public void setvainqueur(String vainqueur) {
        this.vainqueur = vainqueur;
    }

    // Constructeur, getters, setters
}