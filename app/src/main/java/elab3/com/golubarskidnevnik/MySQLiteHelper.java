package elab3.com.golubarskidnevnik;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import elab3.com.golubarskidnevnik.Ekipe.Ekipa;
import elab3.com.golubarskidnevnik.Ekipe.EkipeFragment;
import elab3.com.golubarskidnevnik.Golubovi.Golub;
import elab3.com.golubarskidnevnik.Golubovi.ObrisiGoluba;
import elab3.com.golubarskidnevnik.Letovi.GoluboviULetu;
import elab3.com.golubarskidnevnik.Letovi.Let;

public class MySQLiteHelper extends SQLiteOpenHelper
{
    public MySQLiteHelper(Context context) {
        super(context, "golubarstvo", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE golub ( " +
                "id TEXT PRIMARY KEY, " +
                "boja TEXT," +
                "dodatak TEXT, "+
                "bojaAlke TEXT," +
                "pol TEXT," +
                "ekipa INTEGER )";

        db.execSQL(sql);

        sql="CREATE TABLE let(" +
                "datum DATETIME PRIMARY KEY,\n" +
                "pritisak [numeric](6,2)," +
                "vetar [nchar](15) ," +
                "vlaga [numeric](6,2) ," +
                "temperatura [numeric](6,2)," +
                "opis [nchar](100) ," +
                "prosek [numeric](5, 2) " +
                " )";

        db.execSQL(sql);

        sql="CREATE TABLE goluboviULetu(" +
                "idGoluba [nchar](10)  references golub(id)," +
                "\t[idLeta] datetime references let(datum)," +
                "\t[duzina] [numeric](5, 2),\n" +
                "\t[vezanost] [nchar](15),\n" +
                "\t[rBrLeta] INTEGER," +
                "\t[opis] [nchar](10)," +
                "[razmak] int," +
                "[vremeSletanja] [nchar](20)," +
                "primary key (idGoluba,idLeta)" +
                ")";

        db.execSQL(sql);
        sql="CREATE TABLE ekipa(" +
                "idEkipe INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "nazivEkipe nchar(10)" +
                ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS golub");
        db.execSQL("DROP TABLE IF EXISTS let");
        this.onCreate(db);
    }

    //region Golubovi
        public void dodajGoluba(Golub golub){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", golub.getBrojAlke());
        values.put("boja", golub.getBoja());
        values.put("dodatak", golub.getDodatak());
        values.put("bojaAlke", golub.getBojaAlke());
        values.put("pol", golub.getPol());
        values.put("ekipa", golub.getEkipa());



        db.insert("golub",
                null,
                values);

        db.close();

    }

    public List<Golub> dajSveGolubove(String uslov) {
        List<Golub> golubovi = new LinkedList<>();

        String query = "SELECT  * FROM golub" + uslov +" ORDER BY bojaAlke asc";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        Golub golub = null;
        while (cursor.moveToNext()) {
            golub = new Golub (cursor.getString(0),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5));

            golubovi.add(golub);
        }

        return golubovi;
    }

    public int updateGolub(Golub golub){
        // Referenca ka bazi podataka.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("dodatak",golub.getDodatak());
        values.put("bojaAlke",golub.getBojaAlke());
        values.put("pol",golub.getPol());
        values.put("ekipa",golub.getEkipa());
        values.put("boja",golub.getBoja());


        int i = db.update("golub",
                values,
                "id = ?",
                new String[] { String.valueOf(golub.getKljuc()) });


        db.close();

        return i;
    }

    public ArrayList<Let> vratiLetoveZaGoluba(Golub golub){
        ArrayList<Let> letovi= new ArrayList<>();
        ArrayList<GoluboviULetu> istorije=new ArrayList<>();

        String query = "SELECT  * FROM goluboviULetu WHERE idGoluba='" + golub.getKljuc() +"' ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        GoluboviULetu golubULetu = null;
        while (cursor.moveToNext()) {
            istorije=new ArrayList<>();
            String letID= cursor.getString(1);
            Let let= new Let(null,0.0,null,0.0,0,null,0);
            String query2= "SELECT * from let WHERE datum='"+letID+"'";
            Cursor cursor2= db.rawQuery(query2,null);
            while(cursor2.moveToNext()){
                let= new Let(cursor2.getString(0),cursor2.getDouble(1),
                        cursor2.getString(2),cursor2.getDouble(3),cursor2.getDouble(4),cursor2.getString(5),cursor2.getDouble(6));
            }

            golubULetu = new GoluboviULetu (golub,let,
                    cursor.getDouble(2),cursor.getString(3),cursor.getInt(4),cursor.getString(5),cursor.getInt(6),cursor.getString(7));

            istorije.add(golubULetu);
            let.setGoluboviULetu(istorije);
            letovi.add(let);
        }


        return letovi;
    }
//endregion

    //region Let

    public int vratiRazmak(Golub golub,String datum){
        String query="Select julianday(date('"+datum+"'))-julianday(date(max(idLeta))) from GoluboviULetu where idGoluba='"+golub.getKljuc()+"'";

        Log.d("DATUM",query);

        SQLiteDatabase db = this.getReadableDatabase();
        int broj=0;
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            broj=cursor.getInt(0);
        }

        return broj;

    }

