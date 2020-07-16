package elab3.com.golubarskidnevnik.Golubovi;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import elab3.com.golubarskidnevnik.AdapterZaListViewGolubova;
import elab3.com.golubarskidnevnik.MainActivity;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;

public class GoluboviFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<Golub> listaGolubova;
    ImageButton add;
    ListView listView;
    MySQLiteHelper db;
    EditText etPretraga;
    int selektovanPol;
    Button poPolu;

    public GoluboviFragment() {
        // Required empty public constructor
    }

    public static GoluboviFragment newInstance(String param1, String param2) {
        GoluboviFragment fragment = new GoluboviFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_golubovi, container, false);

        poPolu= view.findViewById(R.id.buttonPoPolu);
        selektovanPol=0;
        poPolu.setOnClickListener(this);


        etPretraga= view.findViewById(R.id.etpretraga2);
        etPretraga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pretrazi();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        add = view.findViewById(R.id.imageButton4);
        add.setOnClickListener(this);

        db= new MySQLiteHelper(getActivity());
        listView= view.findViewById(R.id.listView);
        listaGolubova= new ArrayList<Golub>(db.dajSveGolubove(""));
        AdapterZaListViewGolubova adapterZaListView=new AdapterZaListViewGolubova(getContext(),listaGolubova);
        listView.setAdapter(adapterZaListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(getActivity(),PomocniGolubovi.class);
                intent.putExtra("Golub",listaGolubova.get(i));
                startActivity(intent);



            }
        });




        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButton4:
                Intent intent= new Intent(getActivity(), DodajGoluba.class);
                intent.putExtra("intent","dodaj");
                startActivity(intent);
                break;
            case R.id.buttonPoPolu:
                etPretraga.setText("");
                if(selektovanPol==0){
                    selektovanPol++;
                    poPolu.setBackgroundResource(R.drawable.togglem);
                    dajGolubove(" WHERE pol='m'");

                } else if(selektovanPol==1){
                    selektovanPol++;
                    poPolu.setBackgroundResource(R.drawable.togglez);
                    dajGolubove(" WHERE pol='z'");

                }else{
                    selektovanPol=0;
                    poPolu.setBackgroundResource(R.drawable.togglesvi);
                    dajGolubove(" ");
                }
                break;

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        etPretraga.setText("");
        listaGolubova= new ArrayList<Golub>(db.dajSveGolubove(""));
        AdapterZaListViewGolubova adapterZaListView=new AdapterZaListViewGolubova(getContext(),listaGolubova);
        listView.setAdapter(adapterZaListView);


    }



    public void pretrazi(){
        String pretraga= etPretraga.getText().toString();
        String uslov= " WHERE id like '%"+pretraga+"%' OR bojaAlke like '%"+pretraga+"%' OR boja like '%"+pretraga+"%' OR dodatak like '%"+pretraga+"%'";
        dajGolubove(uslov);

    }
    public void dajGolubove(String uslov){
        listaGolubova= new ArrayList<Golub>(db.dajSveGolubove(uslov));
        AdapterZaListViewGolubova adapterZaListView=new AdapterZaListViewGolubova(getContext(),listaGolubova);
        listView.setAdapter(adapterZaListView);
    }


}
