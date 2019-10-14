package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class ActivitateTrimitereNoua extends AppCompatActivity {
    EditText EditTextCodQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimitere_noua);
        IntroducereCodQR(); // Metoda TextWatcher pentru completare EditText cu codul de bare

    }

    public void IntroducereCodQR() {
        EditTextCodQR = findViewById(R.id.edittext_scanare_id);
        EditTextCodQR.addTextChangedListener(
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
                                       Intent intent = new Intent(getApplicationContext(),TrimitereNouaDupaCompletareCodQR.class);
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
