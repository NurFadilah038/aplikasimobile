package com.example.padigreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class history extends AppCompatActivity {

    SwipeRefreshLayout srl_main;
    String id;
    ArrayList<String> array_id,array_nama_pelapor,array_kamar,array_tanggal,array_isi_laporan,array_kategori,array_photo;
    ProgressDialog progressDialog;
    ListView listProses;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //set variable sesuai dengan widget yang digunakan
        listProses = findViewById(R.id.LV);
        srl_main    = findViewById(R.id.swipe_container);
        progressDialog = new ProgressDialog(this);

        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollRefresh();
                srl_main.setRefreshing(false);
            }
        });
        // Scheme colors for animation
        srl_main.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)

        );

        scrollRefresh();
    }

    public void scrollRefresh(){
        progressDialog.setMessage("Mengambil Data.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        },2000);
    }

    void initializeArray(){
        array_id             = new ArrayList<String>();
        array_nama_pelapor   = new ArrayList<String>();
        array_kamar          = new ArrayList<String>();
        array_tanggal        = new ArrayList<String>();
        array_isi_laporan    = new ArrayList<String>();
        array_kategori       = new ArrayList<String>();
        array_photo          = new ArrayList<String>();

        //clear ini untuk memastikan data empty
        array_id.clear();
        array_nama_pelapor.clear();
        array_kamar.clear();
        array_tanggal.clear();
        array_isi_laporan.clear();
        array_kategori.clear();
        array_photo.clear();

    }

    public void getData(){
        initializeArray();
        //URL API
        AndroidNetworking.get("https://tkjbpnup.com/kelompok_3/api/getDataLaporan.php")
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        try{
                            Boolean status = response.getBoolean("status");
                            if(status){
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon",""+ja);
                                for(int i = 0 ; i < ja.length() ; i++){
                                    JSONObject jo = ja.getJSONObject(i);

                                    array_id.add(jo.getString("id"));
                                    array_nama_pelapor.add(jo.getString("nama_pelapor"));
                                    array_kamar.add(jo.getString("kamar"));
                                    array_tanggal.add(jo.getString("tanggal"));
                                    array_isi_laporan.add(jo.getString("isi_laporan"));
                                    array_kategori.add(jo.getString("kategori"));
                                    array_photo.add(jo.getString("photo"));

                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final CLV_DataLaporan adapter = new CLV_DataLaporan(history.this,array_id,array_nama_pelapor,array_kamar,array_tanggal,array_isi_laporan,array_kategori,array_photo);

                                //Set adapter to list
                                listProses.setAdapter(adapter);


                            }else{
                                Toast.makeText(history.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();

                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_delete){
            hapusData();
        }
        return super.onOptionsItemSelected(item);
    }

    void hapusData(){
        AndroidNetworking.post("https://tkjbpnup.com/kelompok_3/api/hapusHistory.php")
                .addBodyParameter("id",""+id)
                .setPriority(Priority.MEDIUM)
                .setTag("Hapus Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekHapus",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan   = response.getString("result");
                            Toast.makeText(history.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(history.this)
                                        .setMessage("Berhasil Menghapus Data ANda!")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(history.this, history.class);
                                                startActivity(i);
                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(history.this)
                                        .setMessage("Gagal Menghapus Data ANda!")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(history.this, history.class);
                                                startActivity(i);
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                scrollRefresh();
            }else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }

        if(requestCode==2){
            if(resultCode==RESULT_OK){
                scrollRefresh();
            }else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}