package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import ro.bluebit.Database.Constructor;
import ro.bluebit.Database.DatabaseHelper;
import ro.bluebit.UTILITARE.CustomTextWatcher;
import ro.bluebit.UTILITARE.LogicaVerificari;
import ro.bluebit.UTILITARE.SelectieInitialaActivity;

import static java.lang.Long.parseLong;

public class ActivitateTrimitereNoua extends BazaAppCompat {
    EditText EditTextCodQR;
    TextView afisareMesaj;
    String PreiaCodBare;
    CameraSource cameraSource;
    SurfaceView surfaceView;
    BarcodeDetector barcodeDetector;
    public static ArrayList<String> StocareCodBare = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scaneaza_cod_bare);
        EditTextCodQR = findViewById(R.id.cod_bare);
        afisareMesaj = findViewById(R.id.reporter);
        //surfaceView=findViewById(R.id.camerapreview);

        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Scaneaza codul de bare");
        CustomTextWatcher customTextWatcher = new CustomTextWatcher(EditTextCodQR, afisareMesaj, PreiaCodBare, this);
        //IntroducereCodQR(); // Metoda TextWatcher pentru completare EditText cu codul de bare
        // BarcodeScanner(); // Metoda BarcodeScanner
        Toast.makeText(this, getIntent().getExtras().getString("UTILIZATOR"), Toast.LENGTH_SHORT).show();

        EditTextCodQR.addTextChangedListener(customTextWatcher);

        //String sSqlCmd = "SELECT " + Constructor.TabAntetLegaturi.COL_2 + " FROM " + Constructor.TabAntetLegaturi.NUME_TABEL +
        //                " WHERE " + Constructor.TabAntetLegaturi.COL_3 + " = " + codTV.getText().toString();

    }

    public void IntroducereCodQR() {
        EditTextCodQR = findViewById(R.id.edittext_scanare_id);
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
        Toast.makeText(this, "Valoarea primita " + sCodBare, Toast.LENGTH_SHORT).show();
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();


        //inlaturarea zerourilor din sirul de caractere
        String codBareFaraZerouri = LogicaVerificari.RemoveZero(sCodBare);
        //transformarea sirului de caractere in long
        long codBareScurt = parseLong(codBareFaraZerouri);
        //verificare existenta in plaja de coduri
        boolean existInPlajaCoduri = LogicaVerificari.verificareExistentaInPlajaDeCoduri(db, codBareScurt);
        //transformarea in long a sirului da caractere din edittext
        long codBareLung = parseLong(sCodBare);
        //verificarea existentei inregistrarii in tabela Antet  Trimiteri
        boolean existInAntetTrimiteri = LogicaVerificari.verificareExistentaInAntetTrimiteri(db, sCodBare);

        if (existInPlajaCoduri)
            Toast.makeText(this, "Codul de bare " + sCodBare + "exista in plaja de coduri", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Codul de bare " + sCodBare + "nu exista in lotul de coduri", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Corectati codul sau introduceti unul nou", Toast.LENGTH_SHORT).show();
        }


        if (existInPlajaCoduri && existInAntetTrimiteri) {
            Toast.makeText(this, "Codul de bare " + sCodBare + "exista in Antet Trimiteri si a fost adaugat in lista de trimiteri", Toast.LENGTH_SHORT).show();
            EditTextCodQR.setText("");
        } else if (existInPlajaCoduri && !existInAntetTrimiteri)

            Toast.makeText(this, "Codul este unul nou, completeaza campurile", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), TrimitereNouaDupaCompletareCodQR.class);
        intent.putExtra("UserPL",getIntent().getExtras().getString("UserPL"));
        String ag = EditTextCodQR.getText().toString().trim();
//        if(ag.length() != 0){
//            StocareCodBare.add(ag);
        //insertData();
//            db.beginTransaction();
//        String SqlSir = "Insert into " + Constructor.Tabela_Antet_Trimiteri.NUME_TABEL + " (" + Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE + "," + Constructor.Tabela_Antet_Trimiteri.COL_ID_UTILIZATOR + ") " + "Values"
 //               + " (" + EditTextCodQR.getText().toString() + "," + getIntent().getExtras().getString("UTILIZATOR") + ")";

//                Cursor crs = db.rawQuery(SqlSir,null);
//                crs.moveToFirst();
 //               int idAT =(crs.getColumnIndexOrThrow(Constructor.Tabela_Antet_Trimiteri.COL_ID_ANTET_TRIMITERI));

//            db.execSQL(SqlSir);
//            db.endTransaction();
        //      }

        //intent.putExtra(EditTextCodQR.getText().toString(),"CodBare");
        intent.putExtra("CodBare",sCodBare);
        intent.putExtra("UTILIZATOR",getIntent().getExtras().getString("UTILIZATOR"));
        startActivity(intent);
        EditTextCodQR.setText("");
    }

    public boolean insertData() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();

        ContentValues contentValue = new ContentValues();
        contentValue.put(Constructor.Tabela_Antet_Trimiteri.COL_COD_BARE, EditTextCodQR.getText().toString());
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



