package elab3.com.golubarskidnevnik.Golubovi;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;

import java.security.cert.Extension;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class FUF {

    public static ArrayList<Golub> pretrazi(Context context,EditText etPretraga){
        String pretraga= etPretraga.getText().toString();
        String uslov= " WHERE id like '%"+pretraga+"%' OR bojaAlke like '%"+pretraga+"%' OR boja like '%"+pretraga+"%' OR dodatak like '%"+pretraga+"%'";
        MySQLiteHelper db= new MySQLiteHelper(context);
        ArrayList<Golub> golubovi=  new ArrayList<Golub>(db.dajSveGolubove(uslov));
        return golubovi;
    }
    public static ArrayList<Golub> poPolu(Context context, EditText etPretraga, int selektovanPol, ImageButton poPolu ){

        etPretraga.setText("");

        MySQLiteHelper db= new MySQLiteHelper(context);
        if(selektovanPol==0){
            selektovanPol++;
            poPolu.setBackgroundResource(R.drawable.togglem);
            ArrayList<Golub> golubovi=  new ArrayList<Golub>(db.dajSveGolubove(" WHERE pol='m'"));
            return golubovi;

        } else if(selektovanPol==1){
            selektovanPol++;
            poPolu.setBackgroundResource(R.drawable.togglez);
            db= new MySQLiteHelper(context);
            ArrayList<Golub> golubovi=  new ArrayList<Golub>(db.dajSveGolubove(" WHERE pol='z'"));
            return golubovi;

        }else{
            selektovanPol=0;
            poPolu.setBackgroundResource(R.drawable.togglesvi);
            ArrayList<Golub> golubovi=  new ArrayList<Golub>(db.dajSveGolubove(" "));
            return golubovi;
        }

    }

    public static String izCalendarUstring(Calendar datum){
        String dat="";
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date d= datum.getTime();

        return dateFormat.format(d);

    }
    public static Calendar izStringUCalendar(String dat){
        Calendar datum= Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try{
            Date d= new Date();
            d.parse(dat);
            datum.setTime(d);
            return  datum;

        }catch (Exception e){
            return null;
        }


    }
    public static double izracunajVremeIzmedju(int satiOd, int minutiOd, int satiDo, int minutiDo){
        double resenje=0;
        if(minutiDo<minutiOd){
            int min=60-minutiOd+minutiDo;
            int sati=satiDo-satiOd-1;
            if(min>60){
                min-=60;
                sati++;
            }
            double satid= (double)sati;
            resenje=satid+(min/100.0);
        }else{
            int min= minutiDo-minutiOd;
            int sati=satiDo-satiOd;
            if(min>60){
                sati+=1;
                min-=60;
            }
            resenje=(double)sati+min/100.0;
        }

        return  resenje;
    }

}
