package elab3.com.golubarskidnevnik.Letovi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import elab3.com.golubarskidnevnik.Golubovi.FUF;

public class Let implements Serializable, Comparable<Let> {

    /*
    "datum [nchar](15) PRIMARY KEY,\n" +
                "pritisak [nchar](10)," +
                "vetar [nchar](10) ," +
                "vlaga [nchar](10) ," +
                "temperatura [nchar](10)," +
                "opis [nchar](100) ," +
                "prosek [numeric](5, 2) " +
                " )";
     */

    private String datum;
    private double pritisak;
    private String vetar;
    private double vlaga;
    private double temperatura;
    private String opis;
    private double prosek;
    private ArrayList<GoluboviULetu> goluboviULetu= new ArrayList<>();

    public Let(String datum,double pritisak, String vetar, double vlaga, double temperatura, String opis, double prosek) {
        this.datum = datum;
        this.vetar = vetar;
        this.pritisak=pritisak;
        this.vlaga = vlaga;
        this.temperatura = temperatura;
        this.opis = opis;
        this.prosek=prosek;
    }

    public double getPritisak() {
        return pritisak;
    }

    public void setPritisak(double pritisak) {
        this.pritisak = pritisak;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getVetar() {
        return vetar;
    }

    public void setVetar(String vetar) {
        this.vetar = vetar;
    }

    public double getVlaga() {
        return vlaga;
    }

    public void setVlaga(double vlaga) {
        this.vlaga = vlaga;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getProsek() {
        return prosek;
    }

    public void setProsek(double prosek) {
        this.prosek = prosek;
    }

    public ArrayList<GoluboviULetu> getGoluboviULetu() {
        return goluboviULetu;
    }

    public void setGoluboviULetu(ArrayList<GoluboviULetu> goluboviULetu) {
        this.goluboviULetu = goluboviULetu;
    }

    @Override
    public int compareTo(Let let) {
        return FUF.izStringUCalendar(getDatum()).compareTo(FUF.izStringUCalendar(let.getDatum()));
    }
}
