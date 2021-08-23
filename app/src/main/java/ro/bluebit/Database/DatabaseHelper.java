package ro.bluebit.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import ro.bluebit.UTILITARE.LogicaVerificari;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static int VERSIUNE_BAZA_DE_DATE =75;

    public DatabaseHelper(@Nullable Context context) {
        super(context, Constructor.DATABASE_NAME, null, VERSIUNE_BAZA_DE_DATE);
        Log.e("Baza de date", "Baza de date " + getDatabaseName() + " a fost creata");

    }
// trimiterile se stocheaza in tabelele cu _ALT la urma si se trimit catre server prin operatie speciala de sincronizare
    // nu se baga in tabela de timestamp


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constructor.SQL_CREAZA_TABEL_UTILIZATORI);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_ANTET_TRIMITERI);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_POZITII_TRIMITERI);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_INCARC_DESCARC);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_PACHETE);

        db.execSQL(Constructor.SQL_CREAZA_TABEL_ANTET_TRIMITERI_ALT);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_POZITII_TRIMITERI_ALT);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_INCARC_DESCARC_ALT);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_PACHETE_ALT);

        db.execSQL(Constructor.SQL_CREAZA_TABEL_P_LUCRU);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_PLAJA_COD);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_RUTE);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_RUTE_P_LUCRU);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_TIPURI);
        db.execSQL(Constructor.SQL_CREAZA_TABEL_TIMESTAMP);


