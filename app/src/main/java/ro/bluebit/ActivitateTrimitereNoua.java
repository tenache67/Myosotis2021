package ro.bluebit;

import static java.lang.Long.parseLong;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.util.ArrayList;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.Diverse.Siruri;
import ro.bluebit.UTILITARE.CustomTextWatcher;
import ro.bluebit.UTILITARE.LogicaVerificari;

public class ActivitateTrimitereNoua extends BazaAppCompat {
    EditText cod_bare1, cod_bare2;
    TextView afisareMesaj;
    String PreiaCodBare;
    Button BtnManual;
    CameraSource cameraSource;
    SurfaceView surfaceView;
    BarcodeDetector barcodeDetector;
    public static ArrayList<String> StocareCodBare = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scaneaza_cod_bare);
        cod_bare1 = findViewById(R.id.cod_bare);
        cod_bare2 = findViewById(R.id.edittext_codbare_manual);
        BtnManual = findViewById(R.id.btn_cod_manual);
        afisareMesaj = findViewById(R.id.reporter);
        cod_bare2.setEnabled(false);
        //surfaceView=findViewById(R.id.camerapreview);

        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Scaneaza codul de bare");
        CustomTextWatcher customTextWatcher = new CustomTextWatcher(cod_bare1, afisareMesaj, PreiaCodBare, this);
        //IntroducereCodQR(); // Metoda TextWatcher pentru completare EditText cu codul de bare
        // BarcodeScanner(); // Metoda BarcodeScanner
        Toast.makeText(this, getIntent().getExtras().getString("UTILIZATOR"), Toast.LENGTH_SHORT).show();

        cod_bare1.addTextChangedListener(customTextWatcher);
        //EditTextCodQRManual.addTextChangedListener(customTextWatcher);
        //String sSqlCmd = "SELECT " + Constructor.TabAntetLegaturi.COL_2 + " FROM " + Constructor.TabAntetLegaturi.NUME_TABEL +
        //                " WHERE " + Constructor.TabAntetLegaturi.COL_3 + " = " + codTV.getText().toString();

    }
    public void ClickManual(View view){
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


/*        switch (BtnManual.getText().toString()){
            case"Introduc manual codul":
                EditTextCodQR.setEnabled(false);
                EditTextCodQRManual.setEnabled(true);
                EditTextCodQRManual.requestFocus();
                BtnManual.setText("Scaneaza codul");
*//*                EditTextCodQRManual.setEnabled(true);
                EditTextCodQRManual.requestFocus();*//*
                break;
            case "Scaneaza codul":
                EditTextCodQR.setText(EditTextCodQRManual.getText().toString());
                EditTextCodQRManual.setText("");
                EditTextCodQR.setEnabled(true);
                EditTextCodQR.requestFocus();
               *//* EditTextCodQRManual.setEnabled(false);
                EditTextCodQR.setEnabled(true);
                EditTextCodQR.requestFocus();*//*
                BtnManual.setText("Introduc manual codul");
                break;
        }*/

    }

    public void IntroducereCodQR() {
        cod_bare1 = findViewById(R.id.edittext_scanare_id);
//        EditTextCodQR.addTextChangedListener(
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
//                                       Intent intent = new Intent(getApplicationContext(),TrimitereNouaDupaCompletareCodQR.class);
//                                        String ag=EditTextCodQR.getText().toString().trim();
//                                        if(ag.length() != 0){
//                                            StocareCodBare.add(ag);
//                                        }
//                                       startActivity(intent);
//                                    }
//                                },
//                                DELAY
//                        );
//                    }
//                }
//        );
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


        //inlaturarea zerourilor din sirul de caractere
        //String codBareFaraZerouri = LogicaVerificari.RemoveZero(sCodBare);
        String codBareFaraZerouri = sCodBare;
        //transformarea sirului de caractere in long
        //long codBareScurt = parseLong(codBareFaraZerouri);
        long codBareScurt = parseLong(sCodBare);
        //verificare existenta in plaja de coduri
        boolean existInPlajaCoduri = LogicaVerificari.verificareExistentaInPlajaDeCoduri(db, codBareScurt);
        //transformarea in long a sirului da caractere din edittext
        long codBareLung = parseLong(sCodBare);
        //verificarea existentei inregistrarii in tabela Antet  Trimiteri
        boolean existInAntetTrimiteri = LogicaVerificari.verificareExistentaInAntetTrimiteri(db, sCodBare);

//        if (existInPlajaCoduri)
//            Toast.makeText(this, "Codul de bare " + sCodBare + "exista in plaja de coduri", Toast.LENGTH_SHORT).show();
//        else {
//
//        }


        if (existInPlajaCoduri && existInAntetTrimiteri) {
           // Toast.makeText(this, "Codul de bare " + sCodBare + "exista in Antet Trimiteri si a fost adaugat in lista de trimiteri", Toast.LENGTH_SHORT).show();
            cod_bare1.setText("");
        }
        if (existInPlajaCoduri && !existInAntetTrimiteri) {

            //Toast.makeText(this, "Codul este unul nou, completeaza campurile", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), TrimitereNouaDupaCompletareCodQR.class);
            intent.putExtra("UserPL", getIntent().getExtras().getString("UserPL"));
            String ag = cod_bare1.getText().toString().trim();
            intent.putExtra("CodBare", sCodBare);
            intent.putExtra("UTILIZATOR", getIntent().getExtras().getString("UTILIZATOR"));
            startActivityForResult(intent,1);
            cod_bare1.setText("");
        }
        else {
            alertMesajValidari("Cod Incorect", "A fost deja scanat!");
            alertaSunet();
            vibration();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // sincronizare
//            LogicaVerificari.executaSincroNomenc(this) ;
        Siruri.scrieFisLog("logSincroDate.txt","EXECUTA SINRO DUPA TRIMITERE NOUA",this);
        LogicaVerificari.executaSincroTrimiteri(this);
        LogicaVerificari.executaSincroRecTrimiteri(this);

    }

    public void alertMesajValidari(String title, String alert) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(alert);
        alertDialog.setTitle(title);
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.show();
    }
    public void alertaSunet() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.alarma);
        mp.start();
    }

    public Vibrator vibration() {
        final Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        final long[] pattern = {800, 300};
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < pattern.length; i++) {
                    v.vibrate(pattern, -1);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        v.vibrate(pattern, -1);
        return v;

    }

    public boolean insertData() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();

        ContentValues contentValue = new ContentValues();
        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE, cod_bare1.getText().toString());
        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_ID_UTILIZATOR, getIntent().getExtras().getString("UTILIZATOR"));

        long result = db.insert(Constructor.Tabela_Antet_Trimiteri.NUME_TABEL, null, contentValue);
        if (result == -1) {
            return false;
        } else {
            return true;

        }

//        Intent intent = new Intent(getApplicationContext(),TrimitereNouaDupaCompletareCodQR.class);
//        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
// SCANNER COD BARE
//    public void BarcodeScanner(){
//        barcodeDetector = new BarcodeDetector.Builder(this)
//                .setBarcodeFormats(Barcode.ALL_FORMATS).build();
//
//
//        cameraSource = new CameraSource.Builder(this,barcodeDetector)
//                .setRequestedPreviewSize(1920,1080).setAutoFocusEnabled(true).build();
//
//
//
//
//
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder surfaceHolder) {
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//                    return;
//                }
//                try
//                {
//
//
//                    cameraSource.start(surfaceHolder);
//                } catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//                cameraSource.stop();
//            }
//        });
//        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
//            @Override
//            public void release() {
//
//            }
//
//            @Override
//            public void receiveDetections(Detector.Detections<Barcode> detections) {
//                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
//
//                if (qrCodes.size()!=0){
//                    EditTextCodQR.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
//                            vibrator.vibrate(1000);
//                            EditTextCodQR.setText(qrCodes.valueAt(0).displayValue);
//
//                        }
//                    });
//                }
//            }
//        });

//    }



