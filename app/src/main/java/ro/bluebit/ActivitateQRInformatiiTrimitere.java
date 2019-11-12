package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.CustomTextWatcher;
import ro.bluebit.UTILITARE.LogicaVerificari;

import static java.lang.Long.parseLong;

public class ActivitateQRInformatiiTrimitere extends BazaAppCompat {

    EditText EditTextCodQR;
    TextView afisareMesaj;
    String PreiaCodBare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scaneaza_cod_bare);
        EditTextCodQR = findViewById(R.id.cod_bare);
        afisareMesaj = findViewById(R.id.reporter);
        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Scaneaza codul de bare:");
        CustomTextWatcher customTextWatcher = new CustomTextWatcher(EditTextCodQR, afisareMesaj, PreiaCodBare, this);
        EditTextCodQR.addTextChangedListener(customTextWatcher);


    }

    @Override
    public void executalacodvalid(String sCodBare) {
        super.executalacodvalid(sCodBare);

        sCodBare= EditTextCodQR.getText().toString();
        Toast.makeText(this, "Valoarea primita " + sCodBare, Toast.LENGTH_SHORT).show();
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();
        Intent intent = new Intent(getApplicationContext(), InformatiiTrimitere.class);

        //select id_antet_trimiteri from tabela_antet_trimiteri where cod_bare = "0000000000021"

        startActivity(intent);

    }

//    public void IntroducereCodQR(){
//        EditTextScanareInformatiiQR = findViewById(R.id.edittext_qr_informatii_id);
//        EditTextScanareInformatiiQR.addTextChangedListener(
//                new TextWatcher() {
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    }
//
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    }
//
//                    private Timer timer = new Timer();
//                    private final long DELAY = 1000; // milliseconds
//
//                    @Override
//                    public void afterTextChanged(final Editable s) {
//                        timer.cancel();
//                        timer = new Timer();
//                        timer.schedule(
//                                new TimerTask() {
//                                    @Override
//                                    public void run() {
//                                        Intent intent = new Intent(getApplicationContext(),InformatiiTrimitere.class);
//                                        startActivity(intent);
//                                    }
//                                },
//                                DELAY
//                        );
//                    }
//                }
//        );
//    }
}
