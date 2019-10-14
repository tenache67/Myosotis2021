package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;

public class Incarca_Trimiteri_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_incarca_trimiteri);

        EditText cod_bare=findViewById(R.id.cod_bare);
}

}



