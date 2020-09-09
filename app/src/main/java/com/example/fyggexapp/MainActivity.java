package com.example.fyggexapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newsBtn = findViewById(R.id.news_btn);
        Button ratesBtn = findViewById(R.id.rates_btn);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new NewsFragment()).commit();

        newsBtn.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
            fragmentTransaction1.replace(R.id.frame_layout, new NewsFragment()).addToBackStack(null);
            fragmentTransaction1.commit();
        });

        ratesBtn.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction12 = fragmentManager.beginTransaction();
            fragmentTransaction12.replace(R.id.frame_layout, new RatesFragment()).addToBackStack(null);
            fragmentTransaction12.commit();
        });

    }

}