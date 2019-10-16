package ro.bluebit.UTILITARE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ro.bluebit.Database.Constructor;

public class LogicaVerificari {

    public LogicaVerificari() {
    }



    public static boolean verifCorectitudineCodBare(int[] cod) {


        int[] a = new int[13];
        a[0] = cod[0] ;
        a[1] = cod[1];
        a[2] = cod[2] ;
        a[3] = cod[3];
        a[4] = cod[4];
        a[5] = cod[5];
        a[6] = cod[6];
        a[7] = cod[7];
        a[8] = cod[8];
        a[9] = cod[9];
        a[10] = cod[10];
        a[11] = cod[11];
        a[12] = cod[12];
        a[13] = cod[13];
        double suma = 3*( a[1]+a[3]+a[5]+a[7]+a[9]+a[11] ) + (a[0] +a[2] + a[4] +a[6] +a[8] +a[10]+  a[12]);

        double rezultat = Math.round(suma/10.0)*10;
        double diferenta = rezultat-suma;
        // verificare

        if (diferenta == a[13]) {

            return true;

        }else return false;
    }

    //Verificare cod de bare in plaja
    public  static String SQL_QUERY_OBTINE_VALIDARE_PLAJA_CODURI (int CodBare){
        return " SELECT "+ Constructor.Tabela_Plaja_Cod.COL_ID_LOT +" from " + Constructor.Tabela_Plaja_Cod.NUME_TABEL +
                " where " + CodBare + " between " + Constructor.Tabela_Plaja_Cod.COL_MINIM+ " and " + Constructor.Tabela_Plaja_Cod.COL_MAXIM;

    };

}

