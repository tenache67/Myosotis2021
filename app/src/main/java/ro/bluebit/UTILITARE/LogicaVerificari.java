package ro.bluebit.UTILITARE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ro.bluebit.Database.Constructor;

public class LogicaVerificari {

    public LogicaVerificari() {
    }


    // Scoaterea zerourilor din sirul de caractere
    public static String RemoveZero(String sir) {
        int i = 0;
        while (i < sir.length() - 1 && sir.charAt(i) == '0')
            i++;
        StringBuffer sb = new StringBuffer(sir);
        sb.replace(0, i, "");
        return sb.toString();
    }


    // Verificare in plaja de coduri
    public static boolean verificareExistentaInPlajaDeCoduri(SQLiteDatabase db, long codBare) {
        Cursor crs = db.rawQuery((" SELECT " + Constructor.Tabela_Plaja_Cod.COL_ID_LOT + " from " + Constructor.Tabela_Plaja_Cod.NUME_TABEL +
                " where " + codBare + " between " + Constructor.Tabela_Plaja_Cod.COL_MINIM + " and " + Constructor.Tabela_Plaja_Cod.COL_MAXIM), null);
        return crs.getCount() > 0;
    }

    //Verificare in Antet Trimiteri
    public static boolean verificareExistentaInAntetTrimiteri(SQLiteDatabase db, String codBare) {
        Cursor crs1 = db.rawQuery((" select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + " from " +
                Constructor.Tabela_Antet_Trimiteri.NUME_TABEL +
                " where '" + codBare + "' = " + Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE), null);
        return crs1.getCount() > 0;
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
            for (int i = 0; i < cursor.getCount(); i++) {
                retPlucru[i] = cursor.getString(cursor.getColumnIndexOrThrow(Constructor.Tabela_P_Lucru.COL_DENUMIRE));
                cursor.moveToNext();
            }
        }
        return retPlucru;

    }

    //obtinere id din tabela ANTET TRIMITERI
    public static int getId_Antet_Trimiteri(SQLiteDatabase db, String codBare) {
        String selecteazId = (" select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + " from " +
                Constructor.Tabela_Antet_Trimiteri.NUME_TABEL +
                " where '" + codBare + "' = " + Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE);
        Cursor cursor = db.rawQuery(selecteazId, null);
        cursor.moveToFirst();

        return cursor.getInt(cursor.getColumnIndexOrThrow(Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI));
    }

    public static int getPunctLucru(SQLiteDatabase db, String denplucru) {

        String denumirestring = (("select " + Constructor.Tabela_P_Lucru.COL_ID + " from " +
                Constructor.Tabela_P_Lucru.NUME_TABEL + " where '" + denplucru + "'=" + Constructor.Tabela_P_Lucru.COL_DENUMIRE));
        Cursor crs = db.rawQuery(denumirestring, null);
        crs.moveToFirst();
        return crs.getInt(crs.getColumnIndexOrThrow(Constructor.Tabela_P_Lucru.COL_ID));
    }
    public static int getDenumirePunctLucru(SQLiteDatabase db, int id_lucru) {

        String denumirestring = (("select " + Constructor.Tabela_P_Lucru.COL_DENUMIRE + " from " +
                Constructor.Tabela_P_Lucru.NUME_TABEL + " where '" + id_lucru + "'=" + Constructor.Tabela_P_Lucru.COL_ID));
        Cursor crs = db.rawQuery(denumirestring, null);
        crs.moveToFirst();
        return crs.getInt(crs.getColumnIndexOrThrow(Constructor.Tabela_P_Lucru.COL_DENUMIRE));
    }

    public static int getExpDest(SQLiteDatabase db, String denplucru) {

        String denumirestring = (("select " + Constructor.Tabela_P_Lucru.COL_ID + " from " +
                Constructor.Tabela_P_Lucru.NUME_TABEL + " where '" + denplucru + "'=" + Constructor.Tabela_P_Lucru.COL_DENUMIRE));
        Cursor crs = db.rawQuery(denumirestring, null);
        crs.moveToFirst();
        return crs.getInt(crs.getColumnIndexOrThrow(Constructor.Tabela_P_Lucru.COL_ID));
    }

    public static int getIdTip(SQLiteDatabase db, String id_expeditor_destinatar) {

        String idtipstring = (("select " + Constructor.Tabela_Tipuri.COL_ID_TIP + " from " +
                Constructor.Tabela_Tipuri.NUME_TABEL + " where '" + id_expeditor_destinatar + "'=" + Constructor.Tabela_Tipuri.COL_DENUMIRE));
        Cursor crs = db.rawQuery(idtipstring, null);
        crs.moveToFirst();
        return crs.getInt(crs.getColumnIndexOrThrow(Constructor.Tabela_Tipuri.COL_ID_TIP));
    }
//    public String(){
//        return  String("Select "+ Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI+ "from "+ Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+ "as at" +
//                " inner join "+ Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL+  " as pt on"+ "at.id_antet_trimiteri"+ "="+ "pt.id_antet_trimiteri"+
//                " inner join "+Constructor.Tabela_P_Lucru.NUME_TABEL+ "as pl on pt.id_p_lucru=pl.id_p_lucru where pl.id_p_lucru"+ "="+ "14");
//
//    }
    //id_antet_trimiteri from tabela_antet_trimiteri as at inner join tabela_pozitii_trimiteri as pt on at.id_antet_trimiteri
    // = pt.id_antet_trimiteri inner join tabela_P_Lucru as pl on pt.id_p_lucru=pl.id_p_lucru where pl.id_p_lucru = 14

    public static boolean getExistentaInc(SQLiteDatabase db, String codBare) {
        String selecteazId = (" select " +

                "tat"+"."+Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI+", "+

                "tid"+"."+Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI+

     //           Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+"."+"TAT"+
                " from "+
                Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+ " as "+ "tat"+
                " inner join " +
                Constructor.Tabela_Incarc_Descarc.NUME_TABEL+ " as "+"tid"+
                " on " +
               "tat"+"."+Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI+"="+"tid"+"."+Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI +
                " where " +
                "tat"+"."+Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI+"='"+codBare+
                "' and " +
                "tid"+"."+Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI+"='"+"3'");

        Cursor cursor = db.rawQuery(selecteazId, null);
        cursor.moveToFirst();

        return cursor.getCount() > 0;
    }

    public static boolean getExistentaDesc(SQLiteDatabase db, String codBare) {
        String selecteazId = (" select " +

                "tat"+"."+Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI+", "+

                "tid"+"."+Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI+

                //           Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+"."+"TAT"+
                " from "+
                Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+ " as "+ "tat"+
                " inner join " +
                Constructor.Tabela_Incarc_Descarc.NUME_TABEL+ " as "+"tid"+
                " on " +
                "tat"+"."+Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI+"="+"tid"+"."+Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI +
                " where " +
                "tat"+"."+Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI+"='"+codBare+
                "' and " +
                "tid"+"."+Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI+"='"+"4'");

        Cursor cursor = db.rawQuery(selecteazId, null);
        cursor.moveToFirst();

        return cursor.getCount() > 0;
    }


}

