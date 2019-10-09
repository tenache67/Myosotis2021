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
        public static final String DATA = " date not null default current_date ";
        public static final String VALOARE = " numeric not null default 0.000000 ";
        public static final String TIMESTAMP = " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ";
    }

    public static final class  TabelaUtilizatorPin implements BaseColumns {
        public static final String NUME_TABEL= "Tabela_utilizatori";
        public static final String COL_ID="_id";
        public static final String COL_NUME="nume";
        public static final String COL_PIN="pin";
        public static final String COL_NIV_ACCES="nivel_acces";
        public static final String  COL_ID_DEPARTAMENT="_id_departament";

    }
    //sql creare tabel
    public static final String SQL_CREAZA_TABEL_UTILIZATORI = (" create table if not exists " +
            TabelaUtilizatorPin.NUME_TABEL + " ( " +
            TabelaUtilizatorPin.COL_ID + Tip.PRIMARY + " , " +
            TabelaUtilizatorPin.COL_NUME + Tip.TEXT + " , " +
            TabelaUtilizatorPin.COL_PIN + Tip.INTREG + " , " +
            TabelaUtilizatorPin.COL_NIV_ACCES+Tip.INTREG + " , " +
            TabelaUtilizatorPin.COL_ID_DEPARTAMENT+Tip.INTREG
            + ")");

}
