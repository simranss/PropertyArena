package com.nishasimran.propertyarena.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nishasimran.propertyarena.R;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashAct";

    FirebaseAuth auth = FirebaseAuth.getInstance();

    private Long startMillis;
    private final int TIME_OUT = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startMillis = System.currentTimeMillis();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // user logged in
            Log.d(TAG, "user logged in");
            startScreen(MainActivity.class);
        } else {
            // user not logged in
            Log.d(TAG, "user not logged in");
            startScreen(LoginActivity.class);
        }
    }

    private void startScreen(final Class<?> classToStart) {
        Intent intent = new Intent(SplashActivity.this, classToStart);
        long leftMillis = System.currentTimeMillis() - startMillis;
        Log.d(TAG, "leftMillis: " + leftMillis);
        if (leftMillis <= TIME_OUT) {
            new Handler().postDelayed(() -> {
                startActivity(intent);
                finish();
            }, TIME_OUT - leftMillis);
        } else {
            startActivity(intent);
            finish();
        }
    }
}