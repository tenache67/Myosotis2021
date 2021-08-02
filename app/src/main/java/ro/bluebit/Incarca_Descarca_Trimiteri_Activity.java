package ro.bluebit;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;


import org.json.JSONException;

import java.io.IOException;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.Database.MySQLHelper;
import ro.bluebit.UTILITARE.CustomTextWatcher;
import ro.bluebit.UTILITARE.LogicaVerificari;
import ro.bluebit.Diverse.Siruri;
import ro.bluebit.phpmysql.RaspunsRamuraIncDesc;

import static java.lang.Long.parseLong;

// import com.google.
// actualizat activitatea de logare
public class Incarca_Descarca_Trimiteri_Activity extends BazaAppCompat {
    EditText cod_bare1;
    DatabaseHelper myDb;
    Long[] stocareCodBareDinScanner;
    String preiaCodBare1;
    TextView afisareMesaj1, numarpachete;
    public final String TAG = "incarca_descarca";
    AutoCompleteTextView PunctDeLucru;
    Button btn_adaug_manual;
    EditText cod_bare2;
    @Override
    public void executalaHttpResponse(String sScop,String sRaspuns) {
        super.executalaHttpResponse(sScop,sRaspuns);
        if (sScop.equals("SINCRONIZARE_TRIMITERI")) {
            // sincrinizare date ce vin din din server
            Log.d(sScop,"start");
            myDb.sincronizare_trimiteri(sRaspuns);
        } else if (sScop.equals("SINCRONIZARE_RECEPTIE")) {
            // sincronizare date create in aparat
            myDb.sincronizare_receptie(sRaspuns);
        }
        //      stergeRaspuns(sScop);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogicaVerificari.executaSincroNomenc(this) ;
        LogicaVerificari.executaSincroTrimiteri(this);
        LogicaVerificari.executaSincroRecTrimiteri(this);

        setContentView(R.layout.layout_scan_cod_bare_afisare_pachete_ramase);
        Bundle extras = getIntent().getExtras();
        String preluareIntent = extras.getString("ACTIUNE");
        String id_utilizator = extras.getString("UTILIZATOR");
        PunctDeLucru = findViewById(R.id.ACTV1);
        numarpachete = findViewById(R.id.TVnrObiectedeDescarcat);
        btn_adaug_manual=findViewById(R.id.BTN_introduc_manual_codul);
        adaug_manual_cod_bare();

//        if (preluareIntent.equals("incarcare")) {
//            setContentView(R.layout.layout_scaneaza_cod_bare);}
//        else{
//            setContentView(R.layout.layout_scan_cod_bare_afisare_pachete_ramase);
//
//        }

        myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        cod_bare2= findViewById(R.id.cod_bare2);
        cod_bare2.setEnabled(false);
        cod_bare1 = findViewById(R.id.cod_bare1);
        cod_bare1.setEnabled(false);
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

        //     int testez=LogicaVerificari.iGetNumarDeCoduriBare(db,10);
    }

    public void AdauganrColete(){
        if (cod_bare1.isEnabled()){
            numarpachete.setText("Noooo");
            numarPacheteDeDescarcat();
        }
        else  alertMesajValidari("oooooo", "nuuuuu, eroare de numarare");

    }

