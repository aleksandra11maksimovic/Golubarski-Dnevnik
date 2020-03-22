package elab3.com.golubarskidnevnik.Golubovi;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class DodajGoluba extends Activity{


    RadioGroup pol;
    EditText bojaAlke;
    EditText brojAlke;
    String[] muzjaci = { "Mavijan", "Arap", "Darčin", "Boz", "Bakarlija","Šećerlija","Šeš","Beaz","Karudijan","Kulan","Salicer","Telebon","Barak","Kaplan","Krzal","Čapar","Krzal Boz"};
    String[] zenke = { "Mavijanka", "Arapka", "Darčinka", "Boska", "Bakarlinka","Šećerlinka","Šeška","Beazka","Karudijanka","Kulanka","Salicerka","Telebonka","Barka","Kaplanka","Krzalka","Čaparka","Krzal Boska"};
    String[] dodatak = { "Duz","Špic","Jednošpic"};
    RadioGroup rg;
    Button dodaj;

    Spinner spinner;
    Spinner spinner2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_goluba);

        dodaj= findViewById(R.id.button);
        rg=findViewById(R.id.radioGroup);
        brojAlke=findViewById(R.id.etBrAlke);
        bojaAlke= findViewById(R.id.etBojaAlke);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dodajGoluba();
            }
        });


        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width= dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int)(width*.85),(int)(height*.5));

        spinner= findViewById(R.id.spinner);
        spinner2= findViewById(R.id.spinner2);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(DodajGoluba.this,
                android.R.layout.simple_spinner_dropdown_item,
                dodatak);
        spinner2.setAdapter(spinnerArrayAdapter);
        pol = (RadioGroup) findViewById(R.id.radioGroup);
        pol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.radioButton:
                        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(DodajGoluba.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                muzjaci);
                        spinner.setAdapter(spinnerArrayAdapter);
                        break;
                    case R.id.radioButton2:
                        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter(DodajGoluba.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                zenke);
                        spinner.setAdapter(spinnerArrayAdapter2);
                        break;

                }
            }
        });

    }
    public void dodajGoluba(){
        MySQLiteHelper db= new MySQLiteHelper(this);
        String pol="n";
        if(brojAlke.getText().toString().equals("")){
            Toast.makeText(this, "Unesite broj alke",Toast.LENGTH_SHORT).show();
            return;
        }
        if(db.dajSveGolubove(" WHERE id='"+brojAlke.getText().toString()+"'").size()>0){
            Toast.makeText(this,"Već postoji golub sa ovom alkom, promenite broj alke",Toast.LENGTH_SHORT).show();
            return;
        }
        if(bojaAlke.getText().toString().equals("")){
            Toast.makeText(this, "Unesite boju alke",Toast.LENGTH_SHORT).show();
            return;
        }

        if(rg.getCheckedRadioButtonId()==-1){
            Toast.makeText(this, "Niste odabrali pol, boju i dodatak",Toast.LENGTH_SHORT).show();
            return;
        }
        if(rg.getCheckedRadioButtonId()==0){
            pol="m";
        }else {
            pol="z";
        }


        Golub golub=new Golub(brojAlke.getText().toString(), spinner.getSelectedItem().toString(),
                spinner2.getSelectedItem().toString(),
                bojaAlke.getText().toString(),
                pol,
                0
                );
                db.dodajGoluba(golub);
        Log.d("Provera",db.dajSveGolubove("").get(0).getBoja());
                this.finish();
    }






}
