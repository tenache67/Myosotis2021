package ro.bluebit.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static int VERSIUNE_BAZA_DE_DATE =16;

    public DatabaseHelper(@Nullable Context context) {
        super(context, Constructor.DATABASE_NAME, null, VERSIUNE_BAZA_DE_DATE);
        Log.e("Baza de date", "Baza de date " + getDatabaseName() + " a fost creata");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constructor.SQL_CREAZA_TABEL_UTILIZATORI);
        Log.e("Baza de date", "Tabel creat " );
        db.execSQL(Constructor.SQL_CREAZA_TABEL_ANTET_TRIMITERI);
        Log.e("Baza de date", "Tabel creat " );
        db.execSQL(Constructor.SQL_CREAZA_TABEL_POZITII_TRIMITERI);
        Log.e("Baza de date", "Tabel creat " );
        db.execSQL(Constructor.SQL_CREAZA_TABEL_INCARC_DESCARC);
        Log.e("Baza de date", "Tabel creat " );
        db.execSQL(Constructor.SQL_CREAZA_TABEL_P_LUCRU);
        Log.e("Baza de date", "Tabel creat " );
        db.execSQL(Constructor.SQL_CREAZA_TABEL_PACHETE);
        Log.e("Baza de date", "Tabel creat " );
        db.execSQL(Constructor.SQL_CREAZA_TABEL_PLAJA_COD);
        Log.e("Baza de date", "Tabel creat " );
        db.execSQL(Constructor.SQL_CREAZA_TABEL_RUTE);
        Log.e("Baza de date", "Tabel creat " );
        db.execSQL(Constructor.SQL_CREAZA_TABEL_RUTE_P_LUCRU);
        Log.e("Baza de date", "Tabel creat " );
        db.execSQL(Constructor.SQL_CREAZA_TABEL_TIPURI);
        Log.e("Baza de date", "Tabel creat " );


        String sqlSir="";
        sqlSir="INSERT INTO Tabela_utilizatori (id_utilizator, nume, pin, nivel_acces,id_departament) VALUES   (1, 'Marian', 111111, 1, 1)";
        db.execSQL(sqlSir);
        sqlSir="INSERT INTO Tabela_utilizatori (id_utilizator, nume, pin, nivel_acces,id_departament) VALUES   (2, 'Adrian', 222222, 2, 2)";
        db.execSQL(sqlSir);
        sqlSir="INSERT INTO Tabela_utilizatori (id_utilizator, nume, pin, nivel_acces,id_departament) VALUES   (3, 'Vasile', 333333, 4, 2)";
        db.execSQL(sqlSir);

        sqlSir="INSERT INTO tabela_plaja_cod (id_lot, val_minima,val_maxima ) VALUES   (1,  1111111111111, 9999999999999)";
        db.execSQL(sqlSir);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.TabelaUtilizatorPin.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Incarc_Descarc.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_P_Lucru.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Pachete.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Plaja_Cod.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Rute.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Rute_P_Lucru.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Tipuri.NUME_TABEL);

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
