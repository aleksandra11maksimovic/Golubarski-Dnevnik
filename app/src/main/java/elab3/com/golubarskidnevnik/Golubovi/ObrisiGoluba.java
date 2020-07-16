package elab3.com.golubarskidnevnik.Golubovi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import elab3.com.golubarskidnevnik.Letovi.GoluboviULetu;
import elab3.com.golubarskidnevnik.Letovi.Let;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class ObrisiGoluba extends AppCompatActivity implements View.OnClickListener{

    Button buttonNo;
    Button buttonYes;
    TextView tekst;
    Golub golubZaBrisanje;
    int pozicija;
    int intent;
    Let let;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_obrisi_goluba);

        tekst= findViewById(R.id.textView3);

        intent= getIntent().getIntExtra("intent",0);
        if(intent==1){

            tekst.setText(tekst.getText().toString()+"goluba?");
            golubZaBrisanje= (Golub) getIntent().getSerializableExtra("Golub");
        }else if(intent==2){

            tekst.setText(tekst.getText().toString()+"goluba?");
            pozicija= getIntent().getIntExtra("pozicija",-1);
        }else if(intent==3){

            tekst.setText(tekst.getText().toString()+"let?");
            let= (Let)getIntent().getSerializableExtra("Let");
        }
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width= dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.35));
        buttonNo= findViewById(R.id.buttonDeleteNo);
        buttonYes= findViewById(R.id.buttonDeleteYes);
        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonDeleteNo){
            Intent intent= new Intent();
            setResult(0);
            finish();
        }
        if(view.getId()==R.id.buttonDeleteYes){
            if(intent==1) {
                MySQLiteHelper db = new MySQLiteHelper(this);
                int rez = db.obrisiElement("golub", golubZaBrisanje.getNazivKljuca(), golubZaBrisanje.getKljuc());


                Intent intent = new Intent();
                setResult(rez);
                finish();
            }
            else if(intent==2){
                Intent intent = new Intent();

                intent.putExtra("pozicija", pozicija);
                setResult(RESULT_OK, intent);
                finish();
            }else if(intent==3){
                MySQLiteHelper db= new MySQLiteHelper(this);
                for (GoluboviULetu g:let.getGoluboviULetu()
                ) {
                    db.obrisiElement("goluboviULetu","idLeta",let.getDatum());


                }
                db.obrisiElement("let","datum",let.getDatum());
                db.close();
                Intent intent= new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }




        }

    }
}
