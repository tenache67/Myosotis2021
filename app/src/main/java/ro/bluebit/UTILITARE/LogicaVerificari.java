package ro.bluebit.UTILITARE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ro.bluebit.Database.Constructor;

import static java.lang.Long.parseLong;

public class LogicaVerificari {

    public LogicaVerificari() {
    }


    public static double verifCorectitudineBare(String numar) {

        String temp = Long.toString(Long.parseLong(numar));
        int[] a = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++) {
            a[i] = temp.charAt(i) - '0';
        }
        double suma = 3 * (a[1] + a[3] + a[5] + a[7] + a[9] + a[11]) + (a[0] +a[2] + a[4] + a[6] + a[8] + a[10]  );
        double rezultat = Math.ceil(suma/10 )*10;
        double diferenta = rezultat - suma;

        if (diferenta == a[12]) {



        }  return 1;
    }

//Verificare longs de bare in plaja

    public static String SQL_QUERY_OBTINE_VALIDARE_PLAJA_longsURI(long longsBare) {
        return " SELECT " + Constructor.Tabela_Plaja_Cod.COL_ID_LOT + " from " + Constructor.Tabela_Plaja_Cod.NUME_TABEL +
                " where " + longsBare + " between " + Constructor.Tabela_Plaja_Cod.COL_MINIM + " and " + Constructor.Tabela_Plaja_Cod.COL_MAXIM;

    }

    ;

// validarea din plaja de longsuri

    public int verificaPlajalongsBare(SQLiteDatabase db, long longsBare) {
        Long longs = longsBare;
        Cursor crs1 = db.rawQuery(LogicaVerificari.SQL_QUERY_OBTINE_VALIDARE_PLAJA_longsURI(longs), null);
        crs1.moveToFirst();
        int b = crs1.getInt(0);
        if (b != 0) {

        }
        return b;
    }

    public static  class SuperTextWatcher implements TextWatcher{
        TextView afisareMesaj; String preiaCodBare;
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
//                preiaCodBare=cod_bare.getText().toString();
            timer.cancel();
            timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override

                        public void run() {
//                          //  if ( verificCorectitudineCod ()==1 )
//                                if ( verificCorectitudineCod ()==1 && verificPlajaCod() > 0)
//                            StocareCodBare.add(preiaCodBare);

                            // cod_bare.setText("");
                        }
                    },
                    DELAY
            );


        }
    }

}

