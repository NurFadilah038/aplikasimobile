package com.example.padigreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class dashboard extends AppCompatActivity {

    ImageView IBDashboard, IBLaporan, IBFasilitas, IBAkun;
    TextView TVNama_user, TVPengumuman, IBDeposit, IBSewa, TVKamar;
    CardView CVPengumuman, CVReport;
    CircleImageView CIVprofile;
    String strTitle, username, email, kamar, photo;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        IBImage_profile = findViewById(R.id.image_profile);
        IBDeposit = findViewById(R.id.deposit);
        IBSewa = findViewById(R.id.sewa);
        IBDashboard = findViewById(R.id.dashboard);
        IBLaporan = findViewById(R.id.laporan);
        IBFasilitas = findViewById(R.id.fasilitas);
        IBAkun = findViewById(R.id.profile);
        CIVprofile = findViewById(R.id.idIVGambar);
        TVKamar = findViewById(R.id.TVKamar);
        TVNama_user = findViewById(R.id.nama_user);
        TVPengumuman = findViewById(R.id.pengumuman);
        CVPengumuman = findViewById(R.id.CVPengumuman);
        CVReport = findViewById(R.id.CVReport);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        username = user.get(SessionManager.KEY_USERNAME);
        kamar = user.get(SessionManager.KEY_KAMAR);
        photo = user.get(SessionManager.KEY_PHOTO);


        if(username != null){
            TVNama_user.setText(username);
            TVKamar.setText(kamar);
            Picasso.get().load("https://tkjbpnup.com/kelompok_3/image/"+photo).into(CIVprofile);
        }


        //set value ke dalam edit text
       // TVNama_user.setText(username);

        IBDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,dashboard.class));
            }
        });
        IBLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,laporan.class));
            }
        });
        IBFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,fasilitas.class));
            }
        });
//        IBProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(dashboard.this,profile.class));
//            }
//        });
        CVPengumuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,pengumuman.class));
            }
        });
        CVReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,laporan.class));
            }
        });
        IBAkun.setOnClickListener(v -> {
            strTitle = "Profile";
            Intent intent = new Intent(dashboard.this, akun.class);
            intent.putExtra(profile.DATA_TITLE, strTitle);
            startActivity(intent);
        });
    }

}