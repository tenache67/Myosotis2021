package ro.bluebit.Database;

import android.provider.BaseColumns;

import ro.bluebit.UTILITARE.LogicaVerificari;

public class Constructor {

    public Constructor() {
    }


    //definitie baza de date
    public static final String DATABASE_NAME = "Farmacie.db";


    public static abstract class Tip {
        public static final String INTREG = " integer not null default 0 ";
        public static final String DOUBLE = " double not null default 0 ";
        public static final String PRIMARY = " integer primary key ";
        public static final String PRIMARY_AUTO = " integer primary key autoincrement ";
        public static final String TEXT = " text not null default \'\' ";
        public static final String DATA = "data";
        public static final String VALOARE = " numeric not null default 0.000000 ";
        public static final String TIMESTAMP = " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ";
        public static final String ACTIV = " integer not null default 0";// unde 1 este activ
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
        public static final String COL_TIMESTAMP = "timestamp";

        public static final String[][] STR_TABEL= {
                {TabelaUtilizatorPin.COL_ID,Tip.PRIMARY,Tip.INTREG},
                {TabelaUtilizatorPin.COL_NUME,Tip.TEXT,Tip.TEXT},
                {TabelaUtilizatorPin.COL_PIN,Tip.INTREG,Tip.INTREG},
                {TabelaUtilizatorPin.COL_NIV_ACCES,Tip.INTREG,Tip.INTREG},
                {TabelaUtilizatorPin.COL_ID_DEPARTAMENT,Tip.INTREG,Tip.INTREG},
                {TabelaUtilizatorPin.COL_TIMESTAMP,Tip.TIMESTAMP,Tip.TIMESTAMP}
        };
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_UTILIZATORI = TabelaUtilizatorPin.SQL_CREAZA_TABEL ;

    // TABELA P_Lucru

    public static final class Tabela_P_Lucru implements BaseColumns {
        public static final String NUME_TABEL = "tabela_P_Lucru";
        public static final String COL_ID = "id_p_lucru";
        public static final String COL_DENUMIRE = "denumire";
        public static final String COL_ADRESA = "adresa";
        public static final String COL_TELEFON = "telefon";
        public static final String COL_ACTIV = "activ";
        public static final String COL_LATITUDINE = "latitudine";
        public static final String COL_LONGITUDINE = "longitudine";
        public static final String COL_CU_VERIFICARE = "verificare";


        public static final String[][] STR_TABEL = {
                {Tabela_P_Lucru.COL_ID, Tip.PRIMARY, Tip.INTREG},
                {Tabela_P_Lucru.COL_DENUMIRE, Tip.TEXT, Tip.TEXT},
                {Tabela_P_Lucru.COL_ADRESA, Tip.TEXT, Tip.TEXT},
                {Tabela_P_Lucru.COL_TELEFON, Tip.TEXT, Tip.TEXT},
                {Tabela_P_Lucru.COL_ACTIV, Tip.ACTIV, Tip.INTREG},
                {Tabela_P_Lucru.COL_LATITUDINE, Tip.DOUBLE, Tip.DOUBLE},
                {Tabela_P_Lucru.COL_LONGITUDINE, Tip.DOUBLE, Tip.DOUBLE},
                {Tabela_P_Lucru.COL_CU_VERIFICARE, Tip.INTREG, Tip.INTREG}
        }
                ;
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_P_LUCRU =Tabela_P_Lucru.SQL_CREAZA_TABEL;

    // sql insert
    public static final String SQL_INSERT_TABELA_P_LUCRU="INSERT OR REPLACE INTO "+Tabela_P_Lucru.NUME_TABEL+" ("+
            Tabela_P_Lucru.COL_ID + " , " +
            Tabela_P_Lucru.COL_DENUMIRE + " , " +
            Tabela_P_Lucru.COL_ADRESA + " , " +
            Tabela_P_Lucru.COL_TELEFON + " , " +
            Tabela_P_Lucru.COL_ACTIV +  " , " +
            Tabela_P_Lucru.COL_LATITUDINE +" , " +
            Tabela_P_Lucru.COL_LONGITUDINE +
            " ) VALUES (?,?,?,?,?,?,?) " ;


    //    TABELA RUTE
    public static final class Tabela_Rute implements BaseColumns {
        public static final String NUME_TABEL = "tabela_rute";
        public static final String COL_ID = "id_ruta";
        public static final String COL_DENUMIRE = "denumire";