//        Log.e("Baza de date", "Tabela " + Constructor.TabelaUtilizatorPin.NUME_TABEL + " a fost creata");
//        String sqlSir="";
//        sqlSir="INSERT INTO Tabela_utilizatori (id_utilizator, nume, pin, nivel_acces,id_departament) VALUES   (1, 'Marian', 111111, 1, 1)";
//        db.execSQL(sqlSir);
//        sqlSir="INSERT INTO Tabela_utilizatori (id_utilizator, nume, pin, nivel_acces,id_departament) VALUES   (2, 'Adrian', 222222, 2, 2)";
//        db.execSQL(sqlSir);
//        sqlSir="INSERT INTO Tabela_utilizatori (id_utilizator, nume, pin, nivel_acces,id_departament) VALUES   (3, 'Vasile', 333333, 4, 2)";
//        db.execSQL(sqlSir);
//        sqlSir="INSERT INTO Tabela_plaja_cod (id_lot, val_minima, val_maxima) VALUES   (10, 1000000000000, 9999999999999)";
//        db.execSQL(sqlSir);
        // se creeaza inreg in tabela pt timestamp
        // puncte de lucru
        ContentValues cVal = new ContentValues();
        cVal.put(Constructor.Tabela_Timestamp.COL_TABELA,Constructor.Tabela_P_Lucru.NUME_TABEL);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIME,"0");
        cVal.put(Constructor.Tabela_Timestamp.COL_NUME_PRIMARY,Constructor.Tabela_P_Lucru.COL_ID);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIPSINC,0); // 0 - aduce din server 1 - trimite in server
        try {
            long nId = db.insertOrThrow (Constructor.Tabela_Timestamp.NUME_TABEL, null, cVal);
        } catch (Exception e) {
            String smsg=e.getMessage();
        }
        //utilizatori
        cVal.clear();
        cVal.put(Constructor.Tabela_Timestamp.COL_TABELA,Constructor.TabelaUtilizatorPin.NUME_TABEL);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIME,"0");
        cVal.put(Constructor.Tabela_Timestamp.COL_NUME_PRIMARY,Constructor.TabelaUtilizatorPin.COL_ID);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIPSINC,0); // 0 - aduce din server 1 - trimite in server
        try {
            long nId = db.insertOrThrow (Constructor.Tabela_Timestamp.NUME_TABEL, null, cVal);
        } catch (Exception e) {
            String smsg=e.getMessage();
        }
        // plaje cod
        cVal.clear();
        cVal.put(Constructor.Tabela_Timestamp.COL_TABELA,Constructor.Tabela_Plaja_Cod.NUME_TABEL);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIME,"0");
        cVal.put(Constructor.Tabela_Timestamp.COL_NUME_PRIMARY,Constructor.Tabela_Plaja_Cod.COL_ID_LOT);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIPSINC,0); // 0 - aduce din server 1 - trimite in server
        try {
            long nId = db.insertOrThrow (Constructor.Tabela_Timestamp.NUME_TABEL, null, cVal);
        } catch (Exception e) {
            String smsg=e.getMessage();
        }
        // tabela tipuri
        cVal.clear();
        cVal.put(Constructor.Tabela_Timestamp.COL_TABELA,Constructor.Tabela_Tipuri.NUME_TABEL);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIME,"0");
        cVal.put(Constructor.Tabela_Timestamp.COL_NUME_PRIMARY,Constructor.Tabela_Tipuri.COL_ID_TIPURI);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIPSINC,0); // 0 - aduce din server 1 - trimite in server
        try {
            long nId = db.insertOrThrow (Constructor.Tabela_Timestamp.NUME_TABEL, null, cVal);
        } catch (Exception e) {
            String smsg=e.getMessage();
        }
        // tabela antet trimiteri
        cVal.clear();
        cVal.put(Constructor.Tabela_Timestamp.COL_TABELA,Constructor.Tabela_Antet_Trimiteri.NUME_TABEL);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIME,"0");
        cVal.put(Constructor.Tabela_Timestamp.COL_NUME_PRIMARY,Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIPSINC,1); // 0 - aduce din server 1 - trimite in server
        try {
            long nId = db.insertOrThrow (Constructor.Tabela_Timestamp.NUME_TABEL, null, cVal);
        } catch (Exception e) {
            String smsg=e.getMessage();
        }
        cVal.clear();
        cVal.put(Constructor.Tabela_Timestamp.COL_TABELA,Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIME,"0");
        cVal.put(Constructor.Tabela_Timestamp.COL_NUME_PRIMARY,Constructor.Tabela_Pozitii_Trimiteri.COL_ID_POZITII_TRIMITERI);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIPSINC,1); // 0 - aduce din server 1 - trimite in server
        try {
            long nId = db.insertOrThrow (Constructor.Tabela_Timestamp.NUME_TABEL, null, cVal);
        } catch (Exception e) {
            String smsg=e.getMessage();
        }
        cVal.clear();
        cVal.put(Constructor.Tabela_Timestamp.COL_TABELA,Constructor.Tabela_Incarc_Descarc.NUME_TABEL);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIME,"0");
        cVal.put(Constructor.Tabela_Timestamp.COL_NUME_PRIMARY,Constructor.Tabela_Incarc_Descarc.COL_ID_INCARC_DESCARC);
        cVal.put(Constructor.Tabela_Timestamp.COL_TIPSINC,1); // 0 - aduce din server 1 - trimite in server
        try {
            long nId = db.insertOrThrow (Constructor.Tabela_Timestamp.NUME_TABEL, null, cVal);
        } catch (Exception e) {
            String smsg=e.getMessage();
        }





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.TabelaUtilizatorPin.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_P_Lucru.NUME_TABEL);

        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Pozitii_Trimiteri.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Incarc_Descarc.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Pachete.NUME_TABEL);

        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Antet_Trimiteri_Alt.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Pozitii_Trimiteri_Alt.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Incarc_Descarc_Alt.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Pachete_Alt.NUME_TABEL);

        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Plaja_Cod.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Rute.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Rute_P_Lucru.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Tipuri.NUME_TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + Constructor.Tabela_Timestamp.NUME_TABEL);


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

    // pentru insert o sa folosesc insertWithOnConflict care face insert numai daca valoarea pentru cheia unica
    // data in contentvalue nu mai exista. De ex daca am campul _id in tabela si este cheie unica iar in tabela
    // exista inreg care are _id=6 atunci o incercare de a face insert  cu content in care am pus
    // cVal.put("_id",6 ) cVal.put ( .... alte campuri cu valori ....) va da eroare daca folosesc insert simplu.
    // Daca folosesc insertWithOnConflict atunci , prin ultimul parametru din lista de parametri de apel, pot
    // controla ce sa se intample. Eu am folosit CONFLICT_IGNORE astfel incat daca nu exita inreg cu aceeasi cheie
    // sa se faca insert , daca nu sa se ignore si sa nu dea eroare. Mai exista si alte posibilitati pe care
    // le puteti gasi in documentatie
    // pentru a apela mai eficient inserWithOnConflict ( ma scuteste de a mai pune de fiecare data null si CONFLICT_IGNORE
    // intoarce si o valoare contentvalue goala ca sa nu mai scriu cVal.clear dupa fiecare insert
    public ContentValues insertOnConflictIgnore (SQLiteDatabase db, String sTabela,ContentValues cVal) {
        db.insertWithOnConflict(sTabela,null,cVal,SQLiteDatabase.CONFLICT_IGNORE);
        cVal.clear();
        return cVal;
    }

    // extrage o valoare din tabela de la o coloana pe baza unui filtru ( se creeaza un sir sql si se apeleaza
    // varianta cu instr sql de mai jos)
    public String detValoareDinQuery(SQLiteDatabase db,String sColoana,String sTabela,String sWhere) {
        return detValoareDinQuery(db,sColoana,"SELECT "+sColoana+" FROM "+sTabela+" WHERE "+sWhere);
    }
    // extrage o valoare din tabela de la o coloana pe baza unei instr sql
    public String detValoareDinQuery(SQLiteDatabase db,String sColoana,String sCmd) {
        String sRez;
        try {
            Cursor crs = db.rawQuery(sCmd, null);
            crs.moveToFirst();
            sRez = crs.getString(crs.getColumnIndexOrThrow((sColoana)));
            crs.close();
        } catch (Exception e) {
            sRez = "";
        }
        return sRez;
    }
    // insereaza o inreg in rute pe baza denumirii pct de lucru si a rutei
    public void creaRuta (SQLiteDatabase db, String sPlucru, String sRuta) {
        ContentValues cVal = new ContentValues() ;
        int nIdRuta= Integer.parseInt( detValoareDinQuery(db, Constructor.Tabela_Rute.COL_ID, Constructor.Tabela_Rute.NUME_TABEL,
                Constructor.Tabela_Rute.COL_DENUMIRE+" = "+"'"+sRuta+"'"
        ));
        int nIdPlucru=Integer.parseInt(detValoareDinQuery(db, Constructor.Tabela_P_Lucru.COL_ID, Constructor.Tabela_P_Lucru.NUME_TABEL,
                Constructor.Tabela_P_Lucru.COL_DENUMIRE+" = "+"'"+sPlucru+"'"
        ));


        // idul rutei
        cVal.put(Constructor.Tabela_Rute_P_Lucru.COL_ID_RUTA,nIdRuta);
        // idul pct de lucru
        cVal.put(Constructor.Tabela_Rute_P_Lucru.COL_ID_P_LUCRU,nIdPlucru);
        insertOnConflictIgnore(db, Constructor.Tabela_Rute_P_Lucru.NUME_TABEL,cVal);
    }

    public void dateProba1 () {
        ContentValues cVal = new ContentValues();
        SQLiteDatabase db= this.getWritableDatabase();
        // tabela_utilizatori
        for (int i=1 ; i<=10 ; i++) {
            cVal.put(Constructor.TabelaUtilizatorPin.COL_ID, i);
            cVal.put(Constructor.TabelaUtilizatorPin.COL_NUME, "user" + i);
            cVal.put(Constructor.TabelaUtilizatorPin.COL_PIN,111111+i-1);
            cVal.put(Constructor.TabelaUtilizatorPin.COL_NIV_ACCES,1);
            cVal.put(Constructor.TabelaUtilizatorPin.COL_ID_DEPARTAMENT,Math.ceil(i/3));
            cVal=insertOnConflictIgnore(db,Constructor.TabelaUtilizatorPin.NUME_TABEL,cVal);
        }
        // tabela_P_Lucru
        int i=0;
        int index=1;
        cVal.put(Constructor.Tabela_P_Lucru.COL_ID,++i);
        cVal.put(Constructor.Tabela_P_Lucru.COL_DENUMIRE,"Depozit");
        cVal.put(Constructor.Tabela_P_Lucru.COL_ADRESA,"Depozit");
        cVal=insertOnConflictIgnore(db,Constructor.Tabela_P_Lucru.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_P_Lucru.COL_ID,++i);
        cVal.put(Constructor.Tabela_P_Lucru.COL_DENUMIRE,"Contabilitate");
        cVal.put(Constructor.Tabela_P_Lucru.COL_ADRESA,"Sediu");
        cVal=insertOnConflictIgnore(db,Constructor.Tabela_P_Lucru.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_P_Lucru.COL_ID,++i);
        cVal.put(Constructor.Tabela_P_Lucru.COL_DENUMIRE,"Birou");
        cVal.put(Constructor.Tabela_P_Lucru.COL_ADRESA,"Sediu");
        cVal=insertOnConflictIgnore(db,Constructor.Tabela_P_Lucru.NUME_TABEL,cVal);
        for ( index=1; index<=6; index++ ) {
            cVal.put(Constructor.Tabela_P_Lucru.COL_ID, ++i);
            cVal.put(Constructor.Tabela_P_Lucru.COL_DENUMIRE, "Masina" + index);
            cVal.put(Constructor.Tabela_P_Lucru.COL_ADRESA, "");
            cVal = insertOnConflictIgnore(db, Constructor.Tabela_P_Lucru.NUME_TABEL, cVal);
        }
        for( index=1 ; index<=10 ; index++) {
            cVal.put(Constructor.Tabela_P_Lucru.COL_ID, ++i);
            cVal.put(Constructor.Tabela_P_Lucru.COL_DENUMIRE, "M" + index);
            cVal.put(Constructor.Tabela_P_Lucru.COL_ADRESA, "Farmacie");
            cVal = insertOnConflictIgnore(db, Constructor.Tabela_P_Lucru.NUME_TABEL, cVal);
        }
        // tabela_rute
        for ( i=1; i<=5; i++ ) {
            cVal.put(Constructor.Tabela_Rute.COL_ID,i);
            cVal.put(Constructor.Tabela_Rute.COL_DENUMIRE,"Ruta"+ i);
            cVal=insertOnConflictIgnore(db,Constructor.Tabela_Rute.NUME_TABEL,cVal);
        }
        // tabela_rute_p_lucru
        // impartire pcte de lucru pe rute
        creaRuta(db,"Depozit","Ruta1");
        creaRuta(db,"Contabilitate","Ruta1");
        creaRuta(db,"Birou","Ruta1");

        creaRuta(db,"M1","Ruta2");
        creaRuta(db,"M3","Ruta2");
        creaRuta(db,"M4","Ruta2");

        creaRuta(db,"M2","Ruta3");
        creaRuta(db,"M5","Ruta3");
        creaRuta(db,"M8","Ruta3");
        creaRuta(db,"M9","Ruta3");

        creaRuta(db,"M6","Ruta4");
        creaRuta(db,"M7","Ruta4");
        creaRuta(db,"M10","Ruta4");
        // tabela_plaja_cod
        cVal.put(Constructor.Tabela_Plaja_Cod.COL_ID_LOT,1);
        cVal.put(Constructor.Tabela_Plaja_Cod.COL_MINIM,1);
        cVal.put(Constructor.Tabela_Plaja_Cod.COL_MAXIM,1000);
        insertOnConflictIgnore(db, Constructor.Tabela_Plaja_Cod.NUME_TABEL,cVal);
        cVal.put(Constructor.Tabela_Plaja_Cod.COL_ID_LOT,2);
        cVal.put(Constructor.Tabela_Plaja_Cod.COL_MINIM,1001);
        cVal.put(Constructor.Tabela_Plaja_Cod.COL_MAXIM,2000);
        insertOnConflictIgnore(db, Constructor.Tabela_Plaja_Cod.NUME_TABEL,cVal);

        // tabela_tipuri
        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,1);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Expeditor");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,2);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Destinatar");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,3);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Incarcare");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,4);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Descarcare");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,5);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Obisnuit");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,6);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Bani");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,7);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Acte");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,8);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Nu");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,9);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Frig");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,10);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Sepa");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,11);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Normal");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);

        cVal.put(Constructor.Tabela_Tipuri.COL_ID_TIPURI,12);
        cVal.put(Constructor.Tabela_Tipuri.COL_DENUMIRE,"Prioritar");
        insertOnConflictIgnore(db, Constructor.Tabela_Tipuri.NUME_TABEL,cVal);






        db.close();
    }
    // in sRaspuns sunt toate inregistrarile din tabelele din server pentru care timestamp este mai mare decat timestamp de la sincronizarea anterioara
    // primul camp este adaugat in plus se numeste _numetab si contine numele tabelei din sqlite in care ajunge
    public void sincronizare_receptie (String sRaspuns ) {
        Gson g = new Gson();
        //int sver = db.getVersion();
        List<Object> lLista; //=new ArrayList<HashMap<String, String>>();
        Type tipLista = new TypeToken<List<Object>>() {
        }.getType();

//        List<String[]> lLista;
//        Type tipLista = new TypeToken<List<String[]>>(){}.getType();
        try {
            lLista = g.fromJson(sRaspuns, tipLista);
            if (lLista.size() != 0) {
                SQLiteDatabase db = this.getWritableDatabase();
                try {
                    LinkedTreeMap tRow;
                    // SQLiteStatement sqlCmd =null; //= db.compileStatement(Constructor.SQL_INSERT_TABELA_P_LUCRU) ;
                    String sqlCmd = "";
                    String sTabela;
                    String sqlVal = "";
                    String sCampId = "";
                    String sValId = "";
                    Boolean lGasit = false;
                    int i = 0;
                    sTabela = ((LinkedTreeMap) lLista.get(0)).get("_numetab").toString();
                    // se cauta numele campului pt cheia primara din tabela
                    Cursor crs = db.rawQuery("SELECT " + Constructor.Tabela_Timestamp.COL_NUME_PRIMARY + " as cheie FROM " +
                            Constructor.Tabela_Timestamp.NUME_TABEL + " WHERE " + Constructor.Tabela_Timestamp.COL_TABELA +
                            "='" + sTabela + "'", null);
                    try {
                        crs.moveToFirst();
                        sCampId = crs.getString(0);
                    } catch (Exception e) {
                    }
                    crs.close();
//            sqlCmd = "INSERT INTO "+sTabela+" "+
//                    LogicaVerificari.getSqlInsert(sTabela, (LinkedTreeMap) lLista.get(0), false,1)
//            +" VALUES ";
                    Date dLastTime = LogicaVerificari.getDataDinString("0");
                    db.beginTransaction();
                    while (i < lLista.size()) {
                        tRow = (LinkedTreeMap) lLista.get(i);
                        // campul timestamp
                        String sTime = ((LinkedTreeMap) lLista.get(i)).get("timestamp").toString();
                        Date dTime = LogicaVerificari.getDataDinString(sTime);
                        dLastTime = (dLastTime.before(dTime) ? dTime : dLastTime);
                        // se extrage val din campul de cheie primara
                        sValId = ((LinkedTreeMap) lLista.get(i)).get(sCampId).toString();
                        // se cauta valoarea in tabela de sincronizat
                        crs = db.rawQuery(
                                "SELECT " + sCampId + " FROM " + sTabela + " WHERE " + sCampId + " = '" + sValId + "'", null
                        );
                        // daca nu exista inregistrare se face mai departe
                        if (crs.getCount() == 0) {
                            lGasit = true;
                            // se ia doar lista de valori
//                        sqlCmd = sqlCmd + (i>0 ? ",":"")
//                                + LogicaVerificari.getSqlInsert(sTabela, tRow, false, 2);
                            sqlCmd = LogicaVerificari.getSqlInsertDinTabela(sTabela, tRow, false, 0);
                            db.execSQL(sqlCmd);
                        }
                        crs.close();
                        i++;
                    }
                    sqlCmd = "UPDATE " + Constructor.Tabela_Timestamp.NUME_TABEL + " SET " + Constructor.Tabela_Timestamp.COL_TIME + " = " +
                            "'" + LogicaVerificari.getStringDinData(dLastTime) + "'" +
                            " WHERE " + Constructor.Tabela_Timestamp.COL_TABELA + " = " + "'" + sTabela + "'";
                    db.execSQL(sqlCmd);
                    db.setTransactionSuccessful();
                    //sqlCmd.bindLong(1,lLista.get(1).);
                    //((LinkedTreeMap) lLista.get(0)).get("denumire")
                } catch (Exception e) {
                    String sMes = e.getMessage();
                    Log.d("Eroare", sMes);

                } finally {
                    db.endTransaction();
                }
                db.close();
            }
        }catch ( Exception e ) {
            e.getMessage();
        }

    }

    public void sincronizare_trimiteri (String sRaspuns) {
        // daca sraspuns = OK se sterg tabelele _alt
        Gson g = new Gson();
        //int sver = db.getVersion();
        List<Object> lLista; //=new ArrayList<HashMap<String, String>>();
        Type tipLista = new TypeToken<List<Object>>() {
        }.getType();
        try {
            lLista = g.fromJson(sRaspuns, tipLista);
            // am scos conditia de stergere din alt
            if ( true || (lLista.size() != 0)) {
 //               String sRez = ((LinkedTreeMap) lLista.get(0)).get("rez").toString();
                String sRez="OK";
                if (sRez.equals("OK")) {

                    SQLiteDatabase db = this.getWritableDatabase();
                    db.beginTransaction();
                    String sCmd="";
                    sCmd="DELETE FROM "+Constructor.Tabela_Antet_Trimiteri_Alt.NUME_TABEL +" WHERE 1=1 " ;
                    db.execSQL(sCmd);
                    sCmd="DELETE FROM "+Constructor.Tabela_Pozitii_Trimiteri_Alt.NUME_TABEL +" WHERE 1=1 " ;
                    db.execSQL(sCmd);
                    sCmd="DELETE FROM "+Constructor.Tabela_Incarc_Descarc_Alt.NUME_TABEL +" WHERE 1=1 " ;
                    db.execSQL(sCmd);
                    sCmd="DELETE FROM "+Constructor.Tabela_Pachete_Alt.NUME_TABEL +" WHERE 1=1 " ;
                    db.execSQL(sCmd);
//                    db.delete(Constructor.Tabela_Antet_Trimiteri_Alt.NUME_TABEL, "1=1", null);
//                    db.delete(Constructor.Tabela_Pachete_Alt.NUME_TABEL, "1=1", null);
//                    db.delete(Constructor.Tabela_Incarc_Descarc_Alt.NUME_TABEL, "1=1", null);
//                    db.delete(Constructor.Tabela_Pozitii_Trimiteri_Alt.NUME_TABEL, "1=1", null);

                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();

                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
