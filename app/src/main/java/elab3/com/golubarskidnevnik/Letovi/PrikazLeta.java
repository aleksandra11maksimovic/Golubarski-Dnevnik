package elab3.com.golubarskidnevnik.Letovi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import elab3.com.golubarskidnevnik.AdapterZaListViewGolubovaULetu;
import elab3.com.golubarskidnevnik.AdapterZaListViewLetovi;
import elab3.com.golubarskidnevnik.Golubovi.ObrisiGoluba;
import elab3.com.golubarskidnevnik.Golubovi.PomocniGolubovi;
import elab3.com.golubarskidnevnik.Golubovi.PrikazGoluba;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class PrikazLeta extends AppCompatActivity {

    private Let let;
    AdapterZaListViewLetovi adapterZaListViewLetovi;
    ArrayList<Let> letovi;

    public static int REQUEST_CODE_IZMENA_LETA=2;

    private AdapterZaListViewGolubovaULetu adapterZaListViewGolubovaULetu;
    private ListView listGoluboviULetu;
    private TextView txtOpis;
    private TextView txtPritisak;
    private TextView txtVlaga;
    private TextView txtTemperatura;
    private TextView txtDatum;
    private TextView txtVetar;

    private Button btnObrisi;
    private Button btnIzmeni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prikaz_leta);

        listGoluboviULetu= findViewById(R.id.listViewZa1Let);
        let= (Let)getIntent().getSerializableExtra("Let");


        letovi= new ArrayList<Let>();
        letovi.add(let);
        adapterZaListViewGolubovaULetu=new AdapterZaListViewGolubovaULetu(this, let.getGoluboviULetu());
        listGoluboviULetu.setAdapter(adapterZaListViewGolubovaULetu);

        txtDatum= findViewById(R.id.txtLetDatum1);
        txtOpis=findViewById(R.id.txtLetOpis1);
        txtVetar=findViewById(R.id.txtLetVetar1);
        txtTemperatura= findViewById(R.id.txtLetTemperatura1);
        txtVlaga= findViewById(R.id.txtVlaga1);
        txtPritisak=findViewById(R.id.txtPritisak1);
        txtDatum.setText(let.getDatum());
        txtOpis.setText(let.getOpis());
        txtPritisak.setText((int)let.getPritisak()+"");
        txtTemperatura.setText((int)let.getTemperatura()+"");
        txtVlaga.setText((int)let.getVlaga()+"");
        txtVetar.setText(let.getVetar()+"");

        btnObrisi= findViewById(R.id.btnObrisiLet);
        btnIzmeni=findViewById(R.id.btnIzmeniLet);

        btnObrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PrikazLeta.this, ObrisiGoluba.class);
                intent.putExtra("Let",let);
                intent.putExtra("intent",3);
                startActivityForResult(intent,PomocniGolubovi.REQUEST_CODE_BRISANJE);

            }
        });

        btnIzmeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PrikazLeta.this, IzmenaLeta.class);
                intent.putExtra("Let", let);
                startActivityForResult(intent, REQUEST_CODE_IZMENA_LETA);


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        MySQLiteHelper db= new MySQLiteHelper(this);
        letovi=db.dajSveLetove(" WHERE datum='"+let.getDatum()+"'" );
        if(letovi.size()==1){
            let=letovi.get(0);
        }
        adapterZaListViewGolubovaULetu.setLg(let.getGoluboviULetu());
        adapterZaListViewGolubovaULetu.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode== PomocniGolubovi.REQUEST_CODE_BRISANJE){
            if(resultCode==RESULT_OK){
                Toast.makeText(this,"Uspešno obrisan let",Toast.LENGTH_SHORT).show();
                finish();

            }
            else if(resultCode!=RESULT_OK){

                Toast.makeText(this,"Brisanje nije uspelo",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
