package elab3.com.golubarskidnevnik.Letovi;

import java.io.Serializable;

import elab3.com.golubarskidnevnik.Golubovi.Golub;

/*

"idGoluba [nchar](10) primary key references golub(id)," +
                "\t[idLeta] [nchar](15) primary key references let(datum)," +
                "\t[duzina] [numeric](5, 2),\n" +
                "\t[vezanost] [nchar](10),\n" +
                "\t[rBrLeta] [nchar](10)," +
                "\t[opis] [nchar](10)" +
 */
public class GoluboviULetu implements Serializable {

    Golub golub;
    Let let;
    double duzina;
    String vezanost;
    int rBrLeta;
    String opis;
    int razmak;
    String vremeSletanja;

    public GoluboviULetu(Golub golub, Let let, double duzina, String vezanost, int rBrLeta, String opis, int razmak, String vremeSletanja) {
        this.golub = golub;
        this.let = let;
        this.duzina = duzina;
        this.vezanost = vezanost;
        this.rBrLeta = rBrLeta;
        this.opis = opis;
        this.razmak=razmak;
        this.vremeSletanja= vremeSletanja;
    }

    public String getVremeSletanja() {
        return vremeSletanja;
    }

    public void setVremeSletanja(String vremeSletanja) {
        this.vremeSletanja = vremeSletanja;
    }

    public int getRazmak() {
        return razmak;
    }

    public void setRazmak(int razmak) {
        this.razmak = razmak;
    }

    public Golub getGolub() {
        return golub;
    }

    public void setGolub(Golub golub) {
        this.golub = golub;
    }

    public Let getLet() {
        return let;
    }

    public void setLet(Let let) {
        this.let = let;
    }

    public double getDuzina() {
        return duzina;
    }

    public void setDuzina(double duzina) {
        this.duzina = duzina;
    }

    public String getVezanost() {
        return vezanost;
    }

    public void setVezanost(String vezanost) {
        this.vezanost = vezanost;
    }

    public int getrBrLeta() {
        return rBrLeta;
    }

    public void setrBrLeta(int rBrLeta) {
        this.rBrLeta = rBrLeta;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
