package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.LogicaVerificari;
import ro.bluebit.UTILITARE.SelectieInitialaActivity;

public class MainActivity extends AppCompatActivity {
    Button acceseaza;
    TextView afisez;
    EditText parola;
    DatabaseHelper myDb;
    AutoCompleteTextView PunctDeLucru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
// crearea datelor de proba - am pus aici ca sa ruleze de fiecate data la pornire
        myDb.dateProba();

        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Logare in aplicatie");

        parola = findViewById(R.id.editTextPIN);
        afisez = findViewById(R.id.afisareMesajeTV);
        PunctDeLucru = findViewById(R.id.ACTV);
        acceseaza = findViewById(R.id.button_logare);

        acceseaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       String pin=parola.getText().toString().trim();
                Boolean res = myDb.verificPin(parola.getText().toString());

                try {

                    if (res && id_P_Lucru() != 0) {
                        Toast.makeText(MainActivity.this, " Bun venit " + numeUtilizator(), Toast.LENGTH_LONG).show();
                        Intent logareUtilizator = new Intent(MainActivity.this, SelectieInitialaActivity.class);
                        String idUtilizator = id_Utilizator();
                        logareUtilizator.putExtra("UTILIZATOR", idUtilizator);
                        int id_pct_lucru = id_P_Lucru();
                        logareUtilizator.putExtra("ID_P_LUCRU", id_pct_lucru);
                        startActivity(logareUtilizator);

                    } else {
                        Toast.makeText(MainActivity.this, " Utilizator neidentificat", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, " Nu ai selectat nici un punct de lucru", Toast.LENGTH_SHORT).show();
                }
            }

            ;


        });
        PopulareAutocomplete();
    }

    public int id_P_Lucru() {
        SQLiteDatabase db = myDb.getReadableDatabase();
        int rezultatID = LogicaVerificari.getPunctLucru(db, (PunctDeLucru.getText().toString()));
        return rezultatID;
    }


    public String numeUtilizator() {
        SQLiteDatabase db = myDb.getReadableDatabase();
        String result = "";

        String queryUtilizator = ("select " + Constructor.TabelaUtilizatorPin.COL_NUME + " from " + Constructor.TabelaUtilizatorPin.NUME_TABEL +
                " where " + Constructor.TabelaUtilizatorPin.COL_PIN + " = " + parola.getText().toString().trim());
        Cursor cursor = db.rawQuery(queryUtilizator, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(Constructor.TabelaUtilizatorPin.COL_NUME));
        }
        cursor.close();
        return result;
    }

    public String id_Utilizator() {
        SQLiteDatabase db = myDb.getReadableDatabase();
        String result = "";

        String queryUtilizator = ("select " + Constructor.TabelaUtilizatorPin.COL_ID + " from " + Constructor.TabelaUtilizatorPin.NUME_TABEL +
                " where " + Constructor.TabelaUtilizatorPin.COL_PIN + " = " + parola.getText().toString().trim());
        Cursor cursor = db.rawQuery(queryUtilizator, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(Constructor.TabelaUtilizatorPin.COL_ID));
        }
        cursor.close();
        return result;

    }

    public void PopulareAutocomplete() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();
        final String[] Expeditor_Destinatar = LogicaVerificari.getPlucru(db);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Expeditor_Destinatar);
        PunctDeLucru.setAdapter(adapter);

    }
}