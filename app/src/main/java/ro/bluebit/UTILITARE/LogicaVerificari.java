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


    public static boolean verifCorectitudineBare(String numbers) {
//        String[] myArray = numbers.split("");
//        for(int i=0; i<myArray.length; i++)
//        { myArray[i] + = myArray[1]; }

        String[] parts = numbers.split(" ");
        long [] n1 = new long[parts.length];
        for(int n = 0; n < parts.length; n++) {
            n1[n] = parseLong(parts[n]);
        }
        
//use myArray, it's an array of numbers
        long [] a = new long[13];
        a[1] = n1[1];
        a[2] = n1[2];
        a[3] = n1[3];
        a[4] = n1[4];
        a[5] = n1[5];
        a[6] = n1[6];
        a[7] = n1[7];
        a[8] = n1[8];
        a[9] = n1[9];
        a[10] = n1[10];
        a[11] = n1[11];
        a[12] = n1[12];
        a[13] = n1[13];

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

