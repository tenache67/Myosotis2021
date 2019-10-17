package ro.bluebit.UTILITARE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ro.bluebit.Database.Constructor;

import static java.lang.Long.parseLong;

public class LogicaVerificari {

    public LogicaVerificari() {
    }


    public static boolean verifCorectitudinelongsBare(long []longs) {
        long[] a = new long[13];
        a[0] = longs[0];
        a[1] = longs[1];
        a[2] = longs[2];
        a[3] = longs[3];
        a[4] = longs[4];
        a[5] = longs[5];
        a[6] = longs[6];
        a[7] = longs[7];
        a[8] = longs[8];
        a[9] = longs[9];
        a[10] = longs[10];
        a[11] = longs[11];
        a[12] = longs[12];
        a[13] = longs[13];
        double suma = 3 * (a[1] + a[3] + a[5] + a[7] + a[9] + a[11]) + (a[0] + a[2] + a[4] + a[6] + a[8] + a[10] + a[12]);
        double rezultat = Math.round(suma / 10.0) * 10;
        double diferenta = rezultat - suma;
        if (diferenta == a[13]) {

            return true;

        } else return false;
    }

//Verificare longs de bare in plaja

    public static String SQL_QUERY_OBTINE_VALIDARE_PLAJA_longsURI(long longsBare) {
        return " SELECT " + Constructor.Tabela_Plaja_Cod.COL_ID_LOT + " from " + Constructor.Tabela_Plaja_Cod.NUME_TABEL +
                " where " + longsBare + " between " + Constructor.Tabela_Plaja_Cod.COL_MINIM + " and " + Constructor.Tabela_Plaja_Cod.COL_MAXIM;

    }

    ;

// validarea din plaja de longsuri

    public int verificaPlajalongsBare(SQLiteDatabase db, long longsBare) {
        Long longs = longsBare;
        Cursor crs1 = db.rawQuery(LogicaVerificari.SQL_QUERY_OBTINE_VALIDARE_PLAJA_longsURI(longs), null);
        crs1.moveToFirst();
        int b = crs1.getInt(0);
        if (b != 0) {

        }
        return b;
    }


}

