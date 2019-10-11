package ro.bluebit.UTILITARE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import ro.bluebit.Adapters.RVAdapterMain;
import ro.bluebit.R;

public class SelectieInitialaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectieinitiala);

        ArrayList<ClasaIteme> listaIteme = new ArrayList<>();
        listaIteme.add(new ClasaIteme(R.drawable.ic_flight,"Trimitere noua"));
        listaIteme.add(new ClasaIteme(R.drawable.ic_scanare,"Informatii trimitere"));
        listaIteme.add(new ClasaIteme(R.drawable.ic_email,"Incarca trimiteri"));
        listaIteme.add(new ClasaIteme(R.drawable.ic_download,"Descarca trimiteri"));


        mRecyclerView = findViewById(R.id.recycler_activitate1_id);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RVAdapterMain(listaIteme);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pentru iesire foloseste butonul de logout", Toast.LENGTH_SHORT).show();
    }
}