        public static final String[][] STR_TABEL ={
                {Tabela_Rute.COL_ID,Tip.PRIMARY,Tip.INTREG},
                {Tabela_Rute.COL_DENUMIRE,Tip.TEXT,Tip.TEXT}};
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_RUTE = Tabela_Rute.SQL_CREAZA_TABEL ;

    //    TABELA RUTE_P_LUCRU
    public static final class Tabela_Rute_P_Lucru implements BaseColumns {
        public static final String NUME_TABEL = "tabela_rute_p_lucru";
        public static final String COL_ID_RUTA = "id_ruta";
        public static final String COL_ID_P_LUCRU = "_id_p_lucru";

        public static final String[][] STR_TABEL ={
                {Tabela_Rute_P_Lucru.COL_ID_RUTA,Tip.INTREG,Tip.INTREG},
                {Tabela_Rute_P_Lucru.COL_ID_P_LUCRU,Tip.INTREG,Tip.INTREG}};
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_RUTE_P_LUCRU = Tabela_Rute_P_Lucru.SQL_CREAZA_TABEL;

    public static final class Tabela_Timestamp implements  BaseColumns {
        public static final String NUME_TABEL = "tabela_timestamp";
        public static final String COL_TABELA = "tabela";
        public static final String COL_NUME_PRIMARY = "primary_col";
        public static final String COL_TIME = "last_time";
        public static final String COL_TIPSINC = "tip_sincro";
        public static final String[][] STR_TABEL= {
                {Tabela_Timestamp.NUME_TABEL, Tip.TEXT, Tip.TEXT},
                {Tabela_Timestamp.COL_TABELA, Tip.TEXT, Tip.TEXT},
                {Tabela_Timestamp.COL_NUME_PRIMARY,Tip.TEXT,Tip.TEXT},
                {Tabela_Timestamp.COL_TIME, Tip.TIMESTAMP, Tip.TIMESTAMP},
                {Tabela_Timestamp.COL_TIPSINC,Tip.INTREG,Tip.INTREG}
        };
        public static final String SQL_CREAZA_TABEL=LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    // creare tabela
    public static final String SQL_CREAZA_TABEL_TIMESTAMP=Tabela_Timestamp.SQL_CREAZA_TABEL;
    // TABELA PLAJA COD
    public static final class Tabela_Plaja_Cod implements BaseColumns {
        public static final String NUME_TABEL = "tabela_plaja_cod";
        public static final String COL_ID_LOT = "id_lot";
        public static final String COL_MINIM = "val_minima";
        public static final String COL_MAXIM = "val_maxima";

        public static final String[][] STR_TABEL={
                {Tabela_Plaja_Cod.COL_ID_LOT,Tip.PRIMARY,Tip.INTREG},
                {Tabela_Plaja_Cod.COL_MINIM,Tip.INTREG,Tip.INTREG},
                {Tabela_Plaja_Cod.COL_MAXIM,Tip.INTREG,Tip.INTREG}};
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_PLAJA_COD =Tabela_Plaja_Cod.SQL_CREAZA_TABEL;

