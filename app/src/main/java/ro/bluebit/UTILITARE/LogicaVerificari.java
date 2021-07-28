package ro.bluebit.UTILITARE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.google.gson.internal.LinkedTreeMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import ro.bluebit.BazaAppCompat;
import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.Diverse.Siruri;
import ro.bluebit.SincroDate;

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
        if (cursor.getCount()>0) {
            return cursor.getInt(cursor.getColumnIndexOrThrow(Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI));
        } else {
            return 0;
        }


    }

    public static int getId_Antet_Trimiteri_alt(SQLiteDatabase db, String codBare) {
        String selecteazId = (" select " + Constructor.Tabela_Antet_Trimiteri_Alt.COL_ID_ANTET_TRIMITERI + " from " +
                Constructor.Tabela_Antet_Trimiteri_Alt.NUME_TABEL +
                " where '" + codBare + "' = " + Constructor.Tabela_Antet_Trimiteri_Alt.COL_COD_BARE);
        Cursor cursor = db.rawQuery(selecteazId, null);
        cursor.moveToFirst();

        if (cursor.getCount()>0) {
            return cursor.getInt(cursor.getColumnIndexOrThrow(Constructor.Tabela_Antet_Trimiteri_Alt.COL_ID_ANTET_TRIMITERI));
        } else {
            return 0;
        }
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

        String idtipstring = (("select " + Constructor.Tabela_Tipuri.COL_ID_TIPURI + " from " +
                Constructor.Tabela_Tipuri.NUME_TABEL + " where '" + id_expeditor_destinatar + "'=" + Constructor.Tabela_Tipuri.COL_DENUMIRE));
        Cursor crs = db.rawQuery(idtipstring, null);
        crs.moveToFirst();
        return crs.getInt(crs.getColumnIndexOrThrow(Constructor.Tabela_Tipuri.COL_ID_TIPURI));
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

                "tat" + "." + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + ", " +

                "tid" + "." + Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI +

                //           Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+"."+"TAT"+
                " from " +
                Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + " as " + "tat" +
                " inner join " +
                Constructor.Tabela_Incarc_Descarc.NUME_TABEL + " as " + "tid" +
                " on " +
                "tat" + "." + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + "=" + "tid" + "." + Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI +
                " where " +
                "tat" + "." + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + "='" + codBare +
                "' and " +
                "tid" + "." + Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI + "='" + "3'");

        Cursor cursor = db.rawQuery(selecteazId, null);
        cursor.moveToFirst();

        return cursor.getCount() > 0;
    }

    public static boolean getExistentaDesc(SQLiteDatabase db, String codBare) {
        String selecteazId = (" select " +

                "tat" + "." + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + ", " +

                "tid" + "." + Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI +

                //           Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+"."+"TAT"+
                " from " +
                Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + " as " + "tat" +
                " inner join " +
                Constructor.Tabela_Incarc_Descarc.NUME_TABEL + " as " + "tid" +
                " on " +
                "tat" + "." + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + "=" + "tid" + "." + Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI +
                " where " +
                "tat" + "." + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + "='" + codBare +
                "' and " +
                "tid" + "." + Constructor.Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI + "='" + "4'");

        Cursor cursor = db.rawQuery(selecteazId, null);
        cursor.moveToFirst();

        return cursor.getCount() > 0;
    }

    // creeaza instructiune sql necesara pt sincronizare
    // la cheia _numetab - numele tabelei
    // se creeaza instructiuni compilate pt tabele temporare
    public static SQLiteStatement getSqlSincroInsertTemp(LinkedTreeMap lRow) {
        String sTabela = lRow.get("_numetab").toString();
        SQLiteStatement sqlcmd;
        switch (sTabela) {
            case Constructor.Tabela_P_Lucru.NUME_TABEL:
                //Constructor.Tabela_P_Lucru.NUME_TABEL;
                String scmd = getSqlInsertDinTabela(sTabela, lRow, false);
                break;

        }
        return null;
    }

    // genereaza instr de creare a unei tabele .
    // daca se trimite lTemp=true atunci se creeaza o tabela temporara ce va fi referita cu temp.<nume tabela>
    public static String getSqlCreeazaTabela(String sTabName, String[][] sStruct) {
        return getSqlCreeazaTabela(sTabName, sStruct, false);
    }

    public static String getSqlCreeazaTabela(String sTabName, String[][] sStruct, boolean lTemp) {
        String sSql = "";

        for (int i = 0; i < sStruct.length; i++) {
            sSql = sSql + (sSql.isEmpty() ? "" : " , ") + sStruct[i][0] + sStruct[i][1];

        }
        sSql = (lTemp ? "CREATE TEMPORARY TABLE IF NOT EXISTS " : "CREATE TABLE IF NOT EXISTS ") + sTabName + " ( " + sSql + " )";

        return sSql;


    }

    // se genereaza sirul care reprezinta un insert pe tabela precizata eventual cu temp. in fata
    // LinkedTreeMap - este obiectul rezultat din parsare pachetului json venit de la server si are perechi : <nume camp> <valoare>
    // intotdeuna valorile sunt reprezentate ca si string
    public static String getSqlInsertDinTabela(String sTabela, LinkedTreeMap tRow, boolean lTemp) {
        return getSqlInsertDinTabela(sTabela, tRow, lTemp, 0);
    }

    public static String getSqlInsertDinTabela(String sTabela, LinkedTreeMap tRow, boolean lTemp, int nParte) {
        // nParte : 0 - tot , 1 - doar lista de coloane, 2 - doar lista de valori
        String sCmd = "INSERT INTO " + (lTemp ? "temp." : "") + sTabela;
        String sCol = "";
        String sVal = "";
        String sCrt;
        String[][] sStr = Constructor.getStrTabela(sTabela);
        for (int i = 0; i < sStr.length; i++) {
            if (tRow.containsKey(sStr[i][0])) {
                sCol = sCol + (sCol.isEmpty() ? "" : ",") + sStr[i][0];

                switch (sStr[i][2]) {
                    case Constructor.Tip.INTREG:
                        sVal = sVal + (sVal.isEmpty() ? "" : ",") + tRow.get(sStr[i][0]);
                        break;
                    case Constructor.Tip.TEXT:
                        sVal = sVal + (sVal.isEmpty() ? "" : ",'") + tRow.get(sStr[i][0]).toString().trim() + "'";
                        break;
                    case Constructor.Tip.DOUBLE:
                        sVal = sVal + (sVal.isEmpty() ? "" : ",") + tRow.get(sStr[i][0]);
                        break;
                    case Constructor.Tip.TIMESTAMP:
                        sVal = sVal + (sVal.isEmpty() ? "" : ",'") + tRow.get(sStr[i][0]).toString().trim() + "'";
                        break;
                    default:
                        sVal = sVal + (sVal.isEmpty() ? "" : ",'") + tRow.get(sStr[i][0]).toString().trim() + "'";
                        break;
                }
            }
        }
        switch (nParte) {
            case 0:
                sCmd = sCmd + " (" + sCol + ") VALUES (" + sVal + ")";
                break;
            case 1:
                sCmd = " (" + sCol + ") ";
                break;
            case 2:
                sCmd = " (" + sVal + ") ";
                break;
            default:
                sCmd = sCmd + " (" + sCol + ") VALUES (" + sVal + ")";
                break;
        }
        return sCmd;
    }

    //determina timestamb pt tabela data
    public static String getLastTimeStamp(SQLiteDatabase db, String sTabela) {
        String sRetur;
        String sCmd = "SELECT " + Constructor.Tabela_Timestamp.COL_TIME + " FROM " + Constructor.Tabela_Timestamp.NUME_TABEL +
                " WHERE " + Constructor.Tabela_Timestamp.COL_TABELA + " = '" + sTabela + "'";
        try {
            Cursor cursor = db.rawQuery(sCmd, null);
            cursor.moveToFirst();
            sRetur = cursor.getString(cursor.getColumnIndexOrThrow(Constructor.Tabela_Timestamp.COL_TIME));
            if (sRetur.isEmpty()) sRetur="0";
        } catch (Exception e) {
            sRetur = "0";
        } finally {

        }
        return sRetur;

    }

    public static Date getDataDinString(String sSir) {
        return getDataDinString(sSir, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getDataDinString(String sSir, String sFormat) {
        SimpleDateFormat format = new SimpleDateFormat(sFormat);
        Date date = new GregorianCalendar(1900, 0, 01).getTime();
        try {
            date = format.parse(sSir);
        } catch (ParseException e) {
//            date=format.parse("1900-01-01 23:00:00");
        }
        return date;
    }

    public static String getStringDinData(Date data) {
        return getStringDinData(data, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getStringDinData(Date data, String sFormat) {
        SimpleDateFormat format = new SimpleDateFormat(sFormat);
        return format.format(data);
    }

    // sincronizare tabele ce compun o incarcare
    public static String sincronizare_incarcare (SQLiteDatabase db) {
        // se extrag cursoarele pt tabele
        String sExport="";
        sExport=sExport+"DROP TEMPORARY TABLE IF EXISTS tmp_"+Constructor.Tabela_Antet_Trimiteri.NUME_TABEL +" ;"+ Siruri.CR+Siruri.LF;
        sExport=sExport+"DROP TEMPORARY TABLE IF EXISTS tmp_"+Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL +" ;"+ Siruri.CR+Siruri.LF;
        sExport=sExport+"DROP TEMPORARY TABLE IF EXISTS tmp_"+Constructor.Tabela_Incarc_Descarc.NUME_TABEL +" ;"+ Siruri.CR+Siruri.LF;


        sExport=sExport+
                "create temporary table tmp_"+Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+
                " SELECT * FROM "+Constructor.Tabela_Antet_Trimiteri.NUME_TABEL+" WHERE 1<>1"+" ;"+ Siruri.CR+Siruri.LF;
        sExport=sExport+
                "create temporary table tmp_"+Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL+
                " SELECT * FROM "+Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL+" WHERE 1<>1"+" ;"+ Siruri.CR+Siruri.LF;
        sExport=sExport+
                "create temporary table tmp_"+Constructor.Tabela_Incarc_Descarc.NUME_TABEL+
                " SELECT * FROM "+Constructor.Tabela_Incarc_Descarc.NUME_TABEL+" WHERE 1<>1"+" ;"+ Siruri.CR+Siruri.LF;
        try {
            Cursor crs = db.rawQuery("SELECT * FROM " + Constructor.Tabela_Antet_Trimiteri_Alt.NUME_TABEL, null);
            sExport =sExport+ getSqlInsertDinCursor("tmp_"+Constructor.Tabela_Antet_Trimiteri.NUME_TABEL,crs)+" ;"+ Siruri.CR+Siruri.LF;
        }catch (Exception e) {
            String sMes = e.getMessage();
        }
        try {
            Cursor crs = db.rawQuery("SELECT * FROM " + Constructor.Tabela_Pozitii_Trimiteri_Alt.NUME_TABEL, null);
            sExport =sExport+ getSqlInsertDinCursor("tmp_"+Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL,crs)+" ;"+ Siruri.CR+Siruri.LF;
        }catch (Exception e) {
            String sMes = e.getMessage();
        }
        try {
            Cursor crs = db.rawQuery("SELECT * FROM " + Constructor.Tabela_Incarc_Descarc_Alt.NUME_TABEL, null);
            sExport =sExport+ getSqlInsertDinCursor("tmp_"+Constructor.Tabela_Incarc_Descarc.NUME_TABEL,crs)+" ;"+ Siruri.CR+Siruri.LF;
        }catch (Exception e) {
            String sMes = e.getMessage();
        }
        sExport=sExport+"CALL salv_transmiteri " +" ;"+ Siruri.CR+Siruri.LF;
        sExport=sExport+"DROP TEMPORARY TABLE tmp_"+Constructor.Tabela_Antet_Trimiteri.NUME_TABEL +" ;"+ Siruri.CR+Siruri.LF;
        sExport=sExport+"DROP TEMPORARY TABLE tmp_"+Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL +" ;"+ Siruri.CR+Siruri.LF;
        sExport=sExport+"DROP TEMPORARY TABLE tmp_"+Constructor.Tabela_Incarc_Descarc.NUME_TABEL +" ;"+ Siruri.CR+Siruri.LF;

        sExport=sExport+ "select 'OK' as rez ; " + Siruri.CR+Siruri.LF ;
        return  sExport;
    }
    // se genereaza

    // se genereaza sir pentru insert de forma (val,val,val ...) , ( ....) ....
    public static String getSqlInsertDinCursor(String sTabela,Cursor crs) {
        String sSir = "INSERT INTO " + sTabela ;
        String sColoane = "";
        crs.moveToFirst();
        int nColumns = crs.getColumnCount();
        for (int i = 0; i < nColumns; i++) {
            String colName = crs.getColumnName(i);
            if (colName != null) {
                sColoane = sColoane + (sColoane.isEmpty() ? "" : ",") + colName;
            }
        }
        String sListaValori = "";
        while (!crs.isAfterLast()) {
            String sLista = "";
            for (int i = 0; i < nColumns; i++) {
                String sVal = "";
                try {
                    switch (crs.getType(i)) {
                        case Cursor.FIELD_TYPE_BLOB:
                            sVal = "'" + crs.getBlob(i).toString() + "'";
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            sVal = String.valueOf(crs.getDouble(i));
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            sVal = String.valueOf(crs.getLong(i));
                            break;
                        case Cursor.FIELD_TYPE_NULL:
                            sVal = "";
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            sVal = "'" + crs.getString(i) + "'";
                            break;
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
                sLista = sLista + (sLista.isEmpty() ? "" : ",")+sVal;
            }
            sListaValori=sListaValori+(sListaValori.isEmpty() ? "" : ",")+"("+sLista+")" ;
            crs.moveToNext();
        }
        if (sListaValori.isEmpty()) {
            return "" ;
        } else {
            return sSir + " ( " + sColoane + " )" + " VALUES " + sListaValori;
        }
    }

    public static void executaSincro(String sPhp, String sQuery, String sScope, BazaAppCompat activity) {
        SincroDate sincro =  new SincroDate(activity);
        try {
            sincro.execute(sPhp, sQuery, sScope);
        } catch ( Exception e ) {
            String sMes=e.getMessage();
        }
        //      ("test_multiquery.php",sQuery,"SINCRONIZARE_TRIMITERI");

    }
    public static void executaSincroNomenc (BazaAppCompat activity) {
        // sincronizare date de nomenclator
        String sQuery="";
        DatabaseHelper myDb = new DatabaseHelper(activity);
        String sScop="SINCRONIZARE_RECEPTIE";
        if (!activity.existaCerere(sScop)) {
            // tabela_p_lucru
            String sLastTime = LogicaVerificari.getLastTimeStamp
                    (myDb.getReadableDatabase(), Constructor.Tabela_P_Lucru.NUME_TABEL);
            sQuery = "CALL get_new('" + Constructor.Tabela_P_Lucru.NUME_TABEL + "','" + sLastTime + "')";
            executaSincro("test_multiquery.php", sQuery, sScop,activity);
            // TabelaUtilizatoriPin
            sLastTime = LogicaVerificari.getLastTimeStamp
                    (myDb.getReadableDatabase(), Constructor.TabelaUtilizatorPin.NUME_TABEL);
            sQuery = "CALL get_new('" + Constructor.TabelaUtilizatorPin.NUME_TABEL + "','" + sLastTime + "')";
            executaSincro("test_multiquery.php", sQuery, sScop,activity);
            // plaje de cod
            sLastTime = LogicaVerificari.getLastTimeStamp
                    (myDb.getReadableDatabase(), Constructor.Tabela_Plaja_Cod.NUME_TABEL);
            sQuery = "CALL get_new('" + Constructor.Tabela_Plaja_Cod.NUME_TABEL + "','" + sLastTime + "')";
            executaSincro("test_multiquery.php", sQuery, sScop,activity);
            // tabela tipuri
            sLastTime = LogicaVerificari.getLastTimeStamp
                    (myDb.getReadableDatabase(), Constructor.Tabela_Tipuri.NUME_TABEL);
            sQuery = "CALL get_new('" + Constructor.Tabela_Tipuri.NUME_TABEL + "','" + sLastTime + "')";
            executaSincro("test_multiquery.php", sQuery, sScop,activity);
        }

    }
    // test_multiquery.php are rolul de a prelua comanda sql de a o trimite la server si de a intoarce rezultatul
    // pentru aducere date din server se foloseste apelul get_new pe baza de timestamp in executaSincroNomenc
    // prentru trimiterea datelor catre server se formeaza query in LogicaVerificari.sincronizare_incarcare si
    // apoi se trimite cu test_multiquery prin executaSincroTrimiteri . TRIMITEREA SE FAce din tabelele _alt care apoi se sterg
    //
    // aici se fac toate sincronizarile necesare
    public static void executaSincroTrimiteri (BazaAppCompat activity) {
        // sincronizare incarcari descarcari
        DatabaseHelper myDb = new DatabaseHelper(activity);
        String sQuery = LogicaVerificari.sincronizare_incarcare(myDb.getReadableDatabase());
        executaSincro("test_multiquery.php", sQuery, "SINCRONIZARE_TRIMITERI",activity);

    }
    // receptioneaza trimiteri si miscari noi
    public static void executaSincroRecTrimiteri(BazaAppCompat activity) {
        String sQuery="";
        DatabaseHelper myDb = new DatabaseHelper(activity);
        String sScop="SINCRONIZARE_RECEPTIE";
        if (!activity.existaCerere(sScop)) {
            String sLastTime = LogicaVerificari.getLastTimeStamp
                    (myDb.getReadableDatabase(), Constructor.Tabela_Antet_Trimiteri.NUME_TABEL);
            sQuery = "CALL get_new('" + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + "','" + sLastTime + "')";
            executaSincro("test_multiquery.php", sQuery, sScop,activity);
            sLastTime = LogicaVerificari.getLastTimeStamp
                    (myDb.getReadableDatabase(), Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL);
            sQuery = "CALL get_new('" + Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL + "','" + sLastTime + "')";
            executaSincro("test_multiquery.php", sQuery, sScop,activity);
            sLastTime = LogicaVerificari.getLastTimeStamp
                    (myDb.getReadableDatabase(), Constructor.Tabela_Incarc_Descarc.NUME_TABEL);
            sQuery = "CALL get_new('" + Constructor.Tabela_Incarc_Descarc.NUME_TABEL + "','" + sLastTime + "')";
            executaSincro("test_multiquery.php", sQuery, sScop,activity);
        }
    }

}
