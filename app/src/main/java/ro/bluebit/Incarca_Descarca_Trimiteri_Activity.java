package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

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
        setContentView(R.layout.layout_scaneaza_cod_bare);
        myDb = new DatabaseHelper(this);
        SQLiteDatabase db=myDb.getReadableDatabase();
        cod_bare=findViewById(R.id.cod_bare);
        afisareMesaj=findViewById(R.id.reporter);
        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        TextWatcher watchCodBare =new LogicaVerificari.SuperTextWatcher();
        Bundle extras = getIntent().getExtras();
        String preluareIntent = extras.getString("ACTIUNE");
        if (preluareIntent.equals("incarcare")) {
            toolbarSimplu.setSubtitle("Incarca trimiteri:");
        } else
            toolbarSimplu.setSubtitle("Descarca trimiteri:");

        cod_bare.addTextChangedListener(watchCodBare);
//        preiaCodBare=cod_bare.getText().toString();

    }
//VERIFICARE EXISTENTA COD IN PLAJA DE CODURI
    int verificPlajaCod(){
        SQLiteDatabase db = myDb.getReadableDatabase();
         String SQLverifPlaja=(" SELECT " + Constructor.Tabela_Plaja_Cod.COL_ID_LOT + " from " + Constructor.Tabela_Plaja_Cod.NUME_TABEL +
                " where " + preiaCodBare + " between " + Constructor.Tabela_Plaja_Cod.COL_MINIM + " and " + Constructor.Tabela_Plaja_Cod.COL_MAXIM);
        Cursor crs = db.rawQuery(SQLverifPlaja, null);
        crs.moveToFirst();

        int rezultat =crs.getColumnIndexOrThrow(Constructor.Tabela_Plaja_Cod.COL_ID_LOT);
        Log.e(TAG, "verific existenta codului");
       return rezultat;
    }
//VERIFICARE CORECTITUDINE COD
    double verificCorectitudineCod() {
        double corect = LogicaVerificari.verifCorectitudineBare(preiaCodBare);
        Log.e(TAG, "verificCorectitudineCod");
//        if (corect != 1) {
//            afisareMesaj.setText("Nu ai introdus un cod valid");
//            cod_bare.setText("");
  //      }
        return corect;
    }


    }











