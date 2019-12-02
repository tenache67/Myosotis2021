package ro.bluebit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.Database.MySQLHelper;
import ro.bluebit.UTILITARE.CustomTextWatcher;
import ro.bluebit.UTILITARE.LogicaVerificari;
import ro.bluebit.phpmysql.RaspunsRamuraIncDesc;

import static java.lang.Long.parseLong;

public class Incarca_Descarca_Trimiteri_Activity extends BazaAppCompat {
    EditText cod_bare1;
    DatabaseHelper myDb;
    Long[] stocareCodBareDinScanner;
    String preiaCodBare1;
    TextView afisareMesaj1;
    public final String TAG = "incarca_descarca";
    AutoCompleteTextView PunctDeLucru;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scan_cod_bare_afisare_pachete_ramase);
        Bundle extras = getIntent().getExtras();
        String preluareIntent = extras.getString("ACTIUNE");
        String id_utilizator = extras.getString("UTILIZATOR");
        PunctDeLucru = findViewById(R.id.ACTV1);
        myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        cod_bare1 = findViewById(R.id.cod_bare1);
        afisareMesaj1 = findViewById(R.id.reporter1);

        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        TextWatcher watchCodBare = new CustomTextWatcher(cod_bare1, afisareMesaj1, preiaCodBare1, this);
        cod_bare1.addTextChangedListener(watchCodBare);
        if (preluareIntent.equals("incarcare")) {
            toolbarSimplu.setSubtitle("Incarca trimiteri:");

        } else {
            toolbarSimplu.setSubtitle("Descarca trimiteri:");

        }
        PopulareAutocomplete();


    }

    public String getPunctLucru() {
        return ((AutoCompleteTextView) findViewById(R.id.ACTV1)).getText().toString();
    }


    public void verificaPL() {
        String locatie = getPunctLucru();
        if (locatie.isEmpty()) {
            Toast.makeText(this, "Selecteaza mai intai un punct de lucru", Toast.LENGTH_SHORT).show();
        }


    }

    public void alertMesajValidari(String title, String alert) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(alert);
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void alertaSunet() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.alarma);
        mp.start();
    }

    public Vibrator vibration() {
        final Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        final long[] pattern = {800, 300};
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < pattern.length; i++) {
                    v.vibrate(pattern, -1);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        v.vibrate(pattern, -1);
        return v;

    }

    @Override
    public void executalaHttpResponse(String sRaspuns) {
        super.executalaHttpResponse(sRaspuns);

        try {
            verificareConexiuneReusita(sRaspuns);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void verificareConexiuneReusita(String sVerificare) throws JSONException {

        Bundle extras = getIntent().getExtras();
        String preluareIntent = extras.getString("ACTIUNE");
        if (preluareIntent.equals("incarcare")) {
            if (sVerificare == null || sVerificare.equals("[]")) {
                Toast.makeText(this, "Codul nu a fost incarcat niciodata", Toast.LENGTH_SHORT).show();
                cod_bare1.getText().clear();
                vibration();
                alertaSunet();
            }
            if (sVerificare != "[]") {
                Gson gson = new Gson();
                //  RaspunsRamuraIncDesc raspunsRamuraIncDesc=new RaspunsRamuraIncDesc(sVerificare,null,null, null, null,null);
                RaspunsRamuraIncDesc[] raspunsRamuraIncDescs = gson.fromJson(sVerificare, RaspunsRamuraIncDesc[].class);
                for (int i = 0; i < raspunsRamuraIncDescs.length; i++) {
                    String a = raspunsRamuraIncDescs[i].getDataora();
                    String b = raspunsRamuraIncDescs[i].getId_antet_trimiteri();
                    String c = raspunsRamuraIncDescs[i].getId_destinatar();
                    String d = raspunsRamuraIncDescs[i].getId_expeditor();
                    String e = raspunsRamuraIncDescs[i].getId_p_lucru();
                    String f = raspunsRamuraIncDescs[i].getId_tip();
                    if (b != "0") {


                        metodaIncarca(cod_bare1.getText().toString());
                        Toast.makeText(this, "Codul  a fost incarcat " + d, Toast.LENGTH_SHORT).show();
                        cod_bare1.setText("");
                    } else
                        if(f=="3" && ){

                        Toast.makeText(this, "Codul nu a fost Incarcat", Toast.LENGTH_SHORT).show();
                        cod_bare1.getText().clear();

                    }
                }
            }

        }


        if (preluareIntent.equals("descarcare")) {
            if (sVerificare == null || sVerificare.equals("[]")) {
                Toast.makeText(this, "Nu exista inregistrari", Toast.LENGTH_SHORT).show();
                cod_bare1.getText().clear();
            }
            if (sVerificare != "[]") {
                Gson gson = new Gson();
                //  RaspunsRamuraIncDesc raspunsRamuraIncDesc=new RaspunsRamuraIncDesc(sVerificare,null,null, null, null,null);
                RaspunsRamuraIncDesc[] raspunsRamuraIncDescs = gson.fromJson(sVerificare, RaspunsRamuraIncDesc[].class);
                for (int i = 0; i < raspunsRamuraIncDescs.length; i++) {
                    String a = raspunsRamuraIncDescs[i].getDataora();
                    String b = raspunsRamuraIncDescs[i].getId_antet_trimiteri();
                    String c = raspunsRamuraIncDescs[i].getId_destinatar();
                    String d = raspunsRamuraIncDescs[i].getId_expeditor();
                    String e = raspunsRamuraIncDescs[i].getId_p_lucru();
                    String f = raspunsRamuraIncDescs[i].getId_tip();
                    if (b != "0") {
                        Toast.makeText(this, "Codul  a fost incarcat de " + d, Toast.LENGTH_SHORT).show();
                        //              vibration();
                        //               alertaSunet();
                        metodaDescarca(cod_bare1.getText().toString());
                        cod_bare1.setText("");
                    } else {

                        Toast.makeText(this, "Codul nu a fost Incarcat", Toast.LENGTH_SHORT).show();
                        cod_bare1.getText().clear();

                    }
                }
            }

        }
    }


    public void metodaIncarca(String sCodBare) {
        String id_utilizator = (Incarca_Descarca_Trimiteri_Activity.this).getIntent().getExtras().getString("UTILIZATOR");

        SQLiteDatabase db = myDb.getWritableDatabase();
        int abc = LogicaVerificari.getId_Antet_Trimiteri(db, sCodBare);
        db.beginTransaction();

        ContentValues cval = new ContentValues();
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI, abc);
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_UTILIZATOR, id_utilizator);
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_TIP, 3);

//        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_P_LUCRU,PunctDeLucru.getText().toString());
        String oop = PunctDeLucru.getText().toString();
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_P_LUCRU, LogicaVerificari.getPunctLucru(db, oop));

        db.insert(Constructor.Tabela_Incarc_Descarc.NUME_TABEL, null, cval);
        db.setTransactionSuccessful();
        db.endTransaction();

    }

    public void metodaDescarca(String sCodBare) {
        String id_utilizator = (Incarca_Descarca_Trimiteri_Activity.this).getIntent().getExtras().getString("UTILIZATOR");

        SQLiteDatabase db = myDb.getWritableDatabase();
        int abc = LogicaVerificari.getId_Antet_Trimiteri(db, sCodBare);
        db.beginTransaction();

        ContentValues cval = new ContentValues();
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_UTILIZATOR, id_utilizator);
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI, abc);
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_TIP, 4);

        db.insert(Constructor.Tabela_Incarc_Descarc.NUME_TABEL, null, cval);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void PopulareAutocomplete() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();
        final String[] Expeditor_Destinatar = LogicaVerificari.getPlucru(db);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Expeditor_Destinatar);
        PunctDeLucru.setAdapter(adapter);

    }

    public int id_P_Lucru() {
        SQLiteDatabase db = myDb.getReadableDatabase();
        int rezultatID = LogicaVerificari.getPunctLucru(db, PunctDeLucru.getText().toString());
        return rezultatID;
    }

}








