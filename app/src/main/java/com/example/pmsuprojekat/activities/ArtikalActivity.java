package com.example.pmsuprojekat.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pmsuprojekat.MainActivity;
import com.example.pmsuprojekat.R;
import com.example.pmsuprojekat.adapters.ArtikalAdapterClass;
import java.util.List;
import model.Artikal;

public class ArtikalActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnDodajArtikal;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artikli_list);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        btnDodajArtikal=findViewById(R.id.btn_dodajArtikal);
        DBHelper dbHelper = new DBHelper(this);
        Intent intent = getIntent();

        //SHAREDRPEFERENCES NACIN ---
        SharedPreferences prefs = getSharedPreferences("My pref",MODE_PRIVATE);
        int idProdavca = prefs.getInt("idProdavca", 0);
        String idProdavcaa = String.valueOf(idProdavca); //SHARED NACIN ---
        String usernameProdavca = prefs.getString("usernameProdavca", "No name defined");

        //INTENT NACIN ---
        String username = intent.getStringExtra("user");
        String prodavacId = String.valueOf(intent.getIntExtra("idProdavca",0));//OVO

        //Dobavljanje artikala pojedinacnog prodavca
        List<Artikal> artikli = dbHelper.getArtikliProdavca(idProdavcaa);

        if(artikli.size() > 0){
            ArtikalAdapterClass artikalAdapterClass = new ArtikalAdapterClass(artikli,ArtikalActivity.this);
            recyclerView.setAdapter(artikalAdapterClass);
        }else{
            Toast.makeText(ArtikalActivity.this, "Nema artikala u bazi podataka!", Toast.LENGTH_SHORT).show();

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();


        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }

        btnDodajArtikal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prodavacId = idProdavca;
                Intent myIntent = new Intent(v.getContext(), NoviArtikalActivity.class);
                myIntent.putExtra("id", prodavacId);
                v.getContext().startActivity(myIntent);
            }
        });

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.share){
            Intent intent = new Intent(ArtikalActivity.this, MainActivity.class);
            SharedPreferences prefs = getSharedPreferences("My pref",MODE_PRIVATE);

            String usernameProdavca = prefs.getString("userName", "No name defined");
            SharedPreferences.Editor editor = getSharedPreferences("My pref", MODE_PRIVATE).edit();
            editor.putString("usernameProdavca", usernameProdavca);
            editor.apply();

            startActivity(intent);
            finish();
        }
        if(id == R.id.logout){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return true;
    }

}

