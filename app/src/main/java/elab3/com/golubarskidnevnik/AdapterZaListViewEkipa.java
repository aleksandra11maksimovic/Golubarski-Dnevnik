package elab3.com.golubarskidnevnik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import elab3.com.golubarskidnevnik.Ekipe.Ekipa;

public class AdapterZaListViewEkipa extends BaseAdapter {


    ArrayList<Ekipa> listaEkipa;
    private Context context;
    private int[] selektovani;


    private AdapterZaListViewGolubova adapterZaListViewGolubova;

    public AdapterZaListViewEkipa(ArrayList<Ekipa> listaEkipa, Context context) {
        this.listaEkipa = listaEkipa;
        this.context = context;
        this.selektovani= new int[listaEkipa.size()];
        for(int i=0;i<this.selektovani.length;i++){
            selektovani[i]=0;
        }
    }

    public void setListaEkipa(ArrayList<Ekipa> listaEkipa) {
        this.listaEkipa = listaEkipa;
        this.selektovani= new int[listaEkipa.size()];
        for(int i=0;i<this.selektovani.length;i++){
            selektovani[i]=0;
        }
    }

    @Override
    public int getCount() {
        return listaEkipa.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    TextView txtNaziv;
    ListView listaGolubovaUEKipi;
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf= (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inf.inflate(R.layout.list_item_ekipa,null);
        txtNaziv= view.findViewById(R.id.txtNazivEkipe);
        txtNaziv.setText(listaEkipa.get(i).getNaziv());
        listaGolubovaUEKipi= view.findViewById(R.id.listViewGoluboviUEkipi);





        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selektovani[i]==0){
                    listaGolubovaUEKipi= view.findViewById(R.id.listViewGoluboviUEkipi);
                    adapterZaListViewGolubova= new AdapterZaListViewGolubova(context,listaEkipa.get(i).getListaGolubova());
                    listaGolubovaUEKipi.setAdapter(adapterZaListViewGolubova);
                    ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) listaGolubovaUEKipi.getLayoutParams();
                    lp.height = 400;
                    listaGolubovaUEKipi.setLayoutParams(lp);
                    selektovani[i]=1;
                    listaGolubovaUEKipi.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            view.getParent().requestDisallowInterceptTouchEvent(true);
                            return false;
                        }
                    });


                }else{
                    listaGolubovaUEKipi.setAdapter(null);
                    ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) listaGolubovaUEKipi.getLayoutParams();
                    lp.height = 0;
                    listaGolubovaUEKipi.setLayoutParams(lp);
                    selektovani[i]=0;
                }

            }
        });







        return view;
    }
}
