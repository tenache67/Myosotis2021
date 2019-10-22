package ro.bluebit.UTILITARE;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ro.bluebit.BazaAppCompat;

import static ro.bluebit.Incarca_Descarca_Trimiteri_Activity.StocareCodBare;

public class CustomTextWatcher implements TextWatcher {
    EditText codBare;
    TextView afisareMesaj;
    BazaAppCompat Activitate;
    String preiaCodBare;
    public CustomTextWatcher(EditText codBare, TextView afisareMesaj,String preiaCodBare, BazaAppCompat activitate) {
        this.codBare = codBare;
        this.afisareMesaj = afisareMesaj;

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
        if (editable.length()==13 )
            afisareMesaj.setText(preiaCodBare);
              preiaCodBare=codBare.getText().toString();
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override

                    public void run() {

                            StocareCodBare.add(preiaCodBare);

                            }
                },
                DELAY
        );


    }
}

