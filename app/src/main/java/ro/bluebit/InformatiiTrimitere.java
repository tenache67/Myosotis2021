package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;

public class InformatiiTrimitere extends BazaAppCompat {
    TextView Expeditor,Destinatar,Prioritate,Condspec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informatii_trimitere);
        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Informatii Trimitere ");
        Expeditor = findViewById(R.id.Info_expeditor_id);
        selectieexp();
    }
    public void selectieexp() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();
        String selectiestring = "Select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + " from " + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + " as "+ " at " +
                " inner join " + Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL + " as "+  " pt "+ " on " + "at"+"."+"id_antet_trimiteri " + " = " + " pt"+"."+"id_antet_trimiteri " +
                " inner join " + Constructor.Tabela_P_Lucru.NUME_TABEL + " as "+ " pl "+ " on "+ "pt"+"."+"id_p_lucru=pl"+"."+"id_p_lucru"+ " where "+ " pl"+"."+"id_p_lucru " + " = " +  14;
//        String selectiestring = "Select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI+ " from "+ Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+ " where "+
//                Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE+ " = "+ "0000000000021";

        Cursor crs = db.rawQuery(selectiestring, null);
        crs.moveToFirst();

        Expeditor.setText(crs.getInt(crs.getColumnIndexOrThrow(Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI)));
        //select id_antet_trimiteri from tabela_antet_trimiteri as at inner join
        // tabela_pozitii_trimiteri as pt on at.id_antet_trimiteri = pt.id_antet_trimiteri inner join tabela_P_Lucru as pl on pt.id_p_lucru=pl.id_p_lucru where pl.id_p_lucru = 14


//        String selectiestring = "Select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + " from " + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + " as at " +
//                " inner join " + Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL + " as pt on " + "at.id_antet_trimiteri " + "= " + "pt.id_antet_trimiteri " +
//                " inner join " + Constructor.Tabela_P_Lucru.NUME_TABEL + " as pl on pt.id_p_lucru=pl.id_p_lucru where pl.id_p_lucru" + "=" + "14";
    }
}

