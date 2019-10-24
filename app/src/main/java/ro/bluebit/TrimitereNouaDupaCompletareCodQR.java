package ro.bluebit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class TrimitereNouaDupaCompletareCodQR extends AppCompatActivity {
        Spinner Priotitate,Cond_Speciale;
        AutoCompleteTextView Expeditor,Destinatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimitere_noua_dupa_completare_cod_qr);

        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Activitate Trimitere Noua");
        Expeditor = findViewById(R.id.ac_expeditor_id);
        Destinatar = findViewById(R.id.ac_destinatar_id);

        PopulareSpinner(); // Metoda de populare a spinnerelor
        PopulareAutocomplete();
    }



    public void PopulareSpinner(){
        List<String> spinnerArrayConditii =  new ArrayList<String>(); // Spinner pentru conditii speciale
        spinnerArrayConditii.add("NU");
        spinnerArrayConditii.add("DA");

        ArrayAdapter<String> adapterconditii = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayConditii);

        adapterconditii.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItemsConditii = findViewById(R.id.spinner_condspec_id);
        sItemsConditii.setAdapter(adapterconditii);

        List<String> spinnerArray =  new ArrayList<String>(); // Spinner pentru prioritate
        spinnerArray.add("NORMAL");
        spinnerArray.add("PRIORITAR");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = findViewById(R.id.spinner_prioritate_id);
        sItems.setAdapter(adapter);
    }

    public void PopulareAutocomplete(){
          final String[]Expeditor_Destinatar = new String[]{
                "Depozit", "Birou", "Farmacie", "Contabilitate", "M1", "M2", "M3","M4", "M5", "M6"
        };
          ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Expeditor_Destinatar);
          Expeditor.setAdapter(adapter);
          Destinatar.setAdapter(adapter);
    }
}
