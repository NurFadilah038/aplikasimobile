package com.example.padigreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class akun extends AppCompatActivity {
    TextView ETEdit_profile, ETLogout;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        ETEdit_profile = findViewById(R.id.ETEdit_profile);
        ETLogout = findViewById(R.id.ETLogout);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();

        ETEdit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(akun.this, profile.class));
            }
        });
        ETLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logOut();
                Toast.makeText(akun.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }
}