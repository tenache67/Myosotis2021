package ro.bluebit.UTILITARE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ro.bluebit.Database.Constructor;

import static java.lang.Long.parseLong;

public class LogicaVerificari {

    public LogicaVerificari() {
    }


    // Scoaterea zerourilor din sirul de caractere
    public static String RemoveZero (String sir){
        int i=0;
        while (i<sir.length()-1 && sir.charAt(i)=='0')
            i++;
        StringBuffer sb =new StringBuffer(sir);
        sb.replace(0,i,"");
        return sb.toString();
    }


    // Verificare in plaja de coduri
    public static boolean verificareExistentaInPlajaDeCoduri (SQLiteDatabase db, long codBare){
        Cursor crs = db.rawQuery((" SELECT " + Constructor.Tabela_Plaja_Cod.COL_ID_LOT + " from " + Constructor.Tabela_Plaja_Cod.NUME_TABEL +
                " where " + codBare + " between " + Constructor.Tabela_Plaja_Cod.COL_MINIM + " and " + Constructor.Tabela_Plaja_Cod.COL_MAXIM), null);
        return crs.getCount()>0;
    }

    //Verificare in Antet Trimiteri
    public static boolean verificareExistentaInAntetTrimiteri (SQLiteDatabase db, long codBare) {
        Cursor crs1= db.rawQuery((" select "+ Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI+ " from "+
                Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+
                " where "+ codBare +" = " + Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE),null);
        return crs1.getCount()>0;
    }


//Verificare longs de bare in plaja

    public static String SQL_QUERY_OBTINE_VALIDARE_PLAJA_longsURI(long longsBare) {
        return " SELECT " + Constructor.Tabela_Plaja_Cod.COL_ID_LOT + " from " + Constructor.Tabela_Plaja_Cod.NUME_TABEL +
                " where " + longsBare + " between " + Constructor.Tabela_Plaja_Cod.COL_MINIM + " and " + Constructor.Tabela_Plaja_Cod.COL_MAXIM;

    }
    public static String[] getPlucru(SQLiteDatabase db) {
        String selectQuery = Constructor.SQL_QUERY_OBTI_PLUCRU;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String[] retPlucru = new String[cursor.getCount()];
        if (cursor.moveToFirst()) {
            for(int i=0;i<cursor.getCount();i++) {
                retPlucru[i]=cursor.getString(cursor.getColumnIndexOrThrow(Constructor.Tabela_P_Lucru.COL_DENUMIRE));
                cursor.moveToNext();
            }
        }
        return retPlucru;

    }
}
//    public static int[] getCodInt(SQLiteDatabase db) {
//        String selectQuery = Constructor.SQL_QUERY_OBTI_COD_INT;
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        int[] retCodInt = new int[cursor.getCount()];
//        if (cursor.moveToFirst()) {
//            for (int i = 0; i < cursor.getCount(); i++) {
//                //cursor.getInt(0)
//                retCodInt[i] = cursor.getInt(0);
//                cursor.moveToNext();
//
//            }
////            do {
////                retCodInt[cursor.getCount()]= cursor.getInt(cursor.getColumnIndex("cod_int"));
////            }
////            while (cursor.moveToNext());
//        }
//        return retCodInt;
