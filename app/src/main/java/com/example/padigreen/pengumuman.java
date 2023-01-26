package com.example.padigreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class pengumuman extends AppCompatActivity {

    TextView TVPengumuman;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengumuman);

        TVPengumuman = findViewById(R.id.pengumuman);
    }
}