    // TABELA ANTET TRIMITERI
    public static final class Tabela_Antet_Trimiteri implements BaseColumns {
        public static final String NUME_TABEL = "tabela_antet_trimiteri";
        public static final String COL_ID_ANTET_TRIMITERI = "id_antet_trimiteri";
        public static final String COL_ID_UTILIZATOR = "id_utilizator";
        public static final String COL_COD_BARE = "cod_bare";
        public static final String COL_ID_PRIORITATE="id_prioritate_transport";
        public static final String COL_ID_CONDITII="id_conditii_transport";
        public static final String COL_ID_TIP_TRIMITERE="id_tip_trimitere";
        public static final String COL_DATA="dataora";
        public static final String COL_FINALIZAT="finalizat" ;
        public static final String[][] STR_TABEL={
                {Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI,Tip.PRIMARY_AUTO,Tip.INTREG},
                {Tabela_Antet_Trimiteri.COL_ID_UTILIZATOR,Tip.INTREG,Tip.INTREG},
                {Tabela_Antet_Trimiteri.COL_COD_BARE,Tip.COD_BARE,Tip.TEXT},
                {Tabela_Antet_Trimiteri.COL_ID_PRIORITATE,Tip.INTREG,Tip.INTREG},
                {Tabela_Antet_Trimiteri.COL_ID_CONDITII,Tip.INTREG,Tip.INTREG},
                {Tabela_Antet_Trimiteri.COL_ID_TIP_TRIMITERE,Tip.INTREG,Tip.INTREG},
                {Tabela_Antet_Trimiteri.COL_DATA ,Tip.TIMESTAMP,Tip.TIMESTAMP},
                {Tabela_Antet_Trimiteri.COL_FINALIZAT ,Tip.INTREG,Tip.INTREG}
        };
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_ANTET_TRIMITERI = Tabela_Antet_Trimiteri.SQL_CREAZA_TABEL;
    // varianta cu ALT se foloseste pt salvare . varianta normala este pt sincronizarea cu bd
    public static final class Tabela_Antet_Trimiteri_Alt implements BaseColumns {
        public static final String NUME_TABEL = "tabela_antet_trimiteri_alt";
        public static final String COL_ID_ANTET_TRIMITERI = "id_antet_trimiteri";
        public static final String COL_ID_UTILIZATOR = "id_utilizator";
        public static final String COL_COD_BARE = "cod_bare";
        public static final String COL_ID_PRIORITATE="id_prioritate_transport";
        public static final String COL_ID_CONDITII="id_conditii_transport";
        public static final String COL_ID_TIP_TRIMITERE="id_tip_trimitere";
        public static final String COL_DATA="dataora";
        public static final String COL_FINALIZAT="finalizat" ;
        public static final String[][] STR_TABEL={
                {Tabela_Antet_Trimiteri_Alt.COL_ID_ANTET_TRIMITERI,Tip.PRIMARY_AUTO,Tip.INTREG},
                {Tabela_Antet_Trimiteri_Alt.COL_ID_UTILIZATOR,Tip.INTREG,Tip.INTREG},
                {Tabela_Antet_Trimiteri_Alt.COL_COD_BARE,Tip.COD_BARE,Tip.TEXT},
                {Tabela_Antet_Trimiteri_Alt.COL_ID_PRIORITATE,Tip.INTREG,Tip.INTREG},
                {Tabela_Antet_Trimiteri_Alt.COL_ID_CONDITII,Tip.INTREG,Tip.INTREG},
                {Tabela_Antet_Trimiteri_Alt.COL_ID_TIP_TRIMITERE,Tip.INTREG,Tip.INTREG},
                {Tabela_Antet_Trimiteri.COL_DATA ,Tip.TIMESTAMP,Tip.TIMESTAMP},
                {Tabela_Antet_Trimiteri_Alt.COL_FINALIZAT ,Tip.INTREG,Tip.INTREG}
        };
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_ANTET_TRIMITERI_ALT = Tabela_Antet_Trimiteri_Alt.SQL_CREAZA_TABEL;


    // TABELA POZITII TRIMITERI
    public static final class Tabela_Pozitii_Trimiteri implements BaseColumns {
            public static final String NUME_TABEL = "tabela_pozitii_trimiteri";
            public static final String COL_ID_POZITII_TRIMITERI = "id_pozitii_trimiteri";
            public static final String COL_ID_ANTET_TRIMITERI = "id_antet_trimiteri";
            public static final String COL_ID_TIP = "id_tip";
            public static final String COL_ID_P_LUCRU="id_p_lucru";
        public static final String[][] STR_TABEL={
                {Tabela_Pozitii_Trimiteri.COL_ID_ANTET_TRIMITERI,Tip.INTREG,Tip.INTREG},
                {Tabela_Pozitii_Trimiteri.COL_ID_POZITII_TRIMITERI,Tip.INTREG,Tip.INTREG},
                {Tabela_Pozitii_Trimiteri.COL_ID_TIP,Tip.INTREG,Tip.INTREG},
                {Tabela_Pozitii_Trimiteri.COL_ID_P_LUCRU,Tip.INTREG,Tip.INTREG}};
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_POZITII_TRIMITERI =Tabela_Pozitii_Trimiteri.SQL_CREAZA_TABEL;

