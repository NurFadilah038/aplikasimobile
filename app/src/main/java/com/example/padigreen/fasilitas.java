package com.example.padigreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.app.ProgressDialog;
import android.content.Context;
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



public class fasilitas extends AppCompatActivity {

    SwipeRefreshLayout srl_main;
    ArrayList<String> array_id,array_nama_fasilitas,array_jumlah,array_status,array_photo;
    ProgressDialog progressDialog;
    ListView listProses;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas);

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
        array_nama_fasilitas = new ArrayList<String>();
        array_jumlah         = new ArrayList<String>();
        array_status         = new ArrayList<String>();
        array_photo          = new ArrayList<String>();

        //clear ini untuk memastikan data empty
        array_id.clear();
        array_nama_fasilitas.clear();
        array_jumlah.clear();
        array_status.clear();
        array_photo.clear();
    }

    public void getData(){
        initializeArray();
        //URL API
        AndroidNetworking.get("https://tkjbpnup.com/kelompok_3/api/getData.php")
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
                                    array_nama_fasilitas.add(jo.getString("nama_fasilitas"));
                                    array_jumlah.add(jo.getString("jumlah"));
                                    array_status.add(jo.getString("status"));
                                    array_photo.add(jo.getString("photo"));

                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final CLV_DataUser adapter = new CLV_DataUser(fasilitas.this,array_id,array_nama_fasilitas,array_jumlah,array_status,array_photo);

                                //Set adapter to list
                                listProses.setAdapter(adapter);

                                //edit and delete
                                listProses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("TestKlik",""+array_nama_fasilitas.get(position));
                                        Toast.makeText(fasilitas.this, array_nama_fasilitas.get(position), Toast.LENGTH_SHORT).show();

                                        //Setelah proses koneksi keserver selesai, maka aplikasi akan berpindah class
                                        //DataActivity.class dan membawa/mengirim data-data hasil query dari server.
                                        Intent i = new Intent(fasilitas.this, edit_fasilitas.class);
                                        i.putExtra("id",array_id.get(position));
                                        i.putExtra("nama_fasilitas",array_nama_fasilitas.get(position));
                                        i.putExtra("jumlah",array_jumlah.get(position));
                                        i.putExtra("status",array_status.get(position));
                                        i.putExtra("photo",array_photo.get(position));
                                        startActivity(i);
                                    }
                                });


                            }else{
                                Toast.makeText(fasilitas.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();

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
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_add){
            Intent i = new Intent(fasilitas.this,add_fasilitas.class);
            startActivityForResult(i,1);
        }
        return super.onOptionsItemSelected(item);
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
