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
import android.widget.Spinner;
import android.widget.Toast;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.LogicaVerificari;
import ro.bluebit.UTILITARE.SelectieInitialaActivity;

import static ro.bluebit.R.layout.support_simple_spinner_dropdown_item;

public class MainActivity extends AppCompatActivity {
    Button acceseaza;
    EditText parola;
    DatabaseHelper myDb;
    AutoCompleteTextView punctLucru;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
// crearea datelor de proba - am pus aici ca sa ruleze de fiecate data la pornire
        myDb.dateProba();
        punctLucru = findViewById(R.id.AC_PunctLucru);
        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Logare in aplicatie");
        acceseaza = findViewById(R.id.button_logare);
        acceseaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       String pin=parola.getText().toString().trim();
                Boolean res = myDb.verificPin(parola.getText().toString());

                if (res == true) {
                    Toast.makeText(MainActivity.this, " Bun venit " + numeUtilizator(), Toast.LENGTH_LONG).show();
                    Intent logareUtilizator = new Intent(MainActivity.this, SelectieInitialaActivity.class);
                    String idUtilizator = id_Utilizator();
                    logareUtilizator.putExtra("UTILIZATOR", idUtilizator);
                    int iDpuncTlucrU =id_Punct_lucru();
                    logareUtilizator.putExtra("ID_PUNCT_LUCRU", iDpuncTlucrU);
                    startActivity(logareUtilizator);
                } else {
                    Toast.makeText(MainActivity.this, " Utilizator neidentificat", Toast.LENGTH_SHORT).show();
                }


            }
        });

//afisare autocomplete si obtinerea id punct de lucru pentru intent
        PopulareAutocomplete();





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

    public int id_Punct_lucru(){
        SQLiteDatabase db=myDb.getReadableDatabase();

        String  denumirestring =(("select "+Constructor.Tabela_P_Lucru.COL_ID+" from "+
                Constructor.Tabela_P_Lucru.NUME_TABEL+ " where '"+ punctLucru+ "'="+ Constructor.Tabela_P_Lucru.COL_DENUMIRE));
        Cursor crs = db.rawQuery(denumirestring,null);
        crs.moveToFirst();
       int id_id=crs.getColumnIndexOrThrow(Constructor.Tabela_P_Lucru.COL_ID);
        return id_id;
    }


    public void PopulareAutocomplete() {
        SQLiteDatabase db = myDb.getReadableDatabase();
        final String[] Punct_Lucru = LogicaVerificari.getPlucru(db);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, support_simple_spinner_dropdown_item, Punct_Lucru);
        punctLucru.setAdapter(adapter);


    }
}