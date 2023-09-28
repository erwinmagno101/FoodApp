package com.example.foodapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInSection extends AppCompatActivity implements View.OnClickListener {

    Button btnSignUp, btnLogIn, btnShowlog, btnProceed;
    EditText email, password;
    DBHelper DB;
    public static String ID;
    Boolean showpass;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_section);

        btnProceed = findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(this);

        btnSignUp = findViewById(R.id.btnSignUp1);
        btnSignUp.setOnClickListener(this);

        btnLogIn = findViewById(R.id.btnLogIn1);
        btnLogIn.setOnClickListener(this);

        btnShowlog = findViewById(R.id.btnShowlog);
        btnShowlog.setOnClickListener(this);
        showpass = false;

        email = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.LoginPassword);

        DB = new DBHelper(this);
        sharedPreferences = getSharedPreferences(Intro.SHARED_PREF_NAME, MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {

        if(v == btnSignUp){
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
            this.finish();
        }
        if(v == btnLogIn){
            String Email = email.getText().toString();
            String Pass = password.getText().toString();
            if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Pass)){
                Toast.makeText(LogInSection.this, "All Fields Required", Toast.LENGTH_SHORT).show();
            }else{
                Boolean checkemailpass = DB.checkEmailPassword(Email, Pass);
                if (checkemailpass == true){
                    Toast.makeText(LogInSection.this, "LogIn Successfully", Toast.LENGTH_SHORT).show();
                    ID = Email;

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Intro.KEY_ID, Email);
                    editor.apply();

                    Intent intent = new Intent(this, Menu.class);
                    startActivity(intent);
                    this.finish();
                }else{
                    Toast.makeText(LogInSection.this, "LogIn Failed! Incorrect Email/Password", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (v == btnShowlog){

            if (showpass){
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                password.setTypeface(null, Typeface.BOLD);
                Drawable img = this.getResources().getDrawable(R.drawable.ic_baseline_visibility_24);
                DrawableCompat.setTint(img, Color.rgb(102, 85, 60));
                btnShowlog.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                showpass = false;
            }else {
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
                password.setTypeface(null, Typeface.BOLD);
                Drawable img = this.getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24);
                DrawableCompat.setTint(img, Color.rgb(102, 85, 60));
                btnShowlog.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                showpass = true;
            }
            password.setSelection(password.getText().length());

        }

        if (v == btnProceed){
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
            this.finish();
        }
    }
    @Override
    public void onBackPressed(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finishAffinity();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit??").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

}