package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.LogicaVerificari;

public class Incarca_Descarca_Trimiteri_Activity extends AppCompatActivity {
    EditText cod_bare;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_incarca_trimiteri);

        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Incarca trimiteri:");

        EditText cod_bare=findViewById(R.id.cod_bare);


        Bundle extras = getIntent().getExtras();
        String incarcare = String.valueOf(extras.getInt("incarcare"));
        String descarcare = String.valueOf(extras.getInt("descarcare"));
        //validarea daca lungimea codului este corecta

        }





    //validarea  lungimii codului de bare
    public void  verificaLungimeCobBare() {
        String cod = cod_bare.getText().toString();
        int a = cod.length();
        if (a != 13) {
            Toast.makeText(this, "Cod Gresit", Toast.LENGTH_SHORT).show();
        }
    }
    // validarea din plaja de coduri
//    public boolean verificaPlajaCodBare(){
//        String cod = cod_bare.getText().toString();
//        SQLiteDatabase db =myDb.getReadableDatabase();
//         int id_lot_Cod_Bare=db.execSQL(LogicaVerificari.SQL_QUERY_OBTINE_VALIDARE_PLAJA_CODURI(Integer.parseInt(cod_bare.getText().toString().trim())));
//        if (id_lot_Cod_Bare==0){
//            Toast.makeText(this, " Codul de bare nu exista in baza de date", Toast.LENGTH_SHORT).show();
//        }return  false;
//    }




    public void IntroducereCodQR() {

        EditText cod_bare=findViewById(R.id.cod_bare);
        cod_bare.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    private Timer timer = new Timer();
                    private final long DELAY = 1000; // milliseconds

                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getApplicationContext(),TrimitereNouaDupaCompletareCodQR.class);
                                        startActivity(intent);
                                    }
                                },
                                DELAY
                        );
                    }
                }
        );
    }
}



