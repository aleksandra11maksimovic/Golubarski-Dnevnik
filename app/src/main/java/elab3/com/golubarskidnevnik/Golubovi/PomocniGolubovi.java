package elab3.com.golubarskidnevnik.Golubovi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import elab3.com.golubarskidnevnik.Ekipe.Ekipa;
import elab3.com.golubarskidnevnik.Letovi.PrikazLeta;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class PomocniGolubovi extends AppCompatActivity implements View.OnClickListener {

    Golub golub;
    Button prikazi;
    Button obrisi;
    Button dodajEkipu;
    Ekipa ekipa;

    Spinner spinnerEkipe;
    ImageButton promeniEkipu;

    public static final int REQUEST_CODE_BRISANJE=1;
    Button izmeni;
    ArrayList<Ekipa> sveEkipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomocni_golubovi);

        golub=(Golub)getIntent().getSerializableExtra("Golub");
        obrisi= findViewById(R.id.btnObrisiGoluba);
        prikazi= findViewById(R.id.btnPrikaziGoluba);
        dodajEkipu=findViewById(R.id.btnDodajEkipu);
        promeniEkipu=findViewById(R.id.btnPromeniEkipu);
        izmeni=findViewById(R.id.btnIzmeni);
        spinnerEkipe=findViewById(R.id.spinnerPromeniEkipu);

        obrisi.setOnClickListener(this);
        prikazi.setOnClickListener(this);
        dodajEkipu.setOnClickListener(this);
        izmeni.setOnClickListener(this);
        promeniEkipu.setOnClickListener(this);

        MySQLiteHelper db= new MySQLiteHelper(this);
        sveEkipe=db.vratiSveEkipe();
        sveEkipe.add(0, new Ekipa(0,"-",null));
        ArrayAdapter ekipeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sveEkipe);
        spinnerEkipe.setAdapter(ekipeAdapter);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnObrisiGoluba:
                Intent intent= new Intent(PomocniGolubovi.this, ObrisiGoluba.class);
                intent.putExtra("Golub", golub);
                intent.putExtra("intent",1);
                startActivityForResult(intent,REQUEST_CODE_BRISANJE);
                break;
            case R.id.btnDodajEkipu:
                spinnerEkipe.setVisibility(View.VISIBLE);
                promeniEkipu.setVisibility(View.VISIBLE);
                break;
            case R.id.btnPromeniEkipu:

                ekipa= (Ekipa)spinnerEkipe.getSelectedItem();
                golub.setEkipa(ekipa.getIdEkipe());
                MySQLiteHelper db= new MySQLiteHelper(this);
                if(db.updateGolub(golub)>0){
                  Toast.makeText(this,"Uspešno promenjena ekipa",Toast.LENGTH_SHORT).show();
                  finish();
                }
                break;
            case R.id.btnPrikaziGoluba:
                Intent intent1 = new Intent(PomocniGolubovi.this, PrikazGoluba.class);
                intent1.putExtra("Golub",golub);
                startActivity(intent1);
                break;
            case R.id.btnIzmeni:
                Intent intent2= new Intent(PomocniGolubovi.this, DodajGoluba.class);
                intent2.putExtra("intent","izmeni");
                intent2.putExtra("golub", golub);
                startActivity(intent2);
                break;




        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_BRISANJE){
            if(resultCode>0){
                Toast.makeText(this,"Uspešno obrisan golub",Toast.LENGTH_SHORT).show();
                finish();

            }
            else if(resultCode<0){

                Toast.makeText(this,"Brisanje nije uspelo",Toast.LENGTH_SHORT).show();
            }
        }


    }
}
