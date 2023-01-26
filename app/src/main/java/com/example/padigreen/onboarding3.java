package com.example.padigreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class onboarding3 extends AppCompatActivity {

    //Deklarasi variabel widget
    TextView TVLogin, TVRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding3);
        TVLogin = findViewById(R.id.login);
        TVRegister = findViewById(R.id.register);
        TVLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(onboarding3.this,login.class));
            }
        });
        TVRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(onboarding3.this,register.class));
            }
        });
    }
}