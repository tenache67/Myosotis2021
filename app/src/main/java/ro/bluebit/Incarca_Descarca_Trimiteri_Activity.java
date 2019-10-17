package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.LogicaVerificari;

import static java.lang.Long.parseLong;

public class Incarca_Descarca_Trimiteri_Activity extends AppCompatActivity {
    EditText cod_bare;
    DatabaseHelper myDb;
    Long[] stocareCodBareDinScanner;
    String preiaCodBare;
    TextView afisareMesaj;
    public final String TAG= "incarca_descarca";
    public static ArrayList<String> StocareCodBare = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_incarca_descarca_trimiteri);
        myDb = new DatabaseHelper(this);
        SQLiteDatabase db=myDb.getReadableDatabase();
        cod_bare=findViewById(R.id.cod_bare);
        afisareMesaj=findViewById(R.id.reporter);
        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);

        Bundle extras = getIntent().getExtras();
        String preluareIntent = extras.getString("ACTIUNE");
        if (preluareIntent.equals("incarcare")) {
            toolbarSimplu.setSubtitle("Incarca trimiteri:");
        } else
            toolbarSimplu.setSubtitle("Descarca trimiteri:");

        cod_bare.addTextChangedListener(watchCodBare);
        preiaCodBare=cod_bare.getText().toString();

    }
//VERIFICARE EXISTENTA COD IN PLAJA DE CODURI
    int verificPlajaCod(){
        SQLiteDatabase db = myDb.getReadableDatabase();
         String SQLverifPlaja=(" SELECT " + Constructor.Tabela_Plaja_Cod.COL_ID_LOT + " from " + Constructor.Tabela_Plaja_Cod.NUME_TABEL +
                " where " + preiaCodBare + " between " + Constructor.Tabela_Plaja_Cod.COL_MINIM + " and " + Constructor.Tabela_Plaja_Cod.COL_MAXIM);
        Cursor crs = db.rawQuery(SQLverifPlaja, null);
  //      crs.moveToFirst();

        int rezultat =crs.getColumnIndexOrThrow(Constructor.Tabela_Plaja_Cod.COL_ID_LOT);
        Log.e(TAG, "verific existenta codului");
       return rezultat;
    }
//VERIFICARE CORECTITUDINE COD
//    boolean verificCorectitudineCod() {
//        boolean bol = LogicaVerificari.verifCorectitudineBare(preiaCodBare);
//        Log.e(TAG, "verificCorectitudineCod");
//        if (bol = false) {
//            afisareMesaj.setText("Nu ai introdus un cod valid");
//            cod_bare.setText("");
//
//    }return bol;
//    }

// CREAREA TEXT WATCHERULUI PERSONALIZAT
    public TextWatcher watchCodBare =new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        private Timer timer = new Timer();
        private final long DELAY = 5000; // milliseconds


        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length()==0)
                afisareMesaj.setText("Nu ai introdus nici o cifra");
                else if (editable.length()<=12)
                afisareMesaj.setText("Codul introdus are mai putin de 13 caractere ");

            else
                if (editable.length()==13 )
                afisareMesaj.setText(preiaCodBare);
                preiaCodBare=cod_bare.getText().toString();
            timer.cancel();
            timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override

                        public void run() {
                          //  if ( verificCorectitudineCod ()|| verificPlajaCod() > 0)
                             if ( verificPlajaCod() > 0)
                            StocareCodBare.add(preiaCodBare);
                            // cod_bare.setText("");
                        }
                    },
                    DELAY
            );


        }
    };


}








