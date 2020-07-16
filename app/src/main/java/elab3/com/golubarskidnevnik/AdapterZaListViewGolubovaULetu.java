package elab3.com.golubarskidnevnik;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import elab3.com.golubarskidnevnik.Golubovi.Golub;
import elab3.com.golubarskidnevnik.Letovi.GoluboviULetu;
import elab3.com.golubarskidnevnik.Letovi.PomocniGoluboviULetuBrisanje;

public class AdapterZaListViewGolubovaULetu extends BaseAdapter {

    ArrayList<GoluboviULetu> lg=new ArrayList<>();
    Context context;
    public AdapterZaListViewGolubovaULetu(Context context,ArrayList<GoluboviULetu> lg) {
        this.context=context;
        this.lg = lg;
    }

    public void setLg(ArrayList<GoluboviULetu> lg) {
        this.lg = lg;
    }
    @Override
    public int getCount() {
        return lg.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf= (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inf.inflate(R.layout.list_item_golub_u_letu,null);
        TextView txtInformacije= view.findViewById(R.id.txtInformacije);
        TextView rbr= view.findViewById(R.id.txtRbr);
        TextView vezanost= view.findViewById(R.id.txtVezanost);
        TextView duzina= view.findViewById(R.id.txtDuzina);
        TextView razmak= view.findViewById(R.id.txtRazmak);
        Golub golub= lg.get(i).getGolub();
        razmak.setText(lg.get(i).getRazmak()+"");
        txtInformacije.setText(golub.getBoja()+"\n"+golub.getDodatak()+"\n"+golub.getBrojAlke()+"\n" + golub.getBojaAlke());
        rbr.setText(lg.get(i).getrBrLeta()+"");
        vezanost.setText(lg.get(i).getVezanost());
        duzina.setText(lg.get(i).getDuzina()+"");
        if(lg.get(i).getDuzina()==0){
            duzina.setText(lg.get(i).getVremeSletanja());

        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, PomocniGoluboviULetuBrisanje.class);
                intent.putExtra("Golub",lg.get(i));
                context.startActivity(intent);
            }
        });


        return view;


    }
}
