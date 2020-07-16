package elab3.com.golubarskidnevnik.Ekipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import elab3.com.golubarskidnevnik.AdapterZaListViewEkipa;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;


public class EkipeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public EkipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EkipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EkipeFragment newInstance(String param1, String param2) {
        EkipeFragment fragment = new EkipeFragment();
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

    ImageButton btnDodajEkipu;
    ListView listViewEkipe;
    AdapterZaListViewEkipa adapterZaListViewEkipa;
    ArrayList listaEkipa;
    ImageButton unesiEkipu;
    EditText etNoviNaziv;
    MySQLiteHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ekipe, container, false);
        btnDodajEkipu= view.findViewById(R.id.btnDodajEkipu);
        listViewEkipe=view.findViewById(R.id.listViewEkipe);
        db= new MySQLiteHelper(getContext());
        listaEkipa=db.vratiSveEkipe();
        adapterZaListViewEkipa= new AdapterZaListViewEkipa(listaEkipa,getContext());
        listViewEkipe.setAdapter(adapterZaListViewEkipa);
        unesiEkipu= view.findViewById(R.id.imageButton);
        etNoviNaziv= view.findViewById(R.id.etNovaEkipa);



        btnDodajEkipu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etNoviNaziv.setVisibility(View.VISIBLE);
                unesiEkipu.setVisibility(View.VISIBLE);

                unesiEkipu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.dodajEkipu(etNoviNaziv.getText().toString());
                        refreshListView();
                        etNoviNaziv.setVisibility(View.INVISIBLE);
                        unesiEkipu.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Uspesno uneta ekipa",Toast.LENGTH_SHORT).show();
                        etNoviNaziv.setText("");
                    }
                });



            }
        });

        return view;
    }

    public void refreshListView(){

        adapterZaListViewEkipa.setListaEkipa(db.vratiSveEkipe());
        adapterZaListViewEkipa.notifyDataSetChanged();


    }

    @Override
    public void onResume() {
        super.onResume();
        refreshListView();
    }
}
