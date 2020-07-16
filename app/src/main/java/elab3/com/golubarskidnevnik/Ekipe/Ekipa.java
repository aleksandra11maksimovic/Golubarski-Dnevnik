package elab3.com.golubarskidnevnik.Ekipe;

import java.util.ArrayList;

import elab3.com.golubarskidnevnik.Golubovi.Golub;

public class Ekipa {

    private int idEkipe;
    private String naziv;
    ArrayList<Golub> listaGolubova;

    public Ekipa(int idEkipe, String naziv, ArrayList<Golub> listaGolubova) {
        this.idEkipe = idEkipe;
        this.naziv = naziv;
        this.listaGolubova = listaGolubova;
    }

    public int getIdEkipe() {
        return idEkipe;
    }

    public void setIdEkipe(int idEkipe) {
        this.idEkipe = idEkipe;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ArrayList<Golub> getListaGolubova() {
        return listaGolubova;
    }

    public void setListaGolubova(ArrayList<Golub> listaGolubova) {
        this.listaGolubova = listaGolubova;
    }

    @Override
    public String toString() {
        return this.naziv;
    }
}
