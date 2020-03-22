package elab3.com.golubarskidnevnik.Golubovi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class ObrisiGoluba extends Activity implements View.OnClickListener{

    Button buttonNo;
    Button buttonYes;
    Golub golubZaBrisanje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_obrisi_goluba);
        golubZaBrisanje= (Golub) getIntent().getSerializableExtra("Golub");
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width= dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.2));
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
            MySQLiteHelper db= new MySQLiteHelper(this);
            int rez=db.obrisiElement("golub",golubZaBrisanje.getNazivKljuca(),golubZaBrisanje.getKljuc());


                Intent intent= new Intent();
                setResult(rez);
                finish();




        }

    }
}
