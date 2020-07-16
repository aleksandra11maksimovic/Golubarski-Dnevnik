package elab3.com.golubarskidnevnik.Letovi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import elab3.com.golubarskidnevnik.AdapterZaListViewLetovi;
import elab3.com.golubarskidnevnik.Golubovi.DodajGoluba;
import elab3.com.golubarskidnevnik.MainActivity;
import elab3.com.golubarskidnevnik.MySQLiteHelper;
import elab3.com.golubarskidnevnik.R;


public class LetoviFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Let> letovi= new ArrayList<>();

    private ListView listViewLetovi;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AdapterZaListViewLetovi adapterZaListViewLetovi;


    public LetoviFragment() {
        // Required empty public constructor
    }


    ImageButton dodaj;
    // TODO: Rename and change types and number of parameters
    public static LetoviFragment newInstance(String param1, String param2) {
        LetoviFragment fragment = new LetoviFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_letovi, container, false);

        dodaj= view.findViewById(R.id.buttonLetAdd);
        dodaj.setOnClickListener(this);

        MySQLiteHelper db= new MySQLiteHelper(getActivity());

        letovi= db.dajSveLetove("");
       // Collections.sort(letovi);
        listViewLetovi= view.findViewById(R.id.listViewLetovi);
        adapterZaListViewLetovi= new AdapterZaListViewLetovi(getContext(),letovi,"svi");
        listViewLetovi.setAdapter(adapterZaListViewLetovi);






        return view;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonLetAdd:
                Intent intent= new Intent(getActivity(), DodajLet.class);
                startActivity(intent);
                break;

        }

    }

    @Override
    public void onResume() {
        MySQLiteHelper db= new MySQLiteHelper(getActivity());
//        Collections.sort(letovi);
        letovi=db.dajSveLetove("");
        adapterZaListViewLetovi.setLl(letovi);
        adapterZaListViewLetovi.notifyDataSetChanged();
        super.onResume();
    }
}

       /*
                MySQLiteHelper db= new MySQLiteHelper(getContext());
                for (GoluboviULetu g:letovi.get(i).getGoluboviULetu()
                ) {
                    db.obrisiElement("goluboviULetu","idLeta",letovi.get(i).getDatum());


                }
                db.obrisiElement("let","datum",letovi.get(i).getDatum());
                db.close();


                return true;
            }
        });*/