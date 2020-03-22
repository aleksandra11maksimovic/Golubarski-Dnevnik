package elab3.com.golubarskidnevnik.Golubovi;

import java.io.Serializable;

public class Golub implements Serializable {

    private String brojAlke;
    private String boja;
    private String dodatak;
    private String pol;
    private String bojaAlke;
    private int ekipa;


    public Golub(String brojAlke, String boja, String dodatak, String bojaAlke,String pol, int ekipa) {
        this.brojAlke = brojAlke;
        this.boja = boja;
        this.dodatak = dodatak;
        this.pol = pol;
        this.bojaAlke = bojaAlke;
        this.ekipa = ekipa;
    }

    public String getNazivKljuca(){return "id";}
    public String getKljuc(){return brojAlke;}
    public String getBrojAlke() {
        return brojAlke;
    }

    public void setBrojAlke(String brojAlke) {
        this.brojAlke = brojAlke;
    }

    public String getBoja() {
        return boja;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }

    public String getDodatak() {
        return dodatak;
    }

    public void setDodatak(String dodatak) {
        this.dodatak = dodatak;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getBojaAlke() {
        return bojaAlke;
    }

    public void setBojaAlke(String bojaAlke) {
        this.bojaAlke = bojaAlke;
    }

    public int getEkipa() {
        return ekipa;
    }

    public void setEkipa(int ekipa) {
        this.ekipa = ekipa;
    }
}
