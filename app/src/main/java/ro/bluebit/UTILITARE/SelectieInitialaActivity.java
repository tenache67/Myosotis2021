package ro.bluebit.UTILITARE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.bluebit.Adapters.RVAdapterMain;
import ro.bluebit.MainActivity;
import ro.bluebit.R;

public class SelectieInitialaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectieinitiala);
        Toolbar toolbarSimplu = findViewById(R.id.toolbarSimplu);
        setSupportActionBar(toolbarSimplu);
        toolbarSimplu.setSubtitle("Selecteaza o activitate:");

        ArrayList<ClasaIteme> listaIteme = new ArrayList<>();
        listaIteme.add(new ClasaIteme(R.drawable.ic_flight,"Trimitere noua"));
        listaIteme.add(new ClasaIteme(R.drawable.ic_scanare,"Informatii trimitere"));
        listaIteme.add(new ClasaIteme(R.drawable.ic_email,"Incarca trimiteri"));
        listaIteme.add(new ClasaIteme(R.drawable.ic_download,"Descarca trimiteri"));
        listaIteme.add(new ClasaIteme(R.drawable.ic_download,"Test MYSQL"));


        mRecyclerView = findViewById(R.id.recycler_activitate1_id);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RVAdapterMain(listaIteme,this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        Bundle extras = getIntent().getExtras();
        int id_pct_lucru_initial = extras.getInt("ID_P_LUCRU");// marian - am adaugat id punt lucru initial pentru inserare in tabela incarc-descarc
        String idUtilizator = extras.getString("UTILIZATOR");



    }

    // iesire din program cu apsare dubla pe back
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Apasa BACK din nou pentru a iesi", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.meniu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.iesire:
                Intent startAplicatie = new Intent(this, MainActivity.class);
                startActivity(startAplicatie);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

