package com.example.padigreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class laporan extends AppCompatActivity {

    CardView CVHousekeeper,CVMaintenance,CVHistory;
    String strTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        CVHousekeeper = findViewById(R.id.CVHousekeeper);
        CVMaintenance = findViewById(R.id.CVMaintenance);
        CVHistory = findViewById(R.id.CVHistory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CVHousekeeper.setOnClickListener(v -> {
            strTitle = "Laporan Housekeeper";
            Intent intent = new Intent(laporan.this, report.class);
            intent.putExtra(report.DATA_TITLE, strTitle);
            startActivity(intent);
        });

        CVMaintenance.setOnClickListener(v -> {
            strTitle = "Laporan Maintenance Staff";
            Intent intent = new Intent(laporan.this, report.class);
            intent.putExtra(report.DATA_TITLE, strTitle);
            startActivity(intent);
        });

        CVHistory.setOnClickListener(v -> {
            Intent intent = new Intent(laporan.this, history.class);
            startActivity(intent);
        });

    }
}