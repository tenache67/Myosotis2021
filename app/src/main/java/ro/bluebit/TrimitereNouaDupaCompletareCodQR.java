package ro.bluebit;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.Diverse.Siruri;
import ro.bluebit.UTILITARE.LogicaVerificari;

public class TrimitereNouaDupaCompletareCodQR extends BazaAppCompat {
        Spinner Priotitate,Cond_Speciale,Tip_Trimitere;
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
        Tip_Trimitere = findViewById(R.id.spinner_tip_trimitere_id);

        Expeditor.setText(getIntent().getExtras().getString("UserPL"));


        PopulareSpinner(); // Metoda de populare a spinnerelor
        PopulareAutocomplete();
        MetodaSalvareBtnTest();

        LogicaVerificari.executaSincroNomenc(this) ;
        LogicaVerificari.executaSincroTrimiteri(this);
        LogicaVerificari.executaSincroRecTrimiteri(this);

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
            if (Expeditor.getText().toString().equals(Destinatar.getText().toString())) {
                Toast.makeText(getApplicationContext(), "DESTINATAR SI EXPEDITOR IDENTICI!!!", Toast.LENGTH_SHORT).show();

            }else
            try {

                if (id_P_Lucru() != 0) {
                    id_P_Lucru();

                    ContentValues contentValue = new ContentValues();
                    contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE, getIntent().getExtras().getString("CodBare"));
                    contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_UTILIZATOR, getIntent().getExtras().getString("UTILIZATOR"));
                    if (Priotitate.getSelectedItemId() == 0) {
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_PRIORITATE, 11);
                    } else
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_PRIORITATE, 12);
                    if (Cond_Speciale.getSelectedItemId() == 0)
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_CONDITII, 8);
                    if (Cond_Speciale.getSelectedItemId() == 1)
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_CONDITII, 9);
                    else
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_CONDITII, 10);
                    if (Tip_Trimitere.getSelectedItemId() == 0)
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_TIP_TRIMITERE, 5);
                    if (Tip_Trimitere.getSelectedItemId() == 1)
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_TIP_TRIMITERE, 6);
                    else {
                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_TIP_TRIMITERE, 7);
                    }


                        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_DATA, Siruri.ttos(Siruri.getDateTime()));




            long idAT = db.insert(Constructor.Tabela_Antet_Trimiteri_Alt.NUME_TABEL, null, contentValue); // returneaza id-ul antet trimiteri
            contentValue.clear();
            String expeditor = Expeditor.getText().toString();
            String destinatar = Destinatar.getText().toString();
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_ANTET_TRIMITERI,idAT);
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_P_LUCRU,LogicaVerificari.getExpDest(db,expeditor));
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_TIP,LogicaVerificari.getIdTip(db,"Expeditor"));
            db.insert(Constructor.Tabela_Pozitii_Trimiteri_Alt.NUME_TABEL, null, contentValue);
            contentValue.clear();
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_ANTET_TRIMITERI,idAT);
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_P_LUCRU,LogicaVerificari.getExpDest(db,destinatar));
            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_TIP,LogicaVerificari.getIdTip(db,"Destinatar"));
            db.insert(Constructor.Tabela_Pozitii_Trimiteri_Alt.NUME_TABEL, null, contentValue);
            db.setTransactionSuccessful();
            db.endTransaction();
            metodaIncarca(getIntent().getExtras().getString("CodBare"));

            Toast toast = Toast.makeText(TrimitereNouaDupaCompletareCodQR.this, "Trimitere adaugata cu succes", Toast.LENGTH_SHORT);
             toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
             toast.show();
            finish();
                } else {

                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), " SELECTEAZA UN DESTINATAR!", Toast.LENGTH_SHORT).show();
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
    public void metodaIncarca(String sCodBare) {
        String id_utilizator = (TrimitereNouaDupaCompletareCodQR.this).getIntent().getExtras().getString("UTILIZATOR");

        final DatabaseHelper myDb = new DatabaseHelper(this);
        final SQLiteDatabase db = myDb.getWritableDatabase();
        int abc = LogicaVerificari.getId_Antet_Trimiteri(db, sCodBare);
        db.beginTransaction();

        ContentValues cval = new ContentValues();
        cval.put(Constructor.Tabela_Incarc_Descarc_Alt.COL_ID_UTILIZATOR, id_utilizator);
        cval.put(Constructor.Tabela_Incarc_Descarc_Alt.COL_ID_ANTET_TRIMITERI, abc);
        cval.put(Constructor.Tabela_Incarc_Descarc_Alt.COL_ID_TIP, 3);
        String expeditor = Expeditor.getText().toString();
        cval.put(Constructor.Tabela_Incarc_Descarc_Alt.COL_ID_P_LUCRU, LogicaVerificari.getExpDest(db,expeditor));
        cval.put(Constructor.Tabela_Incarc_Descarc_Alt.COL_DATA, Siruri.ttos(Siruri.getDateTime()));
        db.insert(Constructor.Tabela_Incarc_Descarc_Alt.NUME_TABEL, null, cval);
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public void MetodaSalvareBtnTest(){
        btnsalvare = findViewById(R.id.button);
        final DatabaseHelper myDb = new DatabaseHelper(this);
        final SQLiteDatabase db = myDb.getWritableDatabase();

        btnsalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Expeditor.getText().toString().equals(Destinatar.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "DESTINATAR SI EXPEDITOR IDENTICI!!!", Toast.LENGTH_SHORT).show();

                }else
                    try {

                        if (id_P_Lucru() != 0) {


                            //  preluare intent id utilizator + cod bare urmand sa fie inserat AICI cu cVal!!!!
                            ContentValues contentValue = new ContentValues();
                            contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE, getIntent().getExtras().getString("CodBare"));
                            contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_UTILIZATOR, getIntent().getExtras().getString("UTILIZATOR"));
                            if (Priotitate.getSelectedItemId() == 0) {
                                contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_PRIORITATE, 11);
                            } else
                                contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_PRIORITATE, 12);
                            if (Cond_Speciale.getSelectedItemId() == 0)
                                contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_CONDITII, 8);
                            if (Cond_Speciale.getSelectedItemId() == 1)
                                contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_CONDITII, 9);
                            else
                                contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_CONDITII, 10);
                            if (Tip_Trimitere.getSelectedItemId() == 0)
                                contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_TIP_TRIMITERE, 5);
                            if (Tip_Trimitere.getSelectedItemId() == 1)
                                contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_TIP_TRIMITERE, 6);
                            else {
                                contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_TIP_TRIMITERE, 7);
                            }
                            contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_DATA, Siruri.ttos(Siruri.getDateTime()));


                            long idAT = db.insert(Constructor.Tabela_Antet_Trimiteri_Alt.NUME_TABEL, null, contentValue); // returneaza id-ul antet trimiteri
                            contentValue.clear();
                            String expeditor = Expeditor.getText().toString();
                            String destinatar = Destinatar.getText().toString();
                            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_ANTET_TRIMITERI, idAT);
                            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_P_LUCRU, LogicaVerificari.getExpDest(db, expeditor));
                            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_TIP, LogicaVerificari.getIdTip(db, "Expeditor"));
                            db.insert(Constructor.Tabela_Pozitii_Trimiteri_Alt.NUME_TABEL, null, contentValue);
                            contentValue.clear();
                            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_ANTET_TRIMITERI, idAT);
                            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_P_LUCRU, LogicaVerificari.getExpDest(db, destinatar));
                            contentValue.put(Constructor.Tabela_Pozitii_Trimiteri_Alt.COL_ID_TIP, LogicaVerificari.getIdTip(db, "Destinatar"));
                            //contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU,LogicaVerificari.getExpDest(db,destinatar));

                            //contentValue.put(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_TIP,);
                            //SELECT id_p_lucru from tabela_P_Lucru where "Depozit" = denumire
                            //
                            db.insert(Constructor.Tabela_Pozitii_Trimiteri_Alt.NUME_TABEL, null, contentValue);

                            metodaIncarca(getIntent().getExtras().getString("CodBare"));

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
    //Tastatura dispare la scroll
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public int id_P_Lucru() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        String denumireplucru = getIntent().getExtras().getString("UserPL");
        int rezultatID = LogicaVerificari.getPunctLucru(db, Destinatar.getText().toString().trim());
        return rezultatID;
    }
}
