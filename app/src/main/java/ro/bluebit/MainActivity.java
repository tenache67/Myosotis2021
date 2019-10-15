package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.SelectieInitialaActivity;

public class MainActivity extends AppCompatActivity {
    Button acceseaza;
    EditText parola;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Logare in aplicatie");

        parola = findViewById(R.id.editTextPIN);
        acceseaza = findViewById(R.id.button_logare);
        acceseaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 //       String pin=parola.getText().toString().trim();
                 Boolean res = myDb.verificPin(parola.getText().toString());

            if (res == true) {
                    Toast.makeText(MainActivity.this, " Bun venit "+ numeUtilizator(), Toast.LENGTH_LONG).show();
                    Intent selectieTransare = new Intent(MainActivity.this, SelectieInitialaActivity.class);
                    startActivity(selectieTransare);
                }
            else{
                Toast.makeText(MainActivity.this, " Utilizator neidentificat", Toast.LENGTH_SHORT).show();
            }


        }
    });
}
    public String numeUtilizator(){
        SQLiteDatabase db = myDb.getReadableDatabase();
        String result ="";

        String queryUtilizator =( "select "+ Constructor.TabelaUtilizatorPin.COL_NUME + " from " + Constructor.TabelaUtilizatorPin.NUME_TABEL +
                " where " + Constructor.TabelaUtilizatorPin.COL_PIN +" = " + parola.getText().toString().trim() );
        Cursor cursor = db.rawQuery(queryUtilizator, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(Constructor.TabelaUtilizatorPin.COL_NUME));
        }
        cursor.close();
        return result;
    }
}