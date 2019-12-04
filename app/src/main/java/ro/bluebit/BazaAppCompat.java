package ro.bluebit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BazaAppCompat extends AppCompatActivity implements BazaInterfataAppCompat{
    Bundle bHttpResponse ;
    @Override
    public void executalacodvalid(String sCodBare) {

    }

    @Override
    public void executalaHttpResponse(String sScop,String sRaspuns)
    {
        bHttpResponse.putString(sScop,sRaspuns);
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

