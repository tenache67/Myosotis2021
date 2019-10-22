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

}
