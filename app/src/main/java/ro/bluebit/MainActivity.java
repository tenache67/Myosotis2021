package ro.bluebit;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.Database.MySQLHelper;
import ro.bluebit.UTILITARE.LogicaVerificari;
import ro.bluebit.UTILITARE.SelectieInitialaActivity;

public class MainActivity extends BazaAppCompat {
    Button acceseaza;
    TextView afisez;
    EditText parola;
    DatabaseHelper myDb;
   public AutoCompleteTextView PunctDeLucru;

    @Override
    public void executalaHttpResponse(String sScop,String sRaspuns) {
        super.executalaHttpResponse(sScop,sRaspuns);
        if (sScop.equals("SINCRONIZARE")) {
            Log.d(sScop,"start");
            myDb.sincronizare_receptie(sRaspuns);
        }
        stergeRaspuns(sScop);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
// crearea datelor de proba - am pus aici ca sa ruleze de fiecate data la pornire
        myDb.dateProba();

        // test json
//        String sJsonStr="[[\"tabela_p_lucru\"],[\"0\",\"M0\",\"str. Drumul de Centura nr. 236 \",\"Galati\",\"Galati\",\"0236.461.718\",\"1\",\"45.425771\",\"28.004648\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"1\",\"M1\",\"str. Traian Vuia nr. 21, Bl. J2, parter\",\"Galati\",\"Galati\",\"0236.426.344\",\"1\",\"45.454253\",\"28.023639\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"2\",\"M2\",\"str. Otelarilor nr. 20, Bl. P3, Ap. 43\",\"Galati\",\"Galati\",\"0236.312.254\",\"1\",\"45.413565\",\"28.016984\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"3\",\"M3\",\"str. Aleea Comertului nr. 2\",\"Galati\",\"Galati\",\"0236.479.251\",\"1\",\"45.454527\",\"28.022768\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"4\",\"M4\",\"str. Brailei nr.58, Bl. BR2, parter\",\"Galati\",\"Galati\",\"0236.479.252\",\"1\",\"45.43114\",\"28.046655\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"5\",\"M5\",\"str. Principala\",\"Galati\",\"Pechea\",\"0236.823.505\",\"1\",\"45.621635\",\"27.798827\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"6\",\"M6\",\"str. Principala nr. 426\",\"Galati\",\"Tudor Vladimirescu\",\"0236.829.072\",\"1\",\"45.566302\",\"27.643686\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"7\",\"M7\",\"str. Principala\",\"Galati\",\"Independenta\",\"0236.833.351\",\"1\",\"45.471553\",\"27.763546\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"8\",\"M8\",\"str. Principala nr. 115\",\"Vrancea\",\"Tataranu\",\"0237.248.015\",\"1\",\"45.513789\",\"27.325201\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"9\",\"M9\",\"str. Principala\",\"Galati\",\"Slobozia Conachi\",\"0236.824.805\",\"1\",\"45.584731\",\"27.770071\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"10\",\"M10\",\"str. Principala\",\"Galati\",\"Piscu\",\"0236.827.759\",\"1\",\"45.498981\",\"27.727153\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"1001\",\"A01\",\"str. Principala nr. 241\",\"Vrancea\",\"Vulturu\",\"0371.333.159\",\"0\",\"45.620523\",\"27.422869\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"1002\",\"A02\",\"str. Bucegi nr. 26A\",\"Vrancea\",\"Focsani\",\"0237.224.070\",\"0\",\"45.689676\",\"27.187385\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"1003\",\"A03\",\"str. Principala nr. 176\",\"Vrancea\",\"Nanesti\",\"0371.521.430\",\"0\",\"45.551242\",\"27.50023\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"1011\",\"F01\",\"str. Slt. Gavrilov Corneliu nr. 99,  Bl. A1, parter\",\"Tulcea\",\"Tulcea\",\"0340.401.471\",\"1\",\"45.171975\",\"28.804931\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"1012\",\"F02\",\"str. 1848\\/Iuliu Maniu \",\"Tulcea\",\"Tulcea\",\"0340.401.602\",\"1\",\"45.172995\",\"28.785474\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"1013\",\"F03\",\"str. Iuliu Maniu intersectie cu str. 1848\",\"Tulcea\",\"Tulcea\",\"0340.401.125\",\"1\",\"45.173011\",\"28.785897\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"1021\",\"E01\",\"str. Cismelei nr. 2, bl. LA, scara C, ap. 43, parter\",\"Constanta\",\"Constanta\",\"0341.412.476\",\"1\",\"44.210855\",\"28.62417\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"1022\",\"E02\",\"Complex Comercial Grivita, Magazin nr.6, parter\",\"Constanta\",\"Constanta\",\"0341.456.077\",\"1\",\"44.177112\",\"28.643261\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"1023\",\"E03\",\"sos. Constantei nr. 114, bl. F9, ap. 2, parter\",\"Constanta\",\"Cumpana\",\"0341.456.275\",\"1\",\"44.113145\",\"28.556655\",\"2019-11-30 01:29:18\",\"tabela_p_lucru\"],[\"10001\",\"GL06WSF\",\"Dacia Logan\",\" \",\" \",\" \",\"1\",\"0\",\"0\",\"2019-11-30 01:05:16\",\"tabela_p_lucru\"],[\"10002\",\"GL06FJD\",\"Opel\",\" \",\" \",\" \",\"1\",\"0\",\"0\",\"2019-11-30 01:05:16\",\"tabela_p_lucru\"],[\"10003\",\"GL06EMS\",\"SKODA\",\" \",\" \",\" \",\"1\",\"0\",\"0\",\"2019-11-30 01:05:16\",\"tabela_p_lucru\"],[\"10004\",\"GL06JPD\",\"FORD\",\" \",\" \",\" \",\"1\",\"0\",\"0\",\"2019-11-30 01:05:16\",\"tabela_p_lucru\"],[\"10005\",\"GL07PKT\",\"LOGAN\",\" \",\" \",\" \",\"1\",\"0\",\"0\",\"2019-11-30 01:05:16\",\"tabela_p_lucru\"],[\"10006\",\"GL07CFG\",\"LOGAN\",\" \",\" \",\" \",\"1\",\"0\",\"0\",\"2019-11-30 01:05:16\",\"tabela_p_lucru\"],[\"10007\",\"GL07GTA\",\"HYUNDAI\",\" \",\" \",\" \",\"1\",\"0\",\"0\",\"2019-11-30 01:05:16\",\"tabela_p_lucru\"],[\"10008\",\"GL06WDH\",\"VOLSWAGEN\",\" \",\" \",\" \",\"1\",\"0\",\"0\",\"2019-11-30 01:05:16\",\"tabela_p_lucru\"],[\"10009\",\"GL36ZSK\",\"BMW\",\" \",\" \",\" \",\"0\",\"0\",\"0\",\"2019-11-30 01:05:16\",\"tabela_p_lucru\"],[\"10010\",\"GL17DUH\",\"VOLSKWAGEN\",\" \",\" \",\" \",\"0\",\"0\",\"0\",\"2019-11-30 01:05:16\",\"tabela_p_lucru\"],[\"tabela_plaja_cod\"],[\"1\",\"1\",\"1000\",\"2019-11-30 23:05:28\",\"tabela_plaja_cod\"],[\"2\",\"1001\",\"2000\",\"2019-11-30 23:05:28\",\"tabela_plaja_cod\"]]";

        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Logare in aplicatie");

        parola = findViewById(R.id.editTextPIN);
        afisez = findViewById(R.id.afisareMesajeTV);
        PunctDeLucru = findViewById(R.id.ACTV);
        acceseaza = findViewById(R.id.button_logare);

        acceseaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       String pin=parola.getText().toString().trim();
                Boolean res = myDb.verificPin(parola.getText().toString());

                try {

                    if (res && id_P_Lucru() != 0) {
                        Toast.makeText(MainActivity.this, " Bun venit " + numeUtilizator(), Toast.LENGTH_LONG).show();
                        Intent logareUtilizator = new Intent(MainActivity.this, SelectieInitialaActivity.class);
                        String idUtilizator = id_Utilizator();
                        logareUtilizator.putExtra("UTILIZATOR", idUtilizator);
                        logareUtilizator.putExtra("UserPL",PunctDeLucru.getText().toString());
                        int id_pct_lucru = id_P_Lucru();
                        logareUtilizator.putExtra("ID_P_LUCRU", id_pct_lucru);
                        startActivity(logareUtilizator);

                    } else {
                        Toast.makeText(MainActivity.this, " Utilizator neidentificat", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, " Nu ai selectat nici un punct de lucru", Toast.LENGTH_SHORT).show();
                }
            }

            ;


        });
        PopulareAutocomplete();
        actualizareinBackground.execute();
    }

    AsyncTask actualizareinBackground = new AsyncTask() {
        @Override
        protected String doInBackground(Object[] objects) {
            Timer timerr = new Timer();
            final long DELAY = 10000; // milliseconds
            timerr.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            String sQuery="CALL get_new ('')";
                            String sScop="SINCRONIZARE";
                            try {
                                BazaAppCompat cAct = (BazaAppCompat) MainActivity.this;
                                if (!cAct.existaCerere("SINCRONIZARE"))  {
                                    MySQLHelper.postRequest("test_mysql_new.php", sQuery, MainActivity.this,sScop);
                                }
                            } catch (IOException e) {
                                Log.d("SINCRO","Eroare "+e.getMessage());
                                e.printStackTrace();
                            }


                           // Log.d("async","Mesajjjjjjjjjjjj");

                        }


                    },
                    DELAY,10000

            );


            return null;
        }
    };

    public int id_P_Lucru() {
        SQLiteDatabase db = myDb.getReadableDatabase();
        int rezultatID = LogicaVerificari.getPunctLucru(db, PunctDeLucru.getText().toString());
        return rezultatID;
    }


    public String numeUtilizator() {
        SQLiteDatabase db = myDb.getReadableDatabase();
        String result = "";

        String queryUtilizator = ("select " + Constructor.TabelaUtilizatorPin.COL_NUME + " from " + Constructor.TabelaUtilizatorPin.NUME_TABEL +
                " where " + Constructor.TabelaUtilizatorPin.COL_PIN + " = " + parola.getText().toString().trim());
        Cursor cursor = db.rawQuery(queryUtilizator, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(Constructor.TabelaUtilizatorPin.COL_NUME));
        }
        cursor.close();
        return result;
    }

    public String id_Utilizator() {
        SQLiteDatabase db = myDb.getReadableDatabase();
        String result = "";

        String queryUtilizator = ("select " + Constructor.TabelaUtilizatorPin.COL_ID + " from " + Constructor.TabelaUtilizatorPin.NUME_TABEL +
                " where " + Constructor.TabelaUtilizatorPin.COL_PIN + " = " + parola.getText().toString().trim());
        Cursor cursor = db.rawQuery(queryUtilizator, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(Constructor.TabelaUtilizatorPin.COL_ID));
        }
        cursor.close();
        return result;

    }

    public void PopulareAutocomplete() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();
        final String[] Expeditor_Destinatar = LogicaVerificari.getPlucru(db);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Expeditor_Destinatar);
        PunctDeLucru.setAdapter(adapter);

    }
}