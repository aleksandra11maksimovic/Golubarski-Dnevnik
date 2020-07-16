package elab3.com.golubarskidnevnik.Letovi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import elab3.com.golubarskidnevnik.Golubovi.Golub;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class DodajGoluboviULetu extends Activity {

    Golub golub;
    Let let;
    Switch vezanost;
    TextView textViewInfo;

    Button dodaj;
    int rbr;
    EditText etRbr;
    EditText etVezanost;
    EditText etOpis;
    EditText etRazmak;
    int razmak=0;

    GoluboviULetu goluboviULetu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dodaj_goluboviuletu);



        golub= (Golub)getIntent().getSerializableExtra("Golub");
        let= (Let) getIntent().getSerializableExtra("Let");
        final String intent= getIntent().getStringExtra("Intent");


        MySQLiteHelper db1= new MySQLiteHelper(this);
        etVezanost=findViewById(R.id.etVezanost);
        textViewInfo= findViewById(R.id.textViewInfo);
        etOpis= findViewById(R.id.etOpis);
        etRazmak= findViewById(R.id.etRazmak);
        vezanost= findViewById(R.id.switch1);
        etRbr= findViewById(R.id.etRedniBroj);

        MySQLiteHelper db= new MySQLiteHelper(this);
        razmak= db.vratiRazmak(golub,let.getDatum());
        etRazmak.setText(etRazmak.getText().toString()+razmak);
        rbr= db.dajRbrULetu(golub);
        etRbr.setText(rbr+"");
        textViewInfo.setText(golub.getBoja()+" " +golub.getDodatak()+" sa alkom br: "+golub.getBrojAlke()+" "+golub.getBojaAlke());
        dodaj= findViewById(R.id.buttonDodajULet);
        if(intent!= null){
            podesiZaIzmenu();
            goluboviULetu= (GoluboviULetu) getIntent().getSerializableExtra("GolubULetu");
        }
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intent==null){

                    dodajULet();
                }else{
                    izmeniGoluba();
                }
            }
        });


        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width= dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int)(width*.85),(int)(height*.7
        ));

        vezanost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    etVezanost.setEnabled(true);
                    etVezanost.setText("");


                }else{
                    etVezanost.setEnabled(false);
                    etVezanost.setText("Nije vezan");
                }
            }
        });

    }

    private void izmeniGoluba() {
        goluboviULetu.setOpis(etOpis.getText().toString());
        goluboviULetu.setVezanost(etVezanost.getText().toString());
        MySQLiteHelper db3= new MySQLiteHelper(DodajGoluboviULetu.this);
        int rez= db3.updateGoluboviULetu(goluboviULetu);
        if(rez>0){
            Toast.makeText(DodajGoluboviULetu.this,"Uspešno izmenjen golub u Letu",Toast.LENGTH_SHORT).show();
        }

    }

    private void podesiZaIzmenu() {
        etRbr.setEnabled(false);
        etRazmak.setEnabled(false);

        dodaj.setText("Izmeni goluba u letu");


    }

    private void dodajULet() {



        rbr= Integer.parseInt(etRbr.getText().toString());

        String vezan;
        if (vezanost.isChecked()){
            vezan=etVezanost.getText().toString();

        }else{
            vezan="Ne";
        }



        GoluboviULetu goluboviULetu= new GoluboviULetu(golub,let,0,vezan,rbr,etOpis.getText().toString(), razmak,"");

        Intent intent = new Intent();

        intent.putExtra("DodatGolub", goluboviULetu);
        setResult(RESULT_OK, intent);
        finish();


    }
}