    // TABELA POZITII TRIMITERI_ALT
    public static final class Tabela_Pozitii_Trimiteri_Alt implements BaseColumns {
        public static final String NUME_TABEL = "tabela_pozitii_trimiteri_alt";
        public static final String COL_ID_ANTET_TRIMITERI = "id_antet_trimiteri";
        public static final String COL_ID_POZITII_TRIMITERI = "id_pozitii_trimiteri";
        public static final String COL_ID_TIP = "id_tip";
        public static final String COL_ID_P_LUCRU="id_p_lucru";
        public static final String[][] STR_TABEL={
                {Tabela_Pozitii_Trimiteri_Alt.COL_ID_ANTET_TRIMITERI,Tip.INTREG,Tip.INTREG},
                {Tabela_Pozitii_Trimiteri_Alt.COL_ID_POZITII_TRIMITERI,Tip.INTREG,Tip.INTREG},
                {Tabela_Pozitii_Trimiteri_Alt.COL_ID_TIP,Tip.INTREG,Tip.INTREG},
                {Tabela_Pozitii_Trimiteri_Alt.COL_ID_P_LUCRU,Tip.INTREG,Tip.INTREG}};
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_POZITII_TRIMITERI_ALT =Tabela_Pozitii_Trimiteri_Alt.SQL_CREAZA_TABEL;

    // TABELA INCARCARI-DESCARCARI
        public static final class Tabela_Incarc_Descarc implements BaseColumns {
            public static final String NUME_TABEL = "tabela_incarc_descarc";
            public static final String COL_ID_INCARC_DESCARC = "id_incarc_descarc";
            public static final String COL_ID_ANTET_TRIMITERI = "id_antet_trimiteri";
            public static final String COL_ID_UTILIZATOR="id_utilizator";
            public static final String COL_ID_TIP="id_tip";// (3 incarcare, 4 descarcare)
            public static final String COL_ID_P_LUCRU ="id_p_lucru";
            public static final String COL_DATA ="dataora";
            public static final String[][] STR_TABEL ={
                    {Tabela_Incarc_Descarc.COL_ID_INCARC_DESCARC,Tip.PRIMARY_AUTO,Tip.INTREG},
                    {Tabela_Incarc_Descarc.COL_ID_ANTET_TRIMITERI,Tip.INTREG,Tip.INTREG},
                    {Tabela_Incarc_Descarc.COL_ID_UTILIZATOR,Tip.INTREG,Tip.INTREG},
                    {Tabela_Incarc_Descarc.COL_ID_TIP,Tip.INTREG,Tip.INTREG},
                    {Tabela_Incarc_Descarc.COL_ID_P_LUCRU,Tip.INTREG,Tip.INTREG},
                    {Tabela_Incarc_Descarc.COL_DATA,Tip.TIMESTAMP,Tip.TIMESTAMP},
            };
            public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
        }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_INCARC_DESCARC = Tabela_Incarc_Descarc.SQL_CREAZA_TABEL;

    public static final class Tabela_Incarc_Descarc_Alt implements BaseColumns {
        public static final String NUME_TABEL = "tabela_incarc_descarc_alt";
        public static final String COL_ID_INCARC_DESCARC = "id_incarc_descarc";
        public static final String COL_ID_ANTET_TRIMITERI = "id_antet_trimiteri";
        public static final String COL_ID_UTILIZATOR="id_utilizator";
        public static final String COL_ID_TIP="id_tip";// (3 incarcare, 4 descarcare)
        public static final String COL_ID_P_LUCRU ="id_p_lucru";
        public static final String COL_DATA ="dataora";
        public static final String[][] STR_TABEL ={
                {Tabela_Incarc_Descarc_Alt.COL_ID_INCARC_DESCARC,Tip.PRIMARY_AUTO,Tip.INTREG},
                {Tabela_Incarc_Descarc_Alt.COL_ID_ANTET_TRIMITERI,Tip.INTREG,Tip.INTREG},
                {Tabela_Incarc_Descarc_Alt.COL_ID_UTILIZATOR,Tip.INTREG,Tip.INTREG},
                {Tabela_Incarc_Descarc_Alt.COL_ID_TIP,Tip.INTREG,Tip.INTREG},
                {Tabela_Incarc_Descarc_Alt.COL_ID_P_LUCRU,Tip.INTREG,Tip.INTREG},
                {Tabela_Incarc_Descarc_Alt.COL_DATA,Tip.TIMESTAMP,Tip.TIMESTAMP},
        };
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_INCARC_DESCARC_ALT = Tabela_Incarc_Descarc_Alt.SQL_CREAZA_TABEL;


// TABELA PACHETE
        public static final class Tabela_Pachete implements BaseColumns {
            public static final String NUME_TABEL = "tabela_pachete";
            public static final String COL_ID_TRIMITERI_VECHI = "id_antet_trimiteri_vechi";
            public static final String COL_ID_TRIMITERI_NOU = "id_antet_trimiteri_nou";
            public static final String[][] STR_TABEL ={
                {Tabela_Pachete.COL_ID_TRIMITERI_VECHI,Tip.INTREG,Tip.INTREG},
                {Tabela_Pachete.COL_ID_TRIMITERI_NOU,Tip.INTREG,Tip.INTREG}};
            public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
        }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_PACHETE = Tabela_Pachete.SQL_CREAZA_TABEL;
    public static final class Tabela_Pachete_Alt implements BaseColumns {
        public static final String NUME_TABEL = "tabela_pachete_alt";
        public static final String COL_ID_TRIMITERI_VECHI = "id_antet_trimiteri_vechi";
        public static final String COL_ID_TRIMITERI_NOU = "id_antet_trimiteri_nou";
        public static final String[][] STR_TABEL ={
                {Tabela_Pachete_Alt.COL_ID_TRIMITERI_VECHI,Tip.INTREG,Tip.INTREG},
                {Tabela_Pachete_Alt.COL_ID_TRIMITERI_NOU,Tip.INTREG,Tip.INTREG}};
        public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_PACHETE_ALT = Tabela_Pachete_Alt.SQL_CREAZA_TABEL;



