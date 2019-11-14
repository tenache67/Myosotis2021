package ro.bluebit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBuffer;

import java.util.ArrayList;
import java.util.List;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.LogicaVerificari;

public class TrimitereNouaDupaCompletareCodQR extends AppCompatActivity {
        Spinner Priotitate,Cond_Speciale;
        AutoCompleteTextView Expeditor,Destinatar;
        Button btnsalvare;
        DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimitere_noua_dupa_completare_cod_qr);

        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Activitate Trimitere Noua");
        Expeditor = findViewById(R.id.ac_expeditor_id);
        Destinatar = findViewById(R.id.ac_destinatar_id);
        Priotitate = findViewById(R.id.spinner_prioritate_id);
        Cond_Speciale = findViewById(R.id.spinner_condspec_id);
        Expeditor.setText(getIntent().getExtras().getString("UserPL"));


        PopulareSpinner(); // Metoda de populare a spinnerelor
        PopulareAutocomplete();
        MetodaSalvareBtnTest();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_salvare, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_salvare) {
            final DatabaseHelper myDb = new DatabaseHelper(this);
            final SQLiteDatabase db = myDb.getWritableDatabase();
            try {

                if (id_P_Lucru() != 0) {
                    id_P_Lucru();

            ContentValues contentValue = new ContentValues();
            contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE, getIntent().getExtras().getString("CodBare"));
            contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_UTILIZATOR, getIntent().getExtras().getString("UTILIZATOR"));
            contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_PRIORITATE,Priotitate.getSelectedItemId());
            contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_CONDITII,Cond_Speciale.getSelectedItemId());


            long idAT = db.insert(Constructor.Tabela_Antet_Trimiteri.NUME_TABEL, null, contentValue); // returneaza id-ul antet trimiteri
            contentValue.clear();
            String expeditor = Expeditor.getText().toString();
            String destinatar = Destinatar.getText().toString();
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_ANTET_TRIMITERI,idAT);
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU,LogicaVerificari.getExpDest(db,expeditor));
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_TIP,LogicaVerificari.getIdTip(db,"Expeditor"));
            db.insert(Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL, null, contentValue);
            contentValue.clear();
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_ANTET_TRIMITERI,idAT);
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU,LogicaVerificari.getExpDest(db,destinatar));
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_TIP,LogicaVerificari.getIdTip(db,"Destinatar"));
            //contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU,LogicaVerificari.getExpDest(db,destinatar));

            //contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_TIP,);
            //SELECT id_p_lucru from tabela_P_Lucru where "Depozit" = denumire
            //
            db.insert(Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL, null, contentValue);

            Toast.makeText(TrimitereNouaDupaCompletareCodQR.this, "Ai inserat in baza de date", Toast.LENGTH_SHORT).show();
            finish();
                } else {

                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), " SELECTEAZA UN DESTINATAR!!", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void PopulareSpinner(){
        List<String> spinnerArrayConditii =  new ArrayList<String>(); // Spinner pentru conditii speciale
        spinnerArrayConditii.add("NU");
        spinnerArrayConditii.add("FRIG");
        spinnerArrayConditii.add("SEPA");

        ArrayAdapter<String> adapterconditii = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayConditii);

        adapterconditii.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItemsConditii = findViewById(R.id.spinner_condspec_id);
        sItemsConditii.setAdapter(adapterconditii);

        List<String> spinnerArrayTipTrimitere =  new ArrayList<String>(); // Spinner pentru conditii speciale
        spinnerArrayTipTrimitere.add("OBISNUIT");
        spinnerArrayTipTrimitere.add("BANI");
        spinnerArrayTipTrimitere.add("ACTE");

        ArrayAdapter<String> adaptertiptrimitere = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayTipTrimitere);

        adapterconditii.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItemsTipTrimitere = findViewById(R.id.spinner_tip_trimitere_id);
        sItemsTipTrimitere.setAdapter(adaptertiptrimitere);

        List<String> spinnerArray =  new ArrayList<String>(); // Spinner pentru prioritate
        spinnerArray.add("NORMAL");
        spinnerArray.add("PRIORITAR");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = findViewById(R.id.spinner_prioritate_id);
        sItems.setAdapter(adapter);
    }
//    public Cursor ObtinePlucru()
//    {
//
//        String Query = "SELECT Denumire FROM tabela_P_lucru";
//        Cursor CR = db.rawQuery(Query, null);
//
//        return CR;
//    }


    public void PopulareAutocomplete(){
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();
        //<String>ArrayPopulare = LogicaVerificari.getPlucru(db);

          final String[]Expeditor_Destinatar = LogicaVerificari.getPlucru(db);
//                "Depozit", "Birou", "Farmacie", "Contabilitate", "M1", "M2", "M3","M4", "M5", "M6"
//
//
//        };

          ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Expeditor_Destinatar);
         Expeditor.setAdapter(adapter);
         Destinatar.setAdapter(adapter);
    }
    public void MetodaSalvareBtnTest(){
        btnsalvare = findViewById(R.id.button);
        final DatabaseHelper myDb = new DatabaseHelper(this);
        final SQLiteDatabase db = myDb.getWritableDatabase();

        btnsalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (id_P_Lucru() != 0) {



                        //  preluare intent id utilizator + cod bare urmand sa fie inserat AICI cu cVal!!!!
                        ContentValues contentValue = new ContentValues();
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE, getIntent().getExtras().getString("CodBare"));
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_UTILIZATOR, getIntent().getExtras().getString("UTILIZATOR"));
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_PRIORITATE, Priotitate.getSelectedItemId());
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_CONDITII, Cond_Speciale.getSelectedItemId());


                        long idAT = db.insert(Constructor.Tabela_Antet_Trimiteri.NUME_TABEL, null, contentValue); // returneaza id-ul antet trimiteri
                        contentValue.clear();
                        String expeditor = Expeditor.getText().toString();
                        String destinatar = Destinatar.getText().toString();
                        contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_ANTET_TRIMITERI, idAT);
                        contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU, LogicaVerificari.getExpDest(db, expeditor));
                        contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_TIP, LogicaVerificari.getIdTip(db, "Expeditor"));
                        db.insert(Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL, null, contentValue);
                        contentValue.clear();
                        contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_ANTET_TRIMITERI, idAT);
                        contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU, LogicaVerificari.getExpDest(db, destinatar));
                        contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_TIP, LogicaVerificari.getIdTip(db, "Destinatar"));
                        //contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU,LogicaVerificari.getExpDest(db,destinatar));

                        //contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_TIP,);
                        //SELECT id_p_lucru from tabela_P_Lucru where "Depozit" = denumire
                        //
                        db.insert(Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL, null, contentValue);

                        Toast.makeText(TrimitereNouaDupaCompletareCodQR.this, "Ai inserat in baza de date", Toast.LENGTH_SHORT).show();
                        finish();
//                Intent intent = new Intent(TrimitereNouaDupaCompletareCodQR.this,ActivitateTrimitereNoua.class);
//                startActivity(intent);

                    } else {

                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SELECTEAZA UN DESTINATAR!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public int id_P_Lucru() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        String denumireplucru = getIntent().getExtras().getString("UserPL");
        int rezultatID = LogicaVerificari.getPunctLucru(db, Destinatar.getText().toString().trim());
        return rezultatID;
    }
}
