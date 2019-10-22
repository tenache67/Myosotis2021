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
import ro.bluebit.UTILITARE.CustomTextWatcher;
import ro.bluebit.UTILITARE.LogicaVerificari;

import static java.lang.Long.parseLong;

public class Incarca_Descarca_Trimiteri_Activity extends BazaAppCompat {
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
        TextWatcher watchCodBare =new CustomTextWatcher(cod_bare,afisareMesaj,preiaCodBare,this);
        Bundle extras = getIntent().getExtras();
        String preluareIntent = extras.getString("ACTIUNE");
        if (preluareIntent.equals("incarcare")) {
            toolbarSimplu.setSubtitle("Incarca trimiteri:");
        } else
            toolbarSimplu.setSubtitle("Descarca trimiteri:");

        cod_bare.addTextChangedListener(watchCodBare);


    }

    //obtinere acces la baza de date

//    SQLiteDatabase db = myDb.getReadableDatabase();
//    //inlaturarea zerourilor din sirul de caractere
//    String codBareFaraZerouri= LogicaVerificari.RemoveZero(preiaCodBare);
//    //transformarea sirului de caractere in long
//    long codBareScurt = parseLong(codBareFaraZerouri);
//    //verificare existenta in plaja de coduri
//    boolean existInPlajaCoduri =LogicaVerificari.verificareExistentaInPlajaDeCoduri(db, codBareScurt);
//    //transformarea in long a sirului da caractere din edittext
//    long codBareLung = parseLong(preiaCodBare);
//    //verificarea existentei inregistrarii in tabela Antet  Trimiteri
//    boolean existInAntetTrimiteri =LogicaVerificari.verificareExistentaInAntetTrimiteri(db,codBareLung);

}

















