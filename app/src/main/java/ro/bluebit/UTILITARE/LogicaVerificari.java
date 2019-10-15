package ro.bluebit.UTILITARE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LogicaVerificari {

    public LogicaVerificari() {
    }



    public static boolean verifCorectitudineCodBare(int[] cod) {


        int[] a = new int[13];
        a[0] = cod[0] * 3;
        a[1] = cod[1];
        a[2] = cod[2] * 3;
        a[3] = cod[3];
        a[4] = cod[4] * 3;
        a[5] = cod[5];
        a[6] = cod[6] * 3;
        a[7] = cod[7];
        a[8] = cod[8] * 3;
        a[9] = cod[9];
        a[10] = cod[10] * 3;
        a[11] = cod[11];
        a[12] = cod[12] * 3;
        a[13] = cod[13];
        int suma = a[0] + a[1] + a[2] + a[3] + a[4] + a[5] + a[6] + a[7] + a[8] + a[9] + a[10] + a[11] + a[12];
        int rezultat = (10 - (suma % 10)) % 10;
        // verificare

        if (rezultat == a[13]) {

            return true;

        }else return false;
    }
}

