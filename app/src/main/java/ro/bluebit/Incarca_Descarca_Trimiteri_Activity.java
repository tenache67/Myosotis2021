package ro.bluebit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.CustomTextWatcher;
import ro.bluebit.UTILITARE.LogicaVerificari;
import ro.bluebit.UTILITARE.SelectieInitialaActivity;

import static java.lang.Long.parseLong;

public class Incarca_Descarca_Trimiteri_Activity extends BazaAppCompat {
    EditText cod_bare;
    DatabaseHelper myDb;
    Long[] stocareCodBareDinScanner;
    String preiaCodBare;
    TextView afisareMesaj;
    public final String TAG = "incarca_descarca";
    public static ArrayList<String> StocareCodBare = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scaneaza_cod_bare);
        myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        cod_bare = findViewById(R.id.cod_bare);
        afisareMesaj = findViewById(R.id.reporter);
        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        TextWatcher watchCodBare = new CustomTextWatcher(cod_bare, afisareMesaj, preiaCodBare, this);
        Bundle extras = getIntent().getExtras();
        String preluareIntent = extras.getString("ACTIUNE");
        String id_utilizator = extras.getString("UTILIZATOR");
        if (preluareIntent.equals("incarcare")) {
            toolbarSimplu.setSubtitle("Incarca trimiteri:");
        } else
            toolbarSimplu.setSubtitle("Descarca trimiteri:");

        cod_bare.addTextChangedListener(watchCodBare);


    }

    public void alertMesajValidari(String title, String alert) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(alert);
        alertDialog.setTitle(title);
        alertDialog.setCancelable(true);
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
        //transformarea in long a sirului da caractere din edittext
        long codBareLung = parseLong(sCodBare);
        //verificarea existentei inregistrarii in tabela Antet  Trimiteri
        boolean existInAntetTrimiteri = LogicaVerificari.verificareExistentaInAntetTrimiteri(db, codBareLung);

        if (!existInPlajaCoduri) {
//            Toast.makeText(this, "Codul de bare " + sCodBare + "nu exista in lotul de coduri", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "Corectati codul sau introduceti unul nou", Toast.LENGTH_SHORT).show();
            alertMesajValidari("Cod Inexistent", "Nu face parte din codurile noastre");
            vibration();
            alertaSunet();
            cod_bare.setText("");
        }

        if (existInPlajaCoduri && !existInAntetTrimiteri) {
            alertMesajValidari("Cod Nou!", "Codul nu a fost folosit pentru o trimitere noua! ");
            vibration();
            alertaSunet();
            cod_bare.setText("");
        }
        //     Toast.makeText(this, "Codul este unul nou, trebuie introdus la Trimitere noua", Toast.LENGTH_LONG).show();
        //   cod_bare.setText("");

        if (existInPlajaCoduri && existInAntetTrimiteri) {
            Bundle extras = getIntent().getExtras();
            String preluareIntent = extras.getString("ACTIUNE");
            if (preluareIntent.equals("incarcare")) {
                metodaIncarca(codBareLung);
            } else
                metodaDescarca(codBareLung);
            cod_bare.setText("");

        }

    }

    public void metodaIncarca(long codBareLung) {
            String id_utilizator = (Incarca_Descarca_Trimiteri_Activity.this).getIntent().getExtras().getString("UTILIZATOR");
        int abc = LogicaVerificari.getId_Antet_Trimiteri(codBareLung);
        SQLiteDatabase db = myDb.getWritableDatabase();
        db.beginTransaction();

        ContentValues cval = new ContentValues();
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_UTILIZATOR, id_utilizator);
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI, abc);
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_TIP, 3);

        db.insert(Constructor.Tabela_Incarc_Descarc.NUME_TABEL, null, cval);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void metodaDescarca(long codBareLung) {
        String id_utilizator = (Incarca_Descarca_Trimiteri_Activity.this).getIntent().getExtras().getString("UTILIZATOR");
        int abc = LogicaVerificari.getId_Antet_Trimiteri(codBareLung);
        SQLiteDatabase db = myDb.getWritableDatabase();
        db.beginTransaction();

        ContentValues cval = new ContentValues();
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_UTILIZATOR, id_utilizator);
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI, abc);
        cval.put(Constructor.Tabela_Incarc_Descarc.COL_ID_TIP, 4);

        db.insert(Constructor.Tabela_Incarc_Descarc.NUME_TABEL, null, cval);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

}







