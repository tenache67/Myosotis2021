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
        Destinatar = findViewById(R.id.Info_destinatar_id);
        Prioritate = findViewById(R.id.Info_prioritate_id);
        Condspec = findViewById(R.id.Info_conditii_id);
        selectieexp();
    }
    public void selectieexp() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();
        Bundle extras = getIntent().getExtras();

        String codbare = extras.getString("codbare");

        String selectiestring = "Select " + "at" + "." + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + " from " + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + " as " + " at " +
                " inner join " + Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL + " as " + "pt" + " on " + "at" + "." + "id_antet_trimiteri" + " = " + " pt" + "." + "id_antet_trimiteri " +
                " inner join " + Constructor.Tabela_P_Lucru.NUME_TABEL + " as " + " pl " + " on " + "pt" + "." + "id_p_lucru=pl" + "." + "id_p_lucru" + " where " + " pl" + "." + "id_p_lucru " + " = " + 12;
        //punctul de lucru trebuie pus automat
//        String selectiestring = "Select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI+ " from "+ Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+ " where "+
//                Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE+ " = "+ "0000000000021";
        String selectieidantet = "Select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + " from " + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + " where " + Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE +
                "=" + "'" + codbare + "'"; ///////Selectie ID ANTET


        Cursor crs = db.rawQuery(selectieidantet, null);
        //int nrcol = crs.getColumnIndex(Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI);
        //if (nrcol!=1) {
        //Toast.makeText(this, "Codul nu exista", Toast.LENGTH_SHORT).show();
        //} else {
        crs.moveToFirst();

        //Expeditor.setText(crs.getInt(crs.getColumnIndexOrThrow(Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI)));
        int id_antet_preluat = crs.getInt(crs.getColumnIndexOrThrow(Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI));

        crs.close();
        //////////////Selectie id punct lucru
        String selectIdPunctLucruExp = "Select " + Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU + " from " + Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL + " where " +
                Constructor.Tabela_Pozitii_Trimiteri.COL_ID_ANTET_TRIMITERI + "=" + id_antet_preluat + " and " + Constructor.Tabela_Pozitii_Trimiteri.COL_ID_TIP + "=" + 1;
        Cursor crs2 = db.rawQuery(selectIdPunctLucruExp, null);
        crs2.moveToFirst();
        int id_plucruexp_preluat = crs2.getInt(crs2.getColumnIndexOrThrow(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU));
        crs2.close();

        /////////Selectie nume punct lucru
        String selectNumePunctLucruExp = "Select " + Constructor.Tabela_P_Lucru.COL_DENUMIRE + " from " + Constructor.Tabela_P_Lucru.NUME_TABEL + " where " + Constructor.Tabela_P_Lucru.COL_ID + "=" + id_plucruexp_preluat;
        Cursor crs3 = db.rawQuery(selectNumePunctLucruExp, null);
        crs3.moveToFirst();
        String DenumirePunctLucruExp = crs3.getString(crs3.getColumnIndex(Constructor.Tabela_P_Lucru.COL_DENUMIRE));
        Expeditor.setText(DenumirePunctLucruExp);

        String selectIdPunctLucruDest = "Select " + Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU + " from " + Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL + " where " +
                Constructor.Tabela_Pozitii_Trimiteri.COL_ID_ANTET_TRIMITERI + "=" + id_antet_preluat + " and " + Constructor.Tabela_Pozitii_Trimiteri.COL_ID_TIP + "=" + 2;
        crs3.close();
        /////////Selectie id destinatar
        Cursor crs4 = db.rawQuery(selectIdPunctLucruDest, null);
        crs4.moveToFirst();
        int id_plucrudest_preluat = crs4.getInt(crs4.getColumnIndexOrThrow(Constructor.Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU));
        crs4.close();

        ///Selectie nume destinatar
        String selectNumePunctLucruDest = "Select " + Constructor.Tabela_P_Lucru.COL_DENUMIRE + " from " + Constructor.Tabela_P_Lucru.NUME_TABEL + " where " + Constructor.Tabela_P_Lucru.COL_ID + "=" + id_plucrudest_preluat;
        Cursor crs5 = db.rawQuery(selectNumePunctLucruDest, null);
        crs5.moveToFirst();
        String DenumirePunctLucruDest = crs5.getString(crs5.getColumnIndex(Constructor.Tabela_P_Lucru.COL_DENUMIRE));
        Destinatar.setText(DenumirePunctLucruDest);
        crs5.close();


        //Selectie id prioritate
        String selectieidprioritate = "Select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_PRIORITATE + " from " + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + " where " + Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE +
                "=" + "'" + codbare + "'"; ///////Selectie ID ANTET


        Cursor crs6 = db.rawQuery(selectieidprioritate, null);

        crs6.moveToFirst();

        int id_prioritate = crs6.getInt(crs6.getColumnIndexOrThrow(Constructor.Tabela_Antet_Trimiteri.COL_ID_PRIORITATE));
        crs6.close();

        String selectDenPrioritate = "Select "+ Constructor.Tabela_Tipuri.COL_DENUMIRE+ " from "+ Constructor.Tabela_Tipuri.NUME_TABEL+ " where " + Constructor.Tabela_Tipuri.COL_ID_TIPURI+"="+id_prioritate+ " limit 1";
        Cursor crs7 = db.rawQuery(selectDenPrioritate, null);
        crs7.moveToFirst();
        String DenumirePrioritate = crs7.getString(crs7.getColumnIndex(Constructor.Tabela_Tipuri.COL_DENUMIRE));
        Prioritate.setText(DenumirePrioritate);
        crs7.close();

        String selectieidconditii = "Select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_CONDITII + " from " + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + " where " + Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE +
                "=" + "'" + codbare + "'"; ///////Selectie ID ANTET


        Cursor crs8 = db.rawQuery(selectieidconditii, null);

        crs8.moveToFirst();

        int id_conditii = crs8.getInt(crs8.getColumnIndexOrThrow(Constructor.Tabela_Antet_Trimiteri.COL_ID_CONDITII));
        crs8.close();


        ////////denumire conditii
        String selectDenConditii = "Select "+ Constructor.Tabela_Tipuri.COL_DENUMIRE+ " from "+ Constructor.Tabela_Tipuri.NUME_TABEL+ " where " + Constructor.Tabela_Tipuri.COL_ID_TIPURI+"="+id_conditii+ " limit 1";
        Cursor crs9 = db.rawQuery(selectDenConditii, null);
        crs9.moveToFirst();
        String DenumireConditii = crs9.getString(crs9.getColumnIndex(Constructor.Tabela_Tipuri.COL_DENUMIRE));
        Condspec.setText(DenumireConditii);
        crs9.close();

    }
}

