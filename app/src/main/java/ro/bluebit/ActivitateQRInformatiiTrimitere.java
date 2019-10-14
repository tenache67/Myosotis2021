package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class ActivitateQRInformatiiTrimitere extends AppCompatActivity {

    EditText EditTextScanareInformatiiQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitate_qrinformatii_trimitere);
        IntroducereCodQR();
    }

    public void IntroducereCodQR(){
        EditTextScanareInformatiiQR = findViewById(R.id.edittext_qr_informatii_id);
        EditTextScanareInformatiiQR.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    private Timer timer = new Timer();
                    private final long DELAY = 1000; // milliseconds

                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getApplicationContext(),InformatiiTrimitere.class);
                                        startActivity(intent);
                                    }
                                },
                                DELAY
                        );
                    }
                }
        );
    }
}
