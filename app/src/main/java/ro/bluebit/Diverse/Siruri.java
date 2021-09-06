package ro.bluebit.Diverse;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;



public class Siruri {
    public final static char CR  = (char) 0x0D;
    public final static char LF  = (char) 0x0A;

    public static String getCRLF() {
        return "\r\n";
    }
    // genereaza un sir pt folosit la functii
    public static String pad(){
        return "          "+"          "+"          "+"          "+"          "+"          "+
                "          "+"          "+"          "+"          "+"          "+"          "+
                "          "+"          "+"          "+"          "+"          "+"          "+
                "          "+"          "+"          "+"          "+"          "+"          "+
                "          "+"          "+"          "+"          "+"          "+"          "+
                "          "+"          "+"          "+"          "+"          "+"          ";
    }
    public static String replicate(String sir, int nLung) {
        return pad().substring(0,nLung).replace(" ", sir);
    }
    public static String padL (String sir, int nLung, String sPad) {
        return sir.length()<nLung ? Siruri.replicate(sPad, nLung-sir.length())+sir : sir.substring(0,nLung) ;
    }

    public static String padL (String sir, int nLung) {
        return Siruri.padL(sir, nLung, " ");
        //return sir.length()<nLung ? Siruri.replicate(" ", nLung-sir.length())+sir : sir ;
    }

    public static String padR (String sir, int nLung, String sPad) {
        return sir.length()<nLung ? sir+Siruri.replicate(sPad, nLung-sir.length()) : sir.substring(0,nLung) ;
    }
    public static String padR (String sir, int nLung) {
        return Siruri.padR(sir, nLung, " ");
        //return sir.length()<nLung ? sir+Siruri.replicate(" ", nLung-sir.length()) : sir ;
    }
    public static double round (double nVal, int nZec) {
        return (double) Math.round(nVal*Math.pow(10, nZec))/Math.pow(10, nZec);
    }

    //transforma un numar in sir
    public static String str(double num, int nLung, int nZec) {
        String sir=String.valueOf(round(num, nZec));
        Log.d("PRO&", "Numar: " + sir);
        if (nZec>0) {
            String zecimal=Siruri.padR(sir.substring(sir.indexOf(".")+1, sir.length()),nZec,"0");
            String intreg=padL(sir.substring(0,sir.indexOf(".")),nLung-zecimal.length()-1);
            return intreg+"."+zecimal;
        }
        else {
            String intreg = padL(sir.substring(0, sir.indexOf(".")), nLung);
            return intreg ;
        }
    }

    public static String dtos(Calendar c, String sep) {
        return c.get(Calendar.YEAR) +sep+ Siruri.padL((c.get(Calendar.MONTH)+1)+"",2,"0") +sep+ Siruri.padL(c.get(Calendar.DAY_OF_MONTH)+"",2,"0") ;
    }

    public static String dtos (Calendar c) {
        return dtos(c,"");
        //return c.get(Calendar.YEAR) + ""+ Siruri.padL((c.get(Calendar.MONTH)+1)+"",2,"0") + c.get(Calendar.DAY_OF_MONTH) ;
    }

    public static String dtoc (Calendar c) {
        return c.get(Calendar.DAY_OF_MONTH) + "-" + (c.get(Calendar.MONTH)+1)+ "-" + c.get(Calendar.YEAR) ;
    }
    // determina o data din sir de forma yyyy-mm-zz sau dd-mm-yyyy
    // daca nu este - in sir este de forma yyyymmzz
    public static Calendar cTod (String d ) {
        Calendar c = Calendar.getInstance();
        try {
            if (d.indexOf("-")>0)
                // incepe cu an
                c.set(Integer.valueOf(d.substring(0,4)),Integer.valueOf(d.substring(5, 7))-1,Integer.valueOf(d.substring(8,10)));
            else
                c.set(Integer.valueOf(d.substring(0,4)),Integer.valueOf(d.substring(4, 6))-1,Integer.valueOf(d.substring(6,8)));
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return c;
    }
    // transforma cod ascii in sir
    public static String chr(int num) {
        return Character.toString ((char) num);
    }

    public static int getIntDinString(String sval) {
        int iRez=0;
        try {
            iRez=Integer.valueOf(sval);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return iRez;
    }

    //determina valoare din imagine sirului
    public static double getDoubleDinString(String sSir){
        double nval=0.0;
        try {
            nval=Double.valueOf(sSir);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return nval;
    }

    // determina numar din data calendaristica dupa modelul yyyymmzz
    public static int getNrAnLunaZi (Calendar d) {
        return d.get(Calendar.YEAR)*10000+(d.get(Calendar.MONTH)+1)*100+d.get(Calendar.DAY_OF_MONTH);
    }

    public static Calendar getDateTime() {
        return Calendar.getInstance(TimeZone.getDefault());
    }

    public static String ttos(Calendar t) {
        return dtos(t, "-")+" "+t.get(Calendar.HOUR_OF_DAY)+":"+t.get(Calendar.MINUTE)+":"+t.get(Calendar.SECOND);
    }
    // se extrage un subsir din sSir conform parametrului nAlign
    // nAlign =0 - aliniere la stanga 1 - dreapta 2 - centru
    // nRow - numarul randului
    /*public static String alignSir ( String sSir, int nRow, int nAlign, int nWidth ) {
        String sRez="";
        if (nWidth>=sSir.length()) {
            sSir=padR(sSir,nWidth);
        } else {

        }
        return sRez;
    }*/



    // converteste un cursor in string json
    public static String cursorToJsonString(Cursor crs) {
        JSONArray arr = new JSONArray();
        crs.moveToFirst();
        while (!crs.isAfterLast()) {
            int nColumns = crs.getColumnCount();
            JSONObject row = new JSONObject();
            for (int i = 0 ; i < nColumns ; i++) {
                String colName = crs.getColumnName(i);
                if (colName != null) {
                    String val = "";
                    try {
                        switch (crs.getType(i)) {
                            case Cursor.FIELD_TYPE_BLOB   : row.put(colName, crs.getBlob(i).toString()); break;
                            case Cursor.FIELD_TYPE_FLOAT  : row.put(colName, crs.getDouble(i))         ; break;
                            case Cursor.FIELD_TYPE_INTEGER: row.put(colName, crs.getLong(i))           ; break;
                            case Cursor.FIELD_TYPE_NULL   : row.put(colName, null)                     ; break;
                            case Cursor.FIELD_TYPE_STRING : row.put(colName, crs.getString(i))         ; break;
                        }
                    } catch (JSONException e) {
                    }
                }
            }
            arr.put(row);
            if (!crs.moveToNext())
                break;
        }
        crs.close(); // close the cursor
        return arr.toString();
    }

    // creeaza sau adauga la un fis text
    public static void scrieFisLog(String sFisier, String sText, Context context) {
       // if (context!=null)
        if (false)
        {
            String sInsert =
                    Siruri.ttos(Siruri.getDateTime()) + " " +
                            sText + Siruri.getCRLF();

            try {
                FileOutputStream fdi = context.openFileOutput(sFisier, Context.MODE_APPEND);
                //FileOutputStream fdi=openFileOutput(fisier,MODE_PRIVATE );
                fdi.write(sInsert.getBytes());
                fdi.close();
                File fileDir = new File(context.getFilesDir(), sFisier);
                fileDir.toString();
                // Toast.makeText( this,"Fisier salvat in :" +fileDir , Toast.LENGTH_LONG);
                //afisezLoculSalvarii.setText("Fisier salvat in :" +fileDir );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

}

