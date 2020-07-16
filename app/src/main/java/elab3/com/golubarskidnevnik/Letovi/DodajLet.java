package elab3.com.golubarskidnevnik.Letovi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.MeasureFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import elab3.com.golubarskidnevnik.AdapterZaListViewGolubova;
import elab3.com.golubarskidnevnik.AdapterZaListViewGolubovaULetu;
import elab3.com.golubarskidnevnik.Ekipe.Ekipa;
import elab3.com.golubarskidnevnik.Golubovi.FUF;
import elab3.com.golubarskidnevnik.Golubovi.Golub;
import elab3.com.golubarskidnevnik.Golubovi.ObrisiGoluba;
import elab3.com.golubarskidnevnik.Golubovi.PomocniGolubovi;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;



public class DodajLet extends AppCompatActivity implements View.OnClickListener{

    public static final int REQUEST_CODE_DODAJ=2;
    ImageButton datePicker;
    ImageButton timePicker;
    Calendar c;
    DatePickerDialog dpd;
    Button dodajLet;
    Let let;
    int selektovanPol;
    Button zavrsi;

    Spinner spinnerEkipe;
    Date datum;
    TextView date;
    EditText etVlaga;
    EditText etVetar;
    int izbacen;
    AdapterZaListViewGolubova adapterZaListViewGolubova;
    EditText etTemperatura;
    EditText etPritisak;
    EditText etOpis;
    List<Golub> svigolubovi;
    List<GoluboviULetu> goluboviULetu;
    ConstraintLayout clDodavanjeIstorije;
    ListView listSviGolubovi;
    ListView listGoluboviULetu;

    Calendar krajnjiDatum;

    ArrayList<Ekipa> sveEkipe;


    EditText etPretraga;
    Button poPolu;

