package ro.bluebit;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import ro.bluebit.Database.DatabaseHelper;

public class BazaAppCompat extends AppCompatActivity implements BazaInterfataAppCompat{
    Bundle bHttpResponse ;
    Random rand = new Random();
    int nRand ;
    public int getnRand() {
        nRand=rand.nextInt(1000000);
        return nRand;
    }
    @Override
    public void executalacodvalid(String sCodBare) {

    }

    @Override

    public void executalaHttpResponse(String sScop,String sRaspuns) {

        DatabaseHelper Db = new DatabaseHelper(this);
        if (sScop.equals("SINCRONIZARE_TRIMITERI")) {
            // sincrinizare date ce vin din din server
            Log.d(sScop,"start");
            Db.sincronizare_trimiteri(sRaspuns);
        } else if (sScop.equals("SINCRONIZARE_RECEPTIE")) {
            // sincronizare date create in aparat
            Db.sincronizare_receptie(sRaspuns);
        }
        //      stergeRaspuns(sScop);
    }


    @Override
    public void stergeRaspuns(String sScop) {
        if (bHttpResponse!=null && bHttpResponse.get(sScop)!=null ) {
            bHttpResponse.remove(sScop);
        }
    }

    @Override
    public Boolean existaCerere(String sScop) {
        return  (bHttpResponse!=null && bHttpResponse.get(sScop)!=null ) ;
    }
}

