package com.example.pmsuprojekat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.pmsuprojekat.activities.DBHelper;
import com.example.pmsuprojekat.activities.LoginActivity;
import com.example.pmsuprojekat.activities.ResavanjePorudzbina;
import com.example.pmsuprojekat.activities.SharedPreferenceConfig;
import com.example.pmsuprojekat.activities.SviKorisniciActivity;
import com.example.pmsuprojekat.adapters.DrawerListAdapter;
import java.util.ArrayList;
import java.util.List;
import model.Korisnik;
import model.NavItem;

public class MainActivityAdministrator extends AppCompatActivity {

    private SharedPreferenceConfig sharedPreferenceConfig;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mDrawerPane;
    private CharSequence mTitle;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private Spinner spinner;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrator);

        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        DB = new DBHelper(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("user");

        prepareMenu(mNavItems);

        mTitle = getTitle();
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerList = findViewById(R.id.navList);

        mDrawerPane = findViewById(R.id.drawerPane);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setOnItemClickListener(new MainActivityAdministrator.DrawerItemClickListener());
        mDrawerList.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();


        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("iReviewer");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Izborom na neki element iz liste, pokrecemo akciju
        if (savedInstanceState == null) {
            selectItemFromDrawer(0);
        }

        spinner = findViewById(R.id.aSpinnerToolBar);

        List<String> categories = new ArrayList<>();
        categories.add(0, "Izaberi");
        categories.add("Svi korisnici");
        categories.add("Resi porudzbine");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,categories);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Izaberi")) {
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                    if(parent.getItemAtPosition(position).equals("Svi korisnici"))
                    {
                        Intent intent = new Intent(MainActivityAdministrator.this, SviKorisniciActivity.class);
                        startActivity(intent);
                    }
                    if(parent.getItemAtPosition(position).equals("Resi porudzbine"))
                    {
                        Intent intent = new Intent(MainActivityAdministrator.this, ResavanjePorudzbina.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private void prepareMenu(ArrayList<NavItem> mNavItems) {
        SharedPreferences prefs = getSharedPreferences("My pref",MODE_PRIVATE);
        String userName = prefs.getString("userName", "No name defined");//"No name defined" is the default value.
        Korisnik korisnik = DB.findKorisnik(userName);
        if(userName != null) {
            mNavItems.add(new NavItem(userName, korisnik.getUloga(), R.drawable.ic_action_username));
        }
        else{
            mNavItems.add(new NavItem("You are logged out", "Logged out", R.drawable.ic_action_username));

        }
        mNavItems.add(new NavItem(getString(R.string.Location),getString(R.string.FindUs), R.drawable.ic_action_username));
        mNavItems.add(new NavItem(getString(R.string.about), getString(R.string.about_long), R.drawable.ic_action_username));
        mNavItems.add(new NavItem(getString(R.string.logOut), getString(R.string.logOut), R.drawable.ic_action_username));
    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.layout.menu, menu);
        return true;
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }

    private void selectItemFromDrawer(int position) {
        if (position == 0) {

        } else if (position == 1) {
            //..
        } else if (position == 2) {
            //..
        } else if (position == 3) {
            sharedPreferenceConfig.login_status(false);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (position == 4) {
            //..
        } else if (position == 5) {
            //...
        } else {
            Log.e("DRAWER", "Nesto van opsega!");
        }

        mDrawerList.setItemChecked(position, true);
        if (position != 5) // za sve osim za sync
        {
            setTitle(mNavItems.get(position).getmTitle());
        }
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public void setTitle(CharSequence title) {
        /*mTitle = title;
        getSupportActionBar().setTitle(mTitle);*/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.logout){
            sharedPreferenceConfig.login_status(false);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return true;
    }
}