package elab3.com.golubarskidnevnik.Golubovi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import elab3.com.golubarskidnevnik.AdapterZaListViewLetovi;
import elab3.com.golubarskidnevnik.Letovi.Let;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class PrikazGoluba extends AppCompatActivity {

    ListView listView;
    ArrayList<Let> letovi;
    Golub golub;
    AdapterZaListViewLetovi adapterZaListViewLetovi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prikaz_goluba);

        golub=(Golub)getIntent().getSerializableExtra("Golub");
        listView=findViewById(R.id.listViewLetoviGoluba);

        MySQLiteHelper db= new MySQLiteHelper(this);
        letovi=db.vratiLetoveZaGoluba(golub);

        adapterZaListViewLetovi= new AdapterZaListViewLetovi(this,letovi,"prikazGoluba");
        listView.setAdapter(adapterZaListViewLetovi);





    }
}
