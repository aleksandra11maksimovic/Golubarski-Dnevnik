package elab3.com.golubarskidnevnik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import elab3.com.golubarskidnevnik.Letovi.Let;
import elab3.com.golubarskidnevnik.Letovi.PrikazLeta;

public class AdapterZaListViewLetovi extends BaseAdapter {

    private Context context;
    private ArrayList<Let> ll;
    private int[] selektovani;
    private String activity;

    private AdapterZaListViewGolubovaULetu adapterZaListViewGolubovaULetu;
    private ListView listGoluboviULetu;
    private TextView txtOpis;
    private TextView txtPritisak;
    private TextView txtVlaga;
    private TextView txtTemperatura;
    private TextView txtDatum;
    private TextView txtVetar;


    public AdapterZaListViewLetovi(Context context, ArrayList<Let> ll,String activity) {
        this.context = context;
        this.ll = ll;
        this.selektovani= new int[ll.size()];
        for(int i=0;i<this.selektovani.length;i++){
            selektovani[i]=0;
        }
        this.activity=activity;
    }

    public void setLl(ArrayList<Let> ll) {
        this.ll = ll;
        this.selektovani= new int[ll.size()];
        for(int i=0;i<this.selektovani.length;i++){
            selektovani[i]=0;
        }
    }

    @Override
    public int getCount() {
        return ll.size();
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
        final LayoutInflater inf= (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inf.inflate(R.layout.list_item_letovi,null,false);

        txtDatum= view.findViewById(R.id.txtLetDatum);
        txtOpis=view.findViewById(R.id.txtLetOpis);
        txtVetar=view.findViewById(R.id.txtLetVetar);
        txtTemperatura= view.findViewById(R.id.txtLetTemperatura);
        txtVlaga= view.findViewById(R.id.txtVlaga);
        txtPritisak=view.findViewById(R.id.txtPritisak);
        txtDatum.setText(ll.get(i).getDatum());
        txtOpis.setText(ll.get(i).getOpis());
        txtPritisak.setText((int)ll.get(i).getPritisak()+"");
        txtTemperatura.setText((int)ll.get(i).getTemperatura()+"");
        txtVlaga.setText((int)ll.get(i).getVlaga()+"");
        txtVetar.setText(ll.get(i).getVetar()+"");

        listGoluboviULetu= view.findViewById(R.id.listViewLetGolubovi);
        adapterZaListViewGolubovaULetu= new AdapterZaListViewGolubovaULetu(context,ll.get(i).getGoluboviULetu());
        listGoluboviULetu.setAdapter(adapterZaListViewGolubovaULetu);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) listGoluboviULetu.getLayoutParams();
        if(activity.equals("svi")){
            lp.height = 0;

            listGoluboviULetu.setLayoutParams(lp);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity.equals("svi")){
                    Intent intent = new Intent(context, PrikazLeta.class);
                    intent.putExtra("Let",ll.get(i));
                    context.startActivity(intent);

                }


            }
        });
        listGoluboviULetu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        /*

                if(selektovani[i]==0){
                    listGoluboviULetu= view.findViewById(R.id.listViewLetGolubovi);
                    adapterZaListViewGolubovaULetu= new AdapterZaListViewGolubovaULetu(context,ll.get(i).getGoluboviULetu());
                    listGoluboviULetu.setAdapter(adapterZaListViewGolubovaULetu);
                    ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) listGoluboviULetu.getLayoutParams();




                    lp.height = 400;
                    listGoluboviULetu.setLayoutParams(lp);
                    selektovani[i]=1;
                    listGoluboviULetu.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            view.getParent().requestDisallowInterceptTouchEvent(true);
                            return false;
                        }
                    });


                }else{
                    listGoluboviULetu.setAdapter(null);
                    ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) listGoluboviULetu.getLayoutParams();
                    lp.height = 0;
                    listGoluboviULetu.setLayoutParams(lp);
                    selektovani[i]=0;


            }
        });

        }*/





        return view;
    }
}
