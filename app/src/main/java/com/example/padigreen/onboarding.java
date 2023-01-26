package com.example.padigreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class onboarding extends AppCompatActivity {

    //Deklarasi variabel widget
    TextView TVSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        //Proses menghubungkan widget pada xml ke java
        TVSkip = findViewById(R.id.skip);
        TVSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(onboarding.this,onboarding2.class));
            }
        });
    }
}