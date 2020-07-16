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

import java.util.List;

import elab3.com.golubarskidnevnik.Ekipe.Ekipa;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class DodajGoluba extends Activity{


    RadioGroup pol;
    EditText bojaAlke;
    EditText brojAlke;
    String[] muzjaci = { "Mavijan", "Arap","Tekir", "Darčin", "Boz", "Bakarlija","Šećerlija","Šeš","Beaz","Karuđan","Kulan","Saliter","Telebon","Barak","Kaplan","Krzal","Čapar","Krzal Boz","Silber","Naudijan"};
    String[] zenke = { "Mavijanka", "Arapka", "Tekirka","Darčinka", "Boska", "Bakarlinka","Šećerlinka","Šeška","Beazka","Karuđanka","Kulanka","Saliterka","Telebonka","Barka","Kaplanka","Krzalka","Čaparka","Krzal Boska","Silberka","Naudijanka"};
    String[] dodatak = { "Duz","Špic","Jednošpic","Plitkošpic","Ubodeni","Šarenokrili","Peckavi","Belorepi","Šarenorepi"};

    List<Ekipa> sveEkipe;
    RadioGroup rg;
    Button dodaj;

    ArrayAdapter bojaAdapter;
    ArrayAdapter ekipeAdapter;
    ArrayAdapter dodatakAdapter;
    Spinner spinner;
    Spinner spinner2;
    Spinner spinner3;


    Golub golubZaIzmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_goluba);

        dodaj= findViewById(R.id.button);
        rg=findViewById(R.id.radioGroup);
        brojAlke=findViewById(R.id.etBrAlke);
        bojaAlke= findViewById(R.id.etBojaAlke);
        final String intent=getIntent().getStringExtra("intent");
        if(intent.equals("izmeni")){
            golubZaIzmenu= (Golub) getIntent().getSerializableExtra("golub");
            dodaj.setText("Izmeni");
        }
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(intent!=null&&intent.equals("dodaj")){

                    dodajGoluba();
                }

                if(intent!=null&&intent.equals("izmeni")){

                   izmeniGoluba();
                }
            }
        });


        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width= dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int)(width*.85),(int)(height*.5));

        spinner= findViewById(R.id.spinner);
        spinner2= findViewById(R.id.spinner2);
        spinner3= findViewById(R.id.spinner3);
        MySQLiteHelper db= new MySQLiteHelper(this);
        sveEkipe=db.vratiSveEkipe();
        sveEkipe.add(0, new Ekipa(0,"-",null));
        ekipeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sveEkipe);
        spinner3.setAdapter(ekipeAdapter);
        dodatakAdapter = new ArrayAdapter(DodajGoluba.this,
                android.R.layout.simple_spinner_dropdown_item,
                dodatak);
        spinner2.setAdapter(dodatakAdapter);
        pol = (RadioGroup) findViewById(R.id.radioGroup);
        pol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.radioButton:
                        bojaAdapter = new ArrayAdapter(DodajGoluba.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                muzjaci);
                        spinner.setAdapter(bojaAdapter);
                        break;
                    case R.id.radioButton2:
                        bojaAdapter = new ArrayAdapter(DodajGoluba.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                zenke);
                        spinner.setAdapter(bojaAdapter);
                        break;

                }
            }
        });

        if(intent.equals("izmeni")){
            podesiZaIzmenu();
        }

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
        if(rg.getCheckedRadioButtonId()==R.id.radioButton){
            pol="m";
        }else {
            pol="z";
        }
        Ekipa ekipa= (Ekipa)spinner3.getSelectedItem();

        Golub golub=new Golub(brojAlke.getText().toString(), spinner.getSelectedItem().toString(),
                spinner2.getSelectedItem().toString(),
                bojaAlke.getText().toString(),
                pol,
                ekipa.getIdEkipe()
                );
                db.dodajGoluba(golub);
        Log.d("Provera",db.dajSveGolubove("").get(0).getBoja());
                this.finish();
    }

    public void izmeniGoluba(){

        String pol="n";
        MySQLiteHelper db= new MySQLiteHelper(this);
        if(bojaAlke.getText().toString().equals("")){
            Toast.makeText(this, "Unesite boju alke",Toast.LENGTH_SHORT).show();
            return;
        }

        if(rg.getCheckedRadioButtonId()==-1){
            Toast.makeText(this, "Niste odabrali pol, boju i dodatak",Toast.LENGTH_SHORT).show();
            return;
        }
        if(rg.getCheckedRadioButtonId()==R.id.radioButton){
            pol="m";
        }else {
            pol="z";
        }
        Ekipa ekipa= (Ekipa)spinner3.getSelectedItem();

        Golub golub=new Golub(brojAlke.getText().toString(), spinner.getSelectedItem().toString(),
                spinner2.getSelectedItem().toString(),
                bojaAlke.getText().toString(),
                pol,
                ekipa.getIdEkipe()
        );
        if(db.updateGolub(golub)>0){
         Toast.makeText(this, "Uspešno izmenjen golub", Toast.LENGTH_SHORT).show();
        }

        this.finish();


    }

    public void podesiZaIzmenu(){
        brojAlke.setEnabled(false);
        brojAlke.setText(golubZaIzmenu.getBrojAlke());
        bojaAlke.setText(golubZaIzmenu.getBojaAlke());
        if(golubZaIzmenu.getPol().equals("m")){
        pol.check(R.id.radioButton); }else
            pol.check(R.id.radioButton2);
        spinner.setSelection(bojaAdapter.getPosition(golubZaIzmenu.getBoja()));
        spinner2.setSelection(dodatakAdapter.getPosition(golubZaIzmenu.getDodatak()));
        spinner3.setSelection(ekipeAdapter.getPosition(golubZaIzmenu.getEkipa()));

    }




}
