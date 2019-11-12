package ro.bluebit.UTILITARE;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ro.bluebit.BazaAppCompat;



public class CustomTextWatcher implements TextWatcher {
    EditText codBare;
    TextView afisareMesaj;
    BazaAppCompat Activitate;
    String preiaCodBare;
    public CustomTextWatcher(EditText codBare, TextView afisareMesaj,String preiaCodBare, BazaAppCompat activitate) {
        this.codBare = codBare;
        this.afisareMesaj = afisareMesaj;
        this.Activitate = activitate;

    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    private Timer timer = new Timer();
    private final long DELAY = 5000; // milliseconds


    @Override
    public void afterTextChanged(final Editable editable) {
        if (editable.length()==0)
            afisareMesaj.setText("Nu ai introdus nici o cifra");
        else if (editable.length()<=12)
            afisareMesaj.setText("Codul introdus are mai putin de 13 caractere ");
        
        else
        if (editable.length()==13 ) {
            preiaCodBare = codBare.getText().toString();
            afisareMesaj.setText(preiaCodBare);
            Activitate.executalacodvalid(preiaCodBare);

        }

    }
}

