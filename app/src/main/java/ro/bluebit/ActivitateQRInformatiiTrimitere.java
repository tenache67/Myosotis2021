package ro.bluebit;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.CustomTextWatcher;

public class ActivitateQRInformatiiTrimitere extends BazaAppCompat {

    EditText cod_bare1, cod_bare2;
    TextView afisareMesaj;
    String PreiaCodBare;
    Button BtnManual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scaneaza_cod_bare);
        cod_bare1 = findViewById(R.id.cod_bare);
        cod_bare2 = findViewById(R.id.edittext_codbare_manual);
        afisareMesaj = findViewById(R.id.reporter);
        BtnManual = findViewById(R.id.btn_cod_manual);
        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Scaneaza codul de bare:");
        CustomTextWatcher customTextWatcher = new CustomTextWatcher(cod_bare1,afisareMesaj, PreiaCodBare, this);
        cod_bare1.addTextChangedListener(customTextWatcher);
//        cod_bare2.addTextChangedListener(customTextWatcher);

    }

    @Override
    public void executalacodvalid(String sCodBare) {
        super.executalacodvalid(sCodBare);
        if( cod_bare1.isEnabled()==true) {
            sCodBare = cod_bare1.getText().toString();
        }else {
            sCodBare = cod_bare2.getText().toString();
        }
            Toast.makeText(this, "Valoarea primita " + sCodBare, Toast.LENGTH_SHORT).show();
            DatabaseHelper myDb = new DatabaseHelper(this);
            SQLiteDatabase db = myDb.getWritableDatabase();
            Intent intent = new Intent(getApplicationContext(), InformatiiTrimitere.class);
            intent.putExtra("codbare", sCodBare);

            String selectieidantet = "Select " + Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI + " from " + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + " where " + Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE +
                    "=" + "'" + sCodBare + "'"; ///////Selectie ID ANTET


        Cursor crs = db.rawQuery(selectieidantet, null);
        if (crs.getCount() == 0) {
            Toast.makeText(this, "Codul nu exista", Toast.LENGTH_SHORT).show();
        } else {
            crs.moveToFirst();
            crs.close();
            startActivity(intent);
        }

    }

    public void ClickManual(View view) {
        switch (BtnManual.getText().toString()){
            case "Introduc manual codul":
                cod_bare2.setEnabled(true);
                cod_bare2.requestFocus();
                BtnManual.setText("Adauga codul");
                break;
            case "Adauga codul":
                if(cod_bare2.length()<1){
                    cod_bare2.getText().clear();

                }
                else{
                    cod_bare1.setText(cod_bare2.getText().toString());
                    cod_bare2.setText("");
                    BtnManual.setText("Introduc manual codul");
                    cod_bare1.requestFocus();
                }
                break;
        }
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