    public boolean dodajLet(Let let){

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("datum", let.getDatum());
            values.put("pritisak", let.getPritisak());
            values.put("vetar", let.getVetar());
            values.put("vlaga", let.getVlaga());
            values.put("temperatura", let.getTemperatura());
            values.put("opis", let.getOpis());
            values.put("prosek", let.getProsek());



            db.insert("let",
                    null,
                    values);

            db.close();
            return true;
        }catch (Exception e){
            return false;
        }


    }

    public boolean dodajGoluboveULetu(List<GoluboviULetu> goluboviULetu){

        for (GoluboviULetu golub:goluboviULetu
             ) {

            try {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("idGoluba", golub.getGolub().getBrojAlke());
                values.put("idLeta", golub.getLet().getDatum());
                values.put("duzina", golub.getDuzina());
                values.put("vezanost", golub.getVezanost());
                values.put("rBrLeta", golub.getrBrLeta());
                values.put("opis", golub.getOpis());
                values.put("razmak",golub.getRazmak());
                values.put("vremeSletanja",golub.getVremeSletanja());



                db.insert("goluboviULetu",
                        null,
                        values);

                db.close();
            }catch (Exception e){
                return false;
            }

        }

    return  true;

    }
    public ArrayList<Let> dajSveLetove(String uslov) {
        ArrayList<Let> letovi = new ArrayList<>();

        String query = "SELECT  * FROM let " + uslov +" ORDER BY datum DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        Let let = null;
        while (cursor.moveToNext()) {
            let = new Let (cursor.getString(0),cursor.getDouble(1),
                    cursor.getString(2),cursor.getDouble(3),cursor.getDouble(4),cursor.getString(5),cursor.getDouble(6));

            letovi.add(let);
        }
        for (int i=0; i<letovi.size();i++
             ) {
            ArrayList<GoluboviULetu> golubovi= new ArrayList<GoluboviULetu>();
            query= "SELECT * from goluboviULetu where idleta='"+letovi.get(i).getDatum()+"'";
            cursor= db.rawQuery(query,null);
            while(cursor.moveToNext()){
                double duzina= cursor.getDouble(2);
                String vezanost= cursor.getString(3);
                int rbr= cursor.getInt(4);
                String opis=cursor.getString(5);
                int razmak= cursor.getInt(6);
                String vremeSletanja=cursor.getString(7);
                String query2= "SELECT * from golub where id='"+cursor.getString(0)+"'";
                Cursor cursor2=db.rawQuery(query2,null);
                while (cursor2.moveToNext()) {
                    Golub golub = new Golub(cursor2.getString(0), cursor2.getString(1),
                            cursor2.getString(2), cursor2.getString(3), cursor2.getString(4), cursor2.getInt(5));
                    GoluboviULetu gg = new GoluboviULetu(golub, let, duzina, vezanost, rbr, opis,razmak,vremeSletanja);

                    golubovi.add(gg);
                }
            }
            letovi.get(i).setGoluboviULetu(golubovi);

        }

        return letovi;
    }

    public int updateGoluboviULetu(GoluboviULetu goluboULetu){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("vezanost", goluboULetu.getVezanost());
        values.put("opis", goluboULetu.getOpis());


        int i = db.update("goluboviULetu",
                values,
                "idLeta = ? and idGoluba = ?",
                new String[] { String.valueOf(goluboULetu.getLet().getDatum()), String.valueOf(goluboULetu.getGolub().getKljuc()) });


        db.close();

        return i;

    }



    //endregion
    public Integer obrisiElement(String tabela, String kolonaKljuc, String kljucZaBrisanje){


        // Referenca ka bazi podataka.
        SQLiteDatabase db = this.getWritableDatabase();

        // Upit glasi DELETE FROM studenti WHERE id = 4(ili koji god broj da prosleđen metodi)
        int rez=db.delete(tabela,
                kolonaKljuc+"= ?",
                new String[] { kljucZaBrisanje });

        db.close();
        if(tabela.equals("golub")){
            obrisiElement("goluboviULetu","idGoluba", kljucZaBrisanje);
        }
        return rez;

    }


    public int dodajVreme(GoluboviULetu golub){
        // Referenca ka bazi podataka.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("duzina", golub.getDuzina());
        values.put("vremeSletanja", golub.getVremeSletanja());


        int i = db.update("goluboviULetu",
                values,
                "idLeta = ? and idGoluba = ?",
                new String[] { String.valueOf(golub.getLet().getDatum()), String.valueOf(golub.getGolub().getKljuc()) });


        db.close();

        return i;


    }

    public int dajRbrULetu(Golub golub){

        String query = "SELECT  MAX(rBrLeta) FROM goluboviULetu where idGoluba='"+golub.getKljuc()+"'";
        SQLiteDatabase db= this.getReadableDatabase();
        int rbr=0;



            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
               rbr = cursor.getInt(0);
            }
            return  rbr+1;





    }


    public int updateLet(Let let){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("vetar", let.getVetar());
        values.put("vlaga", let.getVlaga());
        values.put("temperatura", let.getTemperatura());
        values.put("opis", let.getOpis());
        values.put("pritisak", let.getPritisak());


        int i = db.update("let",
                values,
                "datum = ?",
                new String[] { String.valueOf(let.getDatum()) });


        db.close();

        return i;


    }
    public int updateProsekULetu(Let let) {

              // Referenca ka bazi podataka.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("prosek", let.getProsek());


        int i = db.update("let",
                values,
                "datum = ?",
                new String[] { String.valueOf(let.getDatum()) });


        db.close();

        return i;

    }

    //region Ekipe

    public ArrayList<Ekipa> vratiSveEkipe(){
        ArrayList<Ekipa> listaEkipa= new ArrayList<Ekipa>();

        String query = "SELECT  * FROM ekipa";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        Golub golub = null;
        while (cursor.moveToNext()) {

            Ekipa ekipa = new Ekipa (cursor.getInt(0),cursor.getString(1),new ArrayList<Golub>());
            ArrayList<Golub> golubovi= new ArrayList<>(dajSveGolubove(" WHERE ekipa="+ekipa.getIdEkipe()));
            ekipa.setListaGolubova(golubovi);
            listaEkipa.add(ekipa);

        }


        return  listaEkipa;
    }
    public String vratiEkipuZaGoluba(Golub golub){
        String ekipa="-";
        String query = "SELECT  nazivEkipe FROM ekipa where idEkipe='"+golub.getEkipa()+"'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        while (cursor.moveToNext()) {

            ekipa=cursor.getString(0);
        }


        return  ekipa;
    }

    public boolean dodajEkipu(String naziv){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nazivEkipe", naziv);




        db.insert("ekipa",
                null,
                values);

        db.close();
        return true;

    }
    //endregion




}
