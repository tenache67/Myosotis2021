package ro.bluebit.Database;

import android.provider.BaseColumns;

public class Constructor {

    public Constructor() {
    }

    //definitie baza de date
    public static final String DATABASE_NAME = "Farmacie.db";

    public static abstract class Tip {
        public static final String INTREG = " integer not null default 0 ";
        public static final String PRIMARY = " integer primary key ";
        public static final String PRIMARY_AUTO = " integer primary key autoincrement ";
        public static final String TEXT = " text not null default \'\' ";
        public static final String DATA = " date not null default current_date_time ";
        public static final String VALOARE = " numeric not null default 0.000000 ";
        public static final String TIMESTAMP = " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ";
        public static final String ACTIV = " integer not null default 1";// unde 1 este activ
        public static final String GPS = " [double latitude, double longitude] ";
        public static final String COD_BARE="  text not null default \'\' ";
    }

    // TABELA UTILIZATORI
    public static final class TabelaUtilizatorPin implements BaseColumns {
        public static final String NUME_TABEL = "tabela_utilizatori";
        public static final String COL_ID = "id_utilizator";
        public static final String COL_NUME = "nume";
        public static final String COL_PIN = "pin";
        public static final String COL_NIV_ACCES = "nivel_acces";
        public static final String COL_ID_DEPARTAMENT = "id_departament";
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_UTILIZATORI = (" create table if not exists " +
            TabelaUtilizatorPin.NUME_TABEL + " ( " +
            TabelaUtilizatorPin.COL_ID + Tip.PRIMARY + " , " +
            TabelaUtilizatorPin.COL_NUME + Tip.TEXT + " , " +
            TabelaUtilizatorPin.COL_PIN + Tip.INTREG + " , " +
            TabelaUtilizatorPin.COL_NIV_ACCES + Tip.INTREG + " , " +
            TabelaUtilizatorPin.COL_ID_DEPARTAMENT + Tip.INTREG
            + ")");





    // TABELA P_Lucru

    public static final class Tabela_P_Lucru implements BaseColumns {
        public static final String NUME_TABEL = "tabela_P_Lucru";
        public static final String COL_ID = "id_p_lucru";
        public static final String COL_DENUMIRE = "denumire";
        public static final String COL_ADRESA = "adresa";
        public static final String COL_TELEFON = "telefon";
        public static final String COL_ACTIV = "activ";
        public static final String COL_COORDONATE = "coordonate";
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_P_LUCRU = (" create table if not exists " +
            Tabela_P_Lucru.NUME_TABEL + " ( " +
            Tabela_P_Lucru.COL_ID + Tip.PRIMARY + " , " +
            Tabela_P_Lucru.COL_DENUMIRE + Tip.TEXT + " , " +
            Tabela_P_Lucru.COL_ADRESA + Tip.TEXT + " , " +
            Tabela_P_Lucru.COL_TELEFON + Tip.INTREG + " , " +
            Tabela_P_Lucru.COL_ACTIV + Tip.ACTIV + " , " +
            Tabela_P_Lucru.COL_COORDONATE + Tip.GPS
            + ")");




    //    TABELA RUTE
    public static final class Tabela_Rute implements BaseColumns {
        public static final String NUME_TABEL = "tabela_rute";
        public static final String COL_ID = "id_ruta";
        public static final String COL_DENUMIRE = "denumire";
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_RUTE = (" create table if not exists " +
            Tabela_Rute.NUME_TABEL + " ( " +
            Tabela_Rute.COL_ID + Tip.PRIMARY + " , " +
            Tabela_Rute.COL_DENUMIRE + Tip.TEXT
            + ")");




    //    TABELA RUTE_P_LUCRU
    public static final class Tabela_Rute_P_Lucru implements BaseColumns {
        public static final String NUME_TABEL = "tabela_rute_p_lucru";
        public static final String COL_ID_RUTA = "id_ruta";
        public static final String COL_ID_P_LUCRU = "_id_p_lucru";
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_RUTE_P_LUCRU = (" create table if not exists " +
            Tabela_Rute_P_Lucru.NUME_TABEL + " ( " +
            Tabela_Rute_P_Lucru.COL_ID_RUTA + Tip.INTREG + " , " +
            Tabela_Rute_P_Lucru.COL_ID_P_LUCRU + Tip.INTREG + " , " +
            "PRIMARY KEY ("+Tabela_Rute_P_Lucru.COL_ID_RUTA + " , " +Tabela_Rute_P_Lucru.COL_ID_P_LUCRU+")"
            + ")");




    // TABELA PLAJA COD
    public static final class Tabela_Plaja_Cod implements BaseColumns {
        public static final String NUME_TABEL = "tabela_plaja_cod";
        public static final String COL_ID_LOT = "id_lot";
        public static final String COL_MINIM = "val_minima";
        public static final String COL_MAXIM = "val_maxima";
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_PLAJA_COD = (" create table if not exists " +
            Tabela_Plaja_Cod.NUME_TABEL + " ( " +
            Tabela_Plaja_Cod.COL_ID_LOT + Tip.PRIMARY + " , " +
            Tabela_Plaja_Cod.COL_MINIM + Tip.INTREG + " , " +
            Tabela_Plaja_Cod.COL_MAXIM + Tip.INTREG
            + ")");



