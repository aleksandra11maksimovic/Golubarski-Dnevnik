package elab3.com.golubarskidnevnik.Letovi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

import elab3.com.golubarskidnevnik.R;

public class DodajLet extends AppCompatActivity implements View.OnClickListener{

    ImageButton datePicker;
    Calendar c;
    DatePickerDialog dpd;
    TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_let);
        date= findViewById(R.id.textView4);
        datePicker= findViewById(R.id.buttonDatePicker);
        datePicker.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonDatePicker:
                obradiDatePicker();
                break;
        }
    }

    private void obradiDatePicker() {

        c= Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);

        dpd= new DatePickerDialog(DodajLet.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                date.setText(d+"."+(m+1)+"."+y);
            }
        }, year, month,day);

        dpd.show();


    }
}
