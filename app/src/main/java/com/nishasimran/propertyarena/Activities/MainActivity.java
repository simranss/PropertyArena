package com.nishasimran.propertyarena.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.nishasimran.propertyarena.Fragments.ClientsFragment;
import com.nishasimran.propertyarena.Fragments.DeveloperFragment;
import com.nishasimran.propertyarena.Fragments.SectorFragment;
import com.nishasimran.propertyarena.Fragments.ZoneFragment;
import com.nishasimran.propertyarena.R;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private FloatingActionButton fab, addProjectFab, addClientFab;
    private boolean isOpen = false;
    private Animation fabOpen, fabClose, fabRClockwise, fabRAnticlockwise;

    // fragments
    private Fragment developerFragment, zoneFragment, sectorFragment, clientsFragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize the drawer layout
        drawerLayout = findViewById(R.id.drawer);
        NavigationView navView = findViewById(R.id.nav_view);

        fab = findViewById(R.id.add_fab);
        addProjectFab = findViewById(R.id.add_project_fab);
        addClientFab = findViewById(R.id.add_client_fab);

        // fragments
        developerFragment = new DeveloperFragment(this);
        zoneFragment = new ZoneFragment(this);
        sectorFragment = new SectorFragment(this);
        clientsFragment = new ClientsFragment(this);

        // by default load the splash fragment
        if (savedInstanceState == null) {
            loadFragment(developerFragment);
        }

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRAnticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        fab.setOnClickListener(v -> {
            if (isOpen) {
                addProjectFab.startAnimation(fabClose);
                addClientFab.startAnimation(fabClose);
                fab.startAnimation(fabRAnticlockwise);
                addProjectFab.setClickable(false);
                addClientFab.setClickable(false);
                isOpen = false;
            } else {
                addProjectFab.startAnimation(fabOpen);
                addClientFab.startAnimation(fabOpen);
                fab.startAnimation(fabRClockwise);
                addProjectFab.setClickable(true);
                addClientFab.setClickable(true);
                isOpen = true;
            }
        });

        addProjectFab.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddProjectActivity.class));
            addProjectFab.startAnimation(fabClose);
            addClientFab.startAnimation(fabClose);
            fab.startAnimation(fabRAnticlockwise);
            addProjectFab.setClickable(false);
            addClientFab.setClickable(false);
            isOpen = false;
        });
        addClientFab.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddClientActivity.class));
            addProjectFab.startAnimation(fabClose);
            addClientFab.startAnimation(fabClose);
            fab.startAnimation(fabRAnticlockwise);
            addProjectFab.setClickable(false);
            addClientFab.setClickable(false);
            isOpen = false;
        });

        // navigation drawer items onClick
        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_developer:
                    Log.d(TAG, "developer clicked");
                    loadFragment(developerFragment);
                    item.setChecked(!item.isChecked());
                    item.setChecked(true);
                    break;
                case R.id.menu_zone:
                    Log.d(TAG, "zone clicked");
                    loadFragment(zoneFragment);
                    item.setChecked(!item.isChecked());
                    item.setChecked(true);
                    break;
                case R.id.menu_sector:
                    Log.d(TAG, "sector clicked");
                    loadFragment(sectorFragment);
                    item.setChecked(!item.isChecked());
                    item.setChecked(true);
                    break;
                case R.id.menu_client:
                    Log.d(TAG, "clients clicked");
                    loadFragment(clientsFragment);
                    item.setChecked(!item.isChecked());
                    item.setChecked(true);
                    break;
                default:
                    Log.d(TAG, "default selected");
                    loadFragment(developerFragment);
            }

            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });

        // hamburger icon animation for navigation drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // load a fragment
    void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    // manage the back button press
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}