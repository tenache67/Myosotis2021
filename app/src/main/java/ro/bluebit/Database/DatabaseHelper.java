package ro.bluebit.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static int VERSIUNE_BAZA_DE_DATE =11;

    public DatabaseHelper(@Nullable Context context) {
        super(context, Constructor.DATABASE_NAME, null, VERSIUNE_BAZA_DE_DATE);
        Log.e("Baza de date", "Baza de date " + getDatabaseName() + " a fost creata");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constructor.SQL_CREAZA_TABEL_UTILIZATORI);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_ANTET_TRIMITERI);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_POZITII_TRIMITERI);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_INCARC_DESCARC);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_P_LUCRU);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_PACHETE);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_PLAJA_COD);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_RUTE);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_RUTE_P_LUCRU);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_TIPURI);


        Log.e("Baza de date", "Tabela " + Constructor.TabelaUtilizatorPin.NUME_TABEL + " a fost creata");
        String sqlSir="";
        sqlSir="INSERT INTO Tabela_utilizatori (id_utilizator, nume, pin, nivel_acces,id_departament) VALUES   (1, 'Marian', 111111, 1, 1)";
        db.execSQL(sqlSir);
        sqlSir="INSERT INTO Tabela_utilizatori (id_utilizator, nume, pin, nivel_acces,id_departament) VALUES   (2, 'Adrian', 222222, 2, 2)";
        db.execSQL(sqlSir);
        sqlSir="INSERT INTO Tabela_utilizatori (id_utilizator, nume, pin, nivel_acces,id_departament) VALUES   (3, 'Vasile', 333333, 4, 2)";
        db.execSQL(sqlSir);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.TabelaUtilizatorPin.NUME_TABEL);
        onCreate(db);

    }

    public boolean verificPin (String pin){
        String [] columns ={Constructor.TabelaUtilizatorPin.COL_PIN};
        SQLiteDatabase db = getReadableDatabase();
        String selection = ( Constructor.TabelaUtilizatorPin.COL_PIN + "=?" );
        String [] selectionArgs = {pin};
        Cursor cursor = db.query(Constructor.TabelaUtilizatorPin.NUME_TABEL, columns, selection, selectionArgs,null,null,null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count>0)
            return true;
        else
            return false;
    }
}
