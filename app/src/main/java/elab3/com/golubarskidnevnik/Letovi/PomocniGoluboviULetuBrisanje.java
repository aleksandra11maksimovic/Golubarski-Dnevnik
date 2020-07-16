package elab3.com.golubarskidnevnik.Letovi;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Calendar;

import elab3.com.golubarskidnevnik.Golubovi.FUF;
import elab3.com.golubarskidnevnik.MainActivity;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class PomocniGoluboviULetuBrisanje extends Activity {

    Button bObrisi;
    Button bIzmeni;

    Button bDodajVreme;
    GoluboviULetu golub;
    ImageButton save;
    Button btnNijeSleteo;
    TextView txt;
    EditText unos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomocni_golubovi_u_letu);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width= dm.widthPixels;
        int height= dm.heightPixels;
        getWindow().setLayout((int)(width*.7),(int)(height*.4));
        golub= (GoluboviULetu) getIntent().getSerializableExtra("Golub");

        save= findViewById(R.id.btnSaveNijeSleteo);
        btnNijeSleteo=findViewById(R.id.buttonNijeLeteo);
        txt= findViewById(R.id.textView18);
        unos=findViewById(R.id.etNijeSleteo);





        bObrisi= findViewById(R.id.buttonObrisiGUL);
        bIzmeni=findViewById(R.id.buttonIzmeniGUL);
        bDodajVreme= findViewById(R.id.buttonDodajVremeGUL);

        bDodajVreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar= Calendar.getInstance();
                int hour=calendar.get(Calendar.HOUR);
                int MINUTE=0;
                TimePickerDialog timePickerDialog= new TimePickerDialog(PomocniGoluboviULetuBrisanje.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        final  String vreme;


                        int min=0;
                        int sati=0;
                        if(golub.getLet().getDatum()!=null&&golub.getLet().getDatum().length()>11){
                            vreme = golub.getLet().getDatum().substring(11);
                            Log.d("VREME",vreme);
                            sati= Integer.parseInt(vreme.substring(0,2));
                            min= Integer.parseInt(vreme.substring(3,5));

                        }

                        double resenje= FUF.izracunajVremeIzmedju(sati, min,i, i1);
                        Toast.makeText(PomocniGoluboviULetuBrisanje.this, resenje+"",Toast.LENGTH_SHORT).show();
                        MySQLiteHelper db= new MySQLiteHelper(PomocniGoluboviULetuBrisanje.this);
                        golub.setDuzina(resenje);
                        golub.setVremeSletanja(i+"."+i1);
                        db.dodajVreme(golub);
                        finish();


                    }
                }, hour,MINUTE,true);
                timePickerDialog.show();
            }
        });


        btnNijeSleteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setVisibility(View.VISIBLE);
                unos.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                golub.setDuzina(0);
                golub.setVremeSletanja(unos.getText().toString());
                MySQLiteHelper db= new MySQLiteHelper(PomocniGoluboviULetuBrisanje.this);
                db.dodajVreme(golub);
                finish();

            }
        });


        bIzmeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PomocniGoluboviULetuBrisanje.this, DodajGoluboviULetu.class);
                intent.putExtra("Intent","Izmena");
                intent.putExtra("Let",golub.getLet());
                intent.putExtra("Golub",golub.getGolub());
                intent.putExtra("GolubULetu",golub);

                startActivity(intent);
            }
        });

    }
}
