package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;

public class BazaAppCompat extends AppCompatActivity implements BazaInterfataAppCompat{
    String sHttpResponse;
    @Override
    public void executalacodvalid(String sCodBare) {

    }

    @Override
    public void executalaHttpResponse(String sRaspuns) {
        sHttpResponse=sRaspuns;
    }
}

