package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class Intro extends AppCompatActivity implements View.OnClickListener{

    ViewFlipper viewFlipper;
    Button Continue;
    SharedPreferences sharedPreferences;

    public static final String SHARED_PREF_NAME = "mypref";
    public static final String KEY_ID = "id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewFlipper = findViewById(R.id.viewFlipper);
        Continue = findViewById(R.id.Continue);
        Continue.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String ID = sharedPreferences.getString(KEY_ID,null);

        if(ID != null){
            LogInSection.ID = ID;
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        }

        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();


    }
    @Override
    public void onClick(View v){

        if(v == Continue){

            Intent intent = new Intent(this, LogInSection.class);
            startActivity(intent);
            this.finish();


        }

    }
}