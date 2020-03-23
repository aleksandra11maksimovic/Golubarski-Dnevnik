package elab3.com.golubarskidnevnik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import elab3.com.golubarskidnevnik.Golubovi.Golub;
import elab3.com.golubarskidnevnik.Golubovi.ObrisiGoluba;

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
                "datum [datetime] PRIMARY KEY,\n" +
                "pritisak [nchar](10)," +
                "vetar [nchar](10) ," +
                "vlaga [nchar](10) ," +
                "temperatura [nchar](10)," +
                "opis [nchar](100) ," +
                "prosek [numeric](5, 2) " +
                " )";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS golub");
        db.execSQL("DROP TABLE IF EXISTS let");
        this.onCreate(db);
    }

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

        String query = "SELECT  * FROM golub" + uslov +" ORDER BY bojaAlke asc, id asc";

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

    public Integer obrisiElement(String tabela, String kolonaKljuc, String kljucZaBrisanje){


        // Referenca ka bazi podataka.
        SQLiteDatabase db = this.getWritableDatabase();

        // Upit glasi DELETE FROM studenti WHERE id = 4(ili koji god broj da prosleđen metodi)
        int rez=db.delete(tabela,
                kolonaKljuc+"= ?",
                new String[] { kljucZaBrisanje });

        db.close();
        return rez;

    }




}
