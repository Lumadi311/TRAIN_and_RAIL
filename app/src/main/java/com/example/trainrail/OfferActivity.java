package com.example.trainrail;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class OfferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        getSupportActionBar().setTitle("Offers Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
