package ro.bluebit;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.Database.MySQLHelper;
import ro.bluebit.UTILITARE.CustomTextWatcher;
import ro.bluebit.UTILITARE.LogicaVerificari;
import ro.bluebit.phpmysql.RaspunsRamuraIncDesc;

import static java.lang.Long.parseLong;

// import com.google.

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
//        if (preluareIntent.equals("incarcare")) {
//            setContentView(R.layout.layout_scaneaza_cod_bare);}
//        else{
//            setContentView(R.layout.layout_scan_cod_bare_afisare_pachete_ramase);
//
//        }

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


    public void verificareConexiuneReusita(String sVerificare) throws JSONException {
        //sVerificare="";
        Gson gson = new Gson();
        SQLiteDatabase db = myDb.getWritableDatabase();
        //  RaspunsRamuraIncDesc raspunsRamuraIncDesc=new RaspunsRamuraIncDesc(sVerificare,null,null, null, null,null);
        RaspunsRamuraIncDesc[] raspunsRamuraIncDescs = gson.fromJson(sVerificare, RaspunsRamuraIncDesc[].class);
        for (int i = 0; i < raspunsRamuraIncDescs.length; i++) {
            String a = raspunsRamuraIncDescs[i].getDataora();
            String b = raspunsRamuraIncDescs[i].getId_antet_trimiteri();
            String c = raspunsRamuraIncDescs[i].getId_destinatar();
            String d = raspunsRamuraIncDescs[i].getId_expeditor();
            int e = Integer.parseInt(raspunsRamuraIncDescs[i].getId_p_lucru());
            String f = raspunsRamuraIncDescs[i].getId_tip();
            Bundle extras = getIntent().getExtras();
            String preluareIntent = extras.getString("ACTIUNE");

            if (preluareIntent.equals("incarcare")) {

                if (sVerificare == null || sVerificare.equals("[]")) {

                    vibration();
                    alertaSunet();
                    alertMesajValidari("Eroare", "Codul nu apartine niciunei descarcari");
                    cod_bare1.getText().clear();
                } else if (sVerificare != "[]") {
                    if (f == "3") {
                        Toast.makeText(this, "Codul nu a fost Incarcat deoarece :", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "a mai fort incarcat o data de  :" + LogicaVerificari.getDenumirePunctLucru(db, e), Toast.LENGTH_SHORT).show();
                        cod_bare1.getText().clear();

                    } else if (f == "4" ) {


                        metodaIncarca(cod_bare1.getText().toString());
                        Toast.makeText(this, "Codul  a fost incarcat " + d, Toast.LENGTH_SHORT).show();
                        cod_bare1.setText("");
                    }

                    Toast.makeText(this, "Codul a fost incarcat", Toast.LENGTH_SHORT).show();
                }

            } else {

                if (sVerificare == null || sVerificare.equals("[]")) {
                    vibration();
                    alertaSunet();
                    alertMesajValidari("Eroare", "Codul nu apartine niciunei incarcari");
                    cod_bare1.getText().clear();
                } else if (sVerificare != "[]") {
                    if (f == "4") {
                        Toast.makeText(this, "Codul nu a fost descarcat deoarece :", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "a mai fort descarcat  o data de  :" + LogicaVerificari.getDenumirePunctLucru(db, e), Toast.LENGTH_SHORT).show();
                        cod_bare1.getText().clear();

                    } else if (f == "3") {


                        metodaDescarca(cod_bare1.getText().toString());
                        Toast.makeText(this, "Codul  a fost incarcat " + d, Toast.LENGTH_SHORT).show();
                        cod_bare1.setText("");
                    }

                    Toast.makeText(this, "Codul a fost incarcat", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void executalacodvalid(String sCodBare) {


        super.executalacodvalid(sCodBare);

        SQLiteDatabase db = myDb.getReadableDatabase();
        //inlaturarea zerourilor din sirul de caractere
        String codBareFaraZerouri = LogicaVerificari.RemoveZero(sCodBare);
        //transformarea sirului de caractere in long
        long codBareScurt = parseLong(codBareFaraZerouri);
        //verificare existenta in plaja de coduri
        boolean existInPlajaCoduri = LogicaVerificari.verificareExistentaInPlajaDeCoduri(db, codBareScurt);
//        boolean existInIncarcare = LogicaVerificari.getExistentaInc(db, sCodBare);
//        boolean existInDescarcare = LogicaVerificari.getExistentaDesc(db, sCodBare);
        //verificarea existentei inregistrarii in tabela Antet  Trimiteri
        //   boolean existInAntetTrimiteri = LogicaVerificari.verificareExistentaInAntetTrimiteri(db, sCodBare);
        String sQueryExistinAntet = ("select *  from v_verifica_in_trimiteri where '" + sCodBare + "'=cod_bare");

//        String sQueryExInAntet = (" select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + " from " +
//                Constructor.Tabela_Antet_Trimiteri.NUME_TABEL +
//                " where '" + sCodBare + "' = " + Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE);


        if (!existInPlajaCoduri) {
            Toast.makeText(this, "Codul de bare " + sCodBare + "nu exista in lotul de coduri", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Corectati codul sau introduceti unul nou", Toast.LENGTH_SHORT).show();
            alertMesajValidari("Cod Inexistent", "Nu face parte din codurile noastre");
            vibration();
            alertaSunet();
            cod_bare1.setText("");
        }
        if (existInPlajaCoduri) {
            try {
                //      this.sHttpResponse = null;
                MySQLHelper.postRequest("test_mysql.php", sQueryExistinAntet, this);
            } catch (IOException e) {
                e.printStackTrace();
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

        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_P_LUCRU, get_id_P_Lucru());
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
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_P_LUCRU, get_id_P_Lucru());

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

    public int get_id_P_Lucru() {
        SQLiteDatabase db = myDb.getReadableDatabase();
        int rezultatID = LogicaVerificari.getPunctLucru(db, PunctDeLucru.getText().toString());
        return rezultatID;
    }

}







