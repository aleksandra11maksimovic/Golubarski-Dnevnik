package elab3.com.golubarskidnevnik.Letovi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class IzmenaLeta extends AppCompatActivity {

    TextView txtLet;
    EditText etTemperatura;
    EditText etPritisak;
    EditText etVlaga;
    EditText etVetar;
    EditText etOpis;
    Button btnIzmeni;
    Let let;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmena_leta);
        txtLet= findViewById(R.id.txtLetZaIzmenu);
        etPritisak= findViewById(R.id.etPritisakIzmena);
        etVetar= findViewById(R.id.etVetarIzmena);
        etTemperatura= findViewById(R.id.etTemperaturaIzmena);
        etVlaga= findViewById(R.id.etVlagaIzmena);
        etOpis= findViewById(R.id.txtDodatniOpisIzmena);
        btnIzmeni= findViewById(R.id.btnIzmeniLet);

        let= (Let) getIntent().getSerializableExtra("Let");

        txtLet.setText(txtLet.getText().toString()+let.getDatum());
        etPritisak.setText(let.getPritisak()+"");
        etVlaga.setText(let.getVlaga()+"");
        etVetar.setText(let.getVetar()+"");
        etTemperatura.setText(let.getTemperatura()+"");
        etOpis.setText(let.getOpis());

        btnIzmeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                izmeniLet();
            }
        });






    }

    public void izmeniLet(){
        double vlaga;
        double pritisak;
        double temperatura;
        String vetar;
        String opis= etOpis.getText().toString();
        double prosek=0;

        MySQLiteHelper db= new MySQLiteHelper(this);

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

        let= new Let(let.getDatum(),pritisak,vetar,vlaga,temperatura,opis,prosek);



        if(db.updateLet(let)>0){





            Toast.makeText(this, "Uspešno izmenjen let", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Došlo je do greške, pokušajte opet",Toast.LENGTH_SHORT).show();
        }


    }
}