    AdapterZaListViewGolubovaULetu adapterZaListViewGolubovaULetu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_let);

        datum= new Date();
        goluboviULetu= new ArrayList<>();
        date= findViewById(R.id.textView4);
        clDodavanjeIstorije=findViewById(R.id.constraintIstorija);
        datePicker= findViewById(R.id.buttonDatePicker);
        datePicker.setOnClickListener(this);
        timePicker=findViewById(R.id.buttonTimePicker);
        timePicker.setOnClickListener(this);
        dodajLet=findViewById(R.id.buttonDodajLet);
        dodajLet.setOnClickListener(this);
        etOpis=findViewById(R.id.editText7);
        etVlaga=findViewById(R.id.etVlaga);
        etTemperatura=findViewById(R.id.etTemperatura);
        etPritisak=findViewById(R.id.etPritisak);
        etVetar=findViewById(R.id.etVetar);

        spinnerEkipe=findViewById(R.id.spinnerLetEkipe);
        MySQLiteHelper db= new MySQLiteHelper(this);
        sveEkipe=db.vratiSveEkipe();
        sveEkipe.add(0, new Ekipa(0,"-",null));
        ArrayAdapter ekipeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sveEkipe);
        spinnerEkipe.setAdapter(ekipeAdapter);


        spinnerEkipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!sveEkipe.get(i).getNaziv().equals("-")) {
                    svigolubovi = sveEkipe.get(i).getListaGolubova();
                    adapterZaListViewGolubova.setLg(new ArrayList<Golub>(svigolubovi));
                    MySQLiteHelper db2= new MySQLiteHelper(DodajLet.this);
                    for(Golub golub : svigolubovi){
                        goluboviULetu.add(new GoluboviULetu(golub,let,0,"Ne",db2.dajRbrULetu(golub),"",db2.vratiRazmak(golub,let.getDatum()),""));
                    }
                    svigolubovi= new ArrayList<Golub>();

                    adapterZaListViewGolubova.setLg(new ArrayList<Golub>(svigolubovi));
                    adapterZaListViewGolubova.notifyDataSetChanged();
                    adapterZaListViewGolubovaULetu.setLg(new ArrayList<GoluboviULetu>(goluboviULetu));
                    adapterZaListViewGolubovaULetu.notifyDataSetChanged();
                    izbaciPostojece();
                }else{
                    MySQLiteHelper db2= new MySQLiteHelper(DodajLet.this);
                    svigolubovi=db2.dajSveGolubove("");

                    izbaciPostojece();

                    adapterZaListViewGolubova.setLg(new ArrayList<Golub>(svigolubovi));
                    adapterZaListViewGolubova.notifyDataSetChanged();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        listSviGolubovi= findViewById(R.id.listviewZaDodavanje);
        listGoluboviULetu=findViewById(R.id.listviewDodati);
        zavrsi= findViewById(R.id.buttonZavrsiDodavanjeLeta);
        krajnjiDatum= Calendar.getInstance();

        db= new MySQLiteHelper(this);
        svigolubovi= db.dajSveGolubove("");
        adapterZaListViewGolubova= new AdapterZaListViewGolubova(this, new ArrayList<Golub>(svigolubovi));
        listSviGolubovi.setAdapter(adapterZaListViewGolubova);

        adapterZaListViewGolubova.notifyDataSetChanged();
        listSviGolubovi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                izbacen=i;
                Intent intent= new Intent(DodajLet.this, DodajGoluboviULetu.class);
                intent.putExtra("Golub", svigolubovi.get(i));
                intent.putExtra("Let", let);
                startActivityForResult(intent,REQUEST_CODE_DODAJ);
            }
        });
        listSviGolubovi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        adapterZaListViewGolubovaULetu= new AdapterZaListViewGolubovaULetu(this, new ArrayList<GoluboviULetu>(goluboviULetu));
        listGoluboviULetu.setAdapter(adapterZaListViewGolubovaULetu);
        adapterZaListViewGolubovaULetu.notifyDataSetChanged();
        listGoluboviULetu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(DodajLet.this, ObrisiGoluba.class);

                intent.putExtra("intent",2);
                intent.putExtra("pozicija",i);
                startActivityForResult(intent, PomocniGolubovi.REQUEST_CODE_BRISANJE);

                return true;

            }
        });
        listGoluboviULetu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        zavrsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySQLiteHelper db= new MySQLiteHelper(getApplicationContext());
                if(db.dodajGoluboveULetu(goluboviULetu)){

                    double suma=0;
                    int broj=0;
                    for(GoluboviULetu g:goluboviULetu){
                        suma+=g.getDuzina();
                        broj++;
                    }

                    double prosek=0;
                    if(broj>0){
                        prosek=suma/broj;

                    }
                    let.setProsek(prosek);
                    db.updateProsekULetu(let);



                    Toast.makeText(getApplicationContext(),"Uspesno dodat let",Toast.LENGTH_SHORT).show();
                    finish();
                }else{

                    Toast.makeText(getApplicationContext(),"Doslo je do greske",Toast.LENGTH_SHORT).show();
                }

            }
        });


        //region pretrazivanje golubova

        etPretraga=findViewById(R.id.etpretraga2);
        poPolu=findViewById(R.id.buttonPoPolu2);
        etPretraga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                svigolubovi=FUF.pretrazi(getApplicationContext(),etPretraga);
                adapterZaListViewGolubova.setLg(new ArrayList<Golub>(svigolubovi));
                adapterZaListViewGolubova.notifyDataSetChanged();
                izbaciPostojece();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        selektovanPol=0;
        poPolu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPretraga.setText("");

                MySQLiteHelper db= new MySQLiteHelper(getApplicationContext());
                if(selektovanPol==0){
                    selektovanPol++;
                    poPolu.setBackgroundResource(R.drawable.togglem);
                    svigolubovi=  new ArrayList<Golub>(db.dajSveGolubove(" WHERE pol='m'"));
                    adapterZaListViewGolubova.setLg(new ArrayList<Golub>(svigolubovi));
                    adapterZaListViewGolubova.notifyDataSetChanged();
                    izbaciPostojece();



                } else if(selektovanPol==1){
                    selektovanPol++;
                    poPolu.setBackgroundResource(R.drawable.togglez);
                    db= new MySQLiteHelper(getApplicationContext());
                    svigolubovi =  new ArrayList<Golub>(db.dajSveGolubove(" WHERE pol='z'"));
                    adapterZaListViewGolubova.setLg(new ArrayList<Golub>(svigolubovi));
                    adapterZaListViewGolubova.notifyDataSetChanged();
                    izbaciPostojece();


                }else{
                    selektovanPol=0;
                    poPolu.setBackgroundResource(R.drawable.togglesvi);
                    svigolubovi=  new ArrayList<Golub>(db.dajSveGolubove(" "));
                    adapterZaListViewGolubova.setLg(new ArrayList<Golub>(svigolubovi));
                    adapterZaListViewGolubova.notifyDataSetChanged();
                    izbaciPostojece();

                }
            }
        });




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonDatePicker:
                obradiDatePicker();
                break;
            case R.id.buttonDodajLet:
                dodajLet();
                break;
            case R.id.buttonTimePicker:
                obradiTimePicker();
                break;
        }
    }

    private void dodajLet() {

        String datum=date.getText().toString();
        double vlaga;
        double pritisak;
        double temperatura;
        String vetar;
        String opis= etOpis.getText().toString();
        double prosek=0;

        MySQLiteHelper db= new MySQLiteHelper(this);
        if(db.dajSveLetove(" WHERE datum='"+datum+"'").size()>0){
            Toast.makeText(this,"Već postoji let za ovaj datum",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            vlaga=Double.parseDouble(etVlaga.getText().toString());
            pritisak=Double.parseDouble(etPritisak.getText().toString());
            temperatura=Double.parseDouble(etTemperatura.getText().toString());

        }catch (Exception e){
           vlaga=0;
           pritisak=0;
           temperatura=0;
        }
        vetar= etVetar.getText().toString();

        let= new Let(datum,pritisak,vetar,vlaga,temperatura,opis,prosek);



        if(db.dodajLet(let)){
            clDodavanjeIstorije.setVisibility(View.VISIBLE);



            Toast.makeText(this, "Uspešno dodat let, možete preći na dodavanje golubova", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Došlo je do greške, pokušajte opet",Toast.LENGTH_SHORT).show();
        }
        Log.d("provera",db.dajSveLetove("").get(0).getOpis());




    }

    private void obradiTimePicker(){
        Calendar calendar= Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR);
        int MINUTE=0;
        TimePickerDialog timePickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Log.d("VREME",i1+" "+i);
                krajnjiDatum.set(Calendar.HOUR_OF_DAY,i);
                krajnjiDatum.set(Calendar.MINUTE,i1);
                date.setText(FUF.izCalendarUstring(krajnjiDatum));
            }
        }, hour,MINUTE,true);
        timePickerDialog.show();

    }

    private void obradiDatePicker() {

        c= Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);

        dpd= new DatePickerDialog(DodajLet.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                krajnjiDatum.set(y,m,d);
                date.setText(FUF.izCalendarUstring(krajnjiDatum));
            }
        }, year, month,day);

        dpd.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DODAJ && resultCode == RESULT_OK && data != null) {
            GoluboviULetu golubULetu = (GoluboviULetu) data.getSerializableExtra("DodatGolub");
            goluboviULetu.add(golubULetu);
            svigolubovi.remove(izbacen);

            adapterZaListViewGolubova.setLg(new ArrayList<Golub>(svigolubovi));
            adapterZaListViewGolubova.notifyDataSetChanged();

            Log.d("PROVERA",goluboviULetu.get(0).getOpis());

            adapterZaListViewGolubovaULetu.setLg(new ArrayList<GoluboviULetu>(goluboviULetu));
            adapterZaListViewGolubovaULetu.notifyDataSetChanged();



        }
        else if(requestCode==PomocniGolubovi.REQUEST_CODE_BRISANJE && resultCode==RESULT_OK){
            int pozicija=data.getIntExtra("pozicija",-1);
            svigolubovi.add(goluboviULetu.get(pozicija).getGolub());
            adapterZaListViewGolubova.setLg(new ArrayList<Golub>(svigolubovi));
            adapterZaListViewGolubova.notifyDataSetChanged();
            goluboviULetu.remove(pozicija);

            adapterZaListViewGolubovaULetu.setLg(new ArrayList<GoluboviULetu>(goluboviULetu));
            adapterZaListViewGolubovaULetu.notifyDataSetChanged();


        }

    }
    public void izbaciPostojece(){
        for(int i=0; i<goluboviULetu.size();i++){
            for(int j=0; j<svigolubovi.size();j++){
                if(goluboviULetu.get(i).getGolub().getBrojAlke().equals(svigolubovi.get(j).getBrojAlke())){
                    svigolubovi.remove(j);
                    adapterZaListViewGolubova.setLg(new ArrayList<Golub>(svigolubovi));
                    adapterZaListViewGolubova.notifyDataSetChanged();
                }
            }

        }
    }
}