        // TABELA TIPURI PENTRU OPERATII        (am numit tabela tipuri pentru a nu fi confundata cu clasa Tip
        public static final class Tabela_Tipuri implements BaseColumns {
            public static final String NUME_TABEL = "tabela_tipuri";
            public static final String COL_ID_TIPURI= "id_tipuri";
            public static final String COL_DENUMIRE = "denumire" ;
            public static final String[][] STR_TABEL ={
                    {Tabela_Tipuri.COL_ID_TIPURI , Tip.INTREG,Tip.INTREG},
                    {Tabela_Tipuri.COL_DENUMIRE , Tip.TEXT,Tip.TEXT}};
            public static final String SQL_CREAZA_TABEL= LogicaVerificari.getSqlCreeazaTabela(NUME_TABEL,STR_TABEL);
        }

    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_TIPURI = Tabela_Tipuri.SQL_CREAZA_TABEL ;



    public static  final String SQL_QUERY_OBTI_PLUCRU = (
            "SELECT "+ Tabela_P_Lucru.COL_DENUMIRE+ " FROM " +Tabela_P_Lucru.NUME_TABEL);

    // returneaza structura dintr-o clasa a unei tabele
    public static String [][] getStrTabela (String sTabela) {

        switch (sTabela) {
            case Tabela_P_Lucru.NUME_TABEL :
                return Tabela_P_Lucru.STR_TABEL ;
            case TabelaUtilizatorPin.NUME_TABEL :
                return TabelaUtilizatorPin.STR_TABEL;
            case Tabela_Rute.NUME_TABEL :
                return Tabela_Rute.STR_TABEL;
            case Tabela_Rute_P_Lucru.NUME_TABEL :
                return Tabela_Rute_P_Lucru.STR_TABEL;
            case Tabela_Plaja_Cod.NUME_TABEL :
                return Tabela_Plaja_Cod.STR_TABEL;
            case Tabela_Antet_Trimiteri.NUME_TABEL :
                return Tabela_Antet_Trimiteri.STR_TABEL;
            case Tabela_Pozitii_Trimiteri.NUME_TABEL :
                return Tabela_Pozitii_Trimiteri.STR_TABEL ;
            case Tabela_Incarc_Descarc.NUME_TABEL :
                return Tabela_Incarc_Descarc.STR_TABEL;
            case Tabela_Pachete.NUME_TABEL :
                return Tabela_Pachete.STR_TABEL;
            case Tabela_Antet_Trimiteri_Alt.NUME_TABEL :
                return Tabela_Antet_Trimiteri_Alt.STR_TABEL;
            case Tabela_Pozitii_Trimiteri_Alt.NUME_TABEL :
                return Tabela_Pozitii_Trimiteri_Alt.STR_TABEL ;
            case Tabela_Incarc_Descarc_Alt.NUME_TABEL :
                return Tabela_Incarc_Descarc_Alt.STR_TABEL;
            case Tabela_Pachete_Alt.NUME_TABEL :
                return Tabela_Pachete_Alt.STR_TABEL;
            case Tabela_Tipuri.NUME_TABEL :
                return Tabela_Tipuri.STR_TABEL ;
            case Tabela_Timestamp.NUME_TABEL :
                return Tabela_Timestamp.STR_TABEL ;
            default:
                return null;
        }
    }

}
