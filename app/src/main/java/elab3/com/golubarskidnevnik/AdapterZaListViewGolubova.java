package elab3.com.golubarskidnevnik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import elab3.com.golubarskidnevnik.Golubovi.Golub;

public class AdapterZaListViewGolubova extends BaseAdapter {

    ArrayList<Golub> lg=new ArrayList<>();
    Context context;
    public AdapterZaListViewGolubova(Context context,ArrayList<Golub> lg) {
        this.context=context;
        this.lg = lg;
    }

    public void setLg(ArrayList<Golub> lg) {
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inf= (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inf.inflate(R.layout.list_item_golub,null);
        TextView txtBrojAlke=view.findViewById(R.id.txtBrojAlke);
        TextView txtBojaAlke=view.findViewById(R.id.txtBojaAlke);
        TextView txtDodatak=view.findViewById(R.id.txtDodatak);
        TextView txtBoja=view.findViewById(R.id.txtBoja);
        TextView txtEkipa= view.findViewById(R.id.txtEkipa);
        TextView txtRbr= view.findViewById(R.id.txtRBr);

        txtBrojAlke.setText(lg.get(position).getBrojAlke());
        txtBojaAlke.setText(lg.get(position).getBojaAlke());
        txtBoja.setText(lg.get(position).getBoja());
        txtDodatak.setText(lg.get(position).getDodatak());
        MySQLiteHelper db= new MySQLiteHelper(view.getContext());
        String ekipa=db.vratiEkipuZaGoluba(lg.get(position));
        txtEkipa.setText(ekipa);
        txtRbr.setText((position+1)+"");

        return view;


    }

}