    public void numarPacheteDeDescarcat(){
        myDb = new DatabaseHelper(Incarca_Descarca_Trimiteri_Activity.this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        int numar=get_id_P_Lucru();
        numarpachete.setText(Integer.toString( LogicaVerificari.iGetNumarDeCoduriBare(db,numar)));
        // alertMesajValidari("oooooo", "nuuuuu");
    }

    public void adaug_manual_cod_bare(){
        btn_adaug_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (btn_adaug_manual.getText().toString()){
                    case "Introduc manual codul":
                        cod_bare2.setEnabled(true);
                        cod_bare2.requestFocus();
                        btn_adaug_manual.setText("Adauga codul");
                        break;
                    case "Adauga codul":
                        if(cod_bare2.length()<1){
                            cod_bare2.getText().clear();

                        }
                        else{
                            cod_bare1.setText(cod_bare2.getText().toString());
                            cod_bare2.setText("");
                            btn_adaug_manual.setText("Introduc manual codul");
                            cod_bare1.requestFocus();
                        }
                        break;
                }
            }
        });
    }



    public int id_P_Lucru() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        String denumireplucru = getIntent().getExtras().getString("UserPL");
        int rezultatID = LogicaVerificari.getPunctLucru(db, PunctDeLucru.getText().toString().trim());
        return rezultatID;
    }
    public   void alertMesajValidari(String title, String alert) {
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
    public void executalacodvalid(String sCodBare) {


        super.executalacodvalid(sCodBare);

        SQLiteDatabase db = myDb.getReadableDatabase();
        //inlaturarea zerourilor din sirul de caractere
        String codBareFaraZerouri = LogicaVerificari.RemoveZero(sCodBare);
        //transformarea sirului de caractere in long
        long codBareScurt = parseLong(codBareFaraZerouri);
        //verificare existenta in plaja de coduri
        boolean existInPlajaCoduri = LogicaVerificari.verificareExistentaInPlajaDeCoduri(db, codBareScurt);

        int existInIncarcDescarc = LogicaVerificari.getExistentaIncDesc(db, sCodBare);

        //verificarea existentei inregistrarii in tabela Antet  Trimiteri
        boolean existInAntetTrimiteri = LogicaVerificari.verificareExistentaInAntetTrimiteri(db, sCodBare);
//        String sQueryExistinAntet = ("select *  from v_verifica_in_trimiteri where '" + sCodBare + "'=cod_bare");

        String sQueryExInAntet = (" select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + " from " +
                Constructor.Tabela_Antet_Trimiteri.NUME_TABEL +
                " where '" + sCodBare + "' = " + Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE);
        Bundle extras = getIntent().getExtras();
        String preluareIntent = extras.getString("ACTIUNE");

        if (!existInPlajaCoduri) {
            Toast.makeText(this, "Codul de bare " + sCodBare + "nu exista in lotul de coduri", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Corectati codul sau introduceti unul nou", Toast.LENGTH_SHORT).show();
            alertMesajValidari("Cod Inexistent", "Nu face parte din codurile noastre");
            vibration();
            alertaSunet();
            cod_bare1.setText("");
        }
        if (existInPlajaCoduri && !existInAntetTrimiteri) {
            alertMesajValidari("Cod Nou!", "Codul nu a fost folosit pentru o trimitere noua! ");
            vibration();
            alertaSunet();
            cod_bare1.setText("");
        }
        if (existInPlajaCoduri && existInAntetTrimiteri && preluareIntent.equals("incarcare")) {
            if (existInIncarcDescarc==4) {
                Toast.makeText(this, "Ai realizat o incarcare noua", Toast.LENGTH_LONG).show();
                metodaIncarca(sCodBare);
                cod_bare1.getText().clear();
            }
            else {

                Toast.makeText(this, "Codul a mai fost incarcat", Toast.LENGTH_LONG).show();
                cod_bare1.getText().clear();

            }
        }

        if (existInPlajaCoduri && existInAntetTrimiteri && preluareIntent.equals("descarcare")) {
            if (existInIncarcDescarc==3) {
                // alertMesajValidari("Cod", "Codul a  fost descarcat");

                Toast.makeText(this, "codul a  fost descarcat ", Toast.LENGTH_LONG).show();
                metodaDescarca(sCodBare);
                cod_bare1.getText().clear();
            }else {
                alertMesajValidari("Atentie!", "Codul a mai fost descarcat");
                // Toast.makeText(this, "codul a mai fost  descarcat ", Toast.LENGTH_SHORT).show();
                cod_bare1.getText().clear();
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
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_DATA, Siruri.ttos(Siruri.getDateTime()));
        db.insert(Constructor.Tabela_Incarc_Descarc_Alt.NUME_TABEL, null, cval);
        db.setTransactionSuccessful();
        db.endTransaction();
        LogicaVerificari.executaSincroNomenc(this) ;
        LogicaVerificari.executaSincroTrimiteri(this);
        LogicaVerificari.executaSincroRecTrimiteri(this);

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
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_DATA, Siruri.ttos(Siruri.getDateTime()));

        db.insert(Constructor.Tabela_Incarc_Descarc_Alt.NUME_TABEL, null, cval);
        db.setTransactionSuccessful();
        db.endTransaction();
        // sincronizare
        LogicaVerificari.executaSincroNomenc(this) ;
        LogicaVerificari.executaSincroTrimiteri(this);
        LogicaVerificari.executaSincroRecTrimiteri(this);

    }

    public void PopulareAutocomplete() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();
        final String[] Expeditor_Destinatar = LogicaVerificari.getPlucru(db);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Expeditor_Destinatar);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        PunctDeLucru.setAdapter(adapter);
        PunctDeLucru.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,  View view, int i, long l) {

                Object item = parent.getItemAtPosition(i);
                String selectedItem = (String) parent.getItemAtPosition(i);
                if (item==selectedItem){
                    cod_bare1.setEnabled(true);
                    cod_bare1.requestFocus();
                    AdauganrColete();
                    //  alertMesajValidari("oooooo", "nuuuuu");

                }
            }
        });
//        PunctDeLucru.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//               // numarPacheteDeDescarcat();
//                Object item = adapterView.getItemAtPosition(i);
//                if (item != null) {
//                    alertMesajValidari("1", "2" );
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//           alertMesajValidari("5","5");
//            }
//        });

        //  afisezNrColete();
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//spinner.setAdapter(adapter);

//        cod_bare1.setEnabled(true);
//        cod_bare1.requestFocus();

    }

    public int get_id_P_Lucru() {
        SQLiteDatabase db = myDb.getReadableDatabase();
        int rezultatID = LogicaVerificari.getPunctLucru(db, PunctDeLucru.getText().toString());
        return rezultatID;
    }

}