    // TABELA ANTET TRIMITERI
    public static final class Tabela_Antet_Trimiteri implements BaseColumns {
        public static final String NUME_TABEL = "tabela_antet_trimiteri";
        public static final String COL_ID_ANTET_TRIMITERI = "id_antet_trimiteri";
        public static final String COL_ID_UTILIZATOR = "id_utilizator";
        public static final String COL_COD_BARE = "cod_bare";
        public static final String COL_ID_PRIORITATE="id_prioritate_transport";
        public static final String COL_ID_CONDITII="id_condtitii_transport";
        public static final String COL_DATA="data";
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_ANTET_TRIMITERI = (" create table if not exists " +
            Tabela_Antet_Trimiteri.NUME_TABEL + " ( " +
            Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + Tip.PRIMARY_AUTO+ " , " +
            Tabela_Antet_Trimiteri.COL_ID_UTILIZATOR + Tip.INTREG + " , " +
            Tabela_Antet_Trimiteri.COL_COD_BARE + Tip.COD_BARE + " , " +
            Tabela_Antet_Trimiteri.COL_ID_PRIORITATE + Tip.INTREG + " , " +
            Tabela_Antet_Trimiteri.COL_ID_CONDITII + Tip.INTREG + " , " +
            Tabela_Antet_Trimiteri.COL_DATA + Tip.DATA
            + ")");




// TABELA POZITII TRIMITERI

    public static final class Tabela_Pozitii_Trimiteri implements BaseColumns {
            public static final String NUME_TABEL = "tabela_pozitii_trimiteri";
            public static final String COL_ID_ANTET_TRIMITERI = "id_antet_trimiteri";
            public static final String COL_ID_TIP = "id_tip";
            public static final String COL_ID_P_LUCRU="id_p_lucru";
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_POZITII_TRIMITERI = (" create table if not exists " +
            Tabela_Pozitii_Trimiteri.NUME_TABEL + " ( " +
            Tabela_Pozitii_Trimiteri.COL_ID_ANTET_TRIMITERI + Tip.INTREG + " , " +
            Tabela_Pozitii_Trimiteri.COL_ID_TIP + Tip.INTREG + " , " +
            Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU + Tip.INTREG
            + ")");



// TABELA INCARCARI-DESCARCARI
        public static final class Tabela_Incarc_Descarc implements BaseColumns {
            public static final String NUME_TABEL = "tabela_incarc_descarc";
            public static final String COL_ID_INCARC_DESCARC = "id_incarc_descarc";
            public static final String COL_ID_ANTET_TRIMITERI = "id_antet_trimiteri";
            public static final String COL_ID_UTILIZATOR="id_utilizator";
            public static final String COL_ID_TIP="id_tip"; // (1 incarcare, 2 descarcare)
        }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_INCARC_DESCARC = (" create table if not exists " +
            Tabela_Incarc_Descarc.NUME_TABEL + " ( " +
            Tabela_Incarc_Descarc.COL_ID_INCARC_DESCARC + Tip.PRIMARY_AUTO + " , " +
            Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI + Tip.INTREG + " , " +
            Tabela_Incarc_Descarc.COL_ID_UTILIZATOR + Tip.INTREG + " , " +
            Tabela_Incarc_Descarc.COL_ID_TIP + Tip.INTREG
            + ")");



// TABELA PACHETE
        public static final class Tabela_Pachete implements BaseColumns {
            public static final String NUME_TABEL = "tabela_pachete";
            public static final String COL_ID_TRIMITERI_VECHI = "id_antet_trimiteri_vechi";
            public static final String COL_ID_TRIMITERI_NOU = "id_antet_trimiteri_nou";
        }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_PACHETE = (" create table if not exists " +
            Tabela_Pachete.NUME_TABEL + " ( " +
            Tabela_Pachete.COL_ID_TRIMITERI_VECHI + Tip.INTREG + " , " +
            Tabela_Pachete.COL_ID_TRIMITERI_NOU + Tip.INTREG
            + ")");

// TABELA TIPURI PENTRU OPERATII        (am numit tabela tipuri pentru a nu fi confundata cu clasa Tip
    public static final class Tabela_Tipuri implements BaseColumns {
        public static final String NUME_TABEL = "tabela_tipuri";
        public static final String COL_ID_TIPURI= "id_tipuri";
        public static final String COL_DENUMIRE = "denumire" ;
    }

    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_TIPURI = (" create table if not exists " +
            Tabela_Tipuri.NUME_TABEL + " ( " +
            Tabela_Tipuri.COL_ID_TIPURI + Tip.INTREG + " , " +
            Tabela_Tipuri.COL_DENUMIRE + Tip.INTREG
            + ")");

    public static  final String SQL_QUERY_OBTI_PLUCRU = (
            "SELECT "+ Tabela_P_Lucru.COL_DENUMIRE+ " FROM " +Tabela_P_Lucru.NUME_TABEL);


}