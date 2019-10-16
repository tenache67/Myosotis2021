package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.LogicaVerificari;

import static java.lang.Long.parseLong;

public class Incarca_Descarca_Trimiteri_Activity extends AppCompatActivity {
    EditText cod_bare;
    DatabaseHelper myDb;
    Long[] stocareCodBareDinScanner;
    String preiaCodBare;
    TextView afisareMesaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_incarca_descarca_trimiteri);
        myDb = new DatabaseHelper(this);
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
        stocareCodBareDinScanner=new Long[10];

        cod_bare.addTextChangedListener(watchCodBare);


    }


    public TextWatcher watchCodBare =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            if (editable.length()==0)
                afisareMesaj.setText("Nu ai introdus nici o cifra");
                else if (editable.length()<=12)
                afisareMesaj.setText("Codul introdus are mai putin de 13 caractere ");
            else if (editable.length()==13)
                afisareMesaj.setText(preiaCodBare);
                afisareMesaj.getHighlightColor();
            preiaCodBare=cod_bare.getText().toString();

        }
    };

    public Long VerificarePreiaCodBare (){
        int [] preiaCB=new int[1];
        int a=Integer.parseInt(preiaCodBare);
        for (int i=0;i<preiaCB.length; i++);{

        }

        LogicaVerificari.verifCorectitudineCodBare(rezultat[]);
    }
}



//        long asdf=1234567891234l;
//        SQLiteDatabase db =myDb.getReadableDatabase();
//        db.execSQL(LogicaVerificari.SQL_QUERY_OBTINE_VALIDARE_PLAJA_CODURI(asdf));

// AICI URMEAZA INSERTURILE SI ALTE METODE




