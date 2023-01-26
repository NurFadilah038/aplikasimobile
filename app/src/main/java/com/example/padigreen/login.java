package com.example.padigreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;


public class login extends AppCompatActivity {

    String username, password;
    EditText ETNama, ETPass;
    CheckBox CBCheckbox;
    Button BTNLogin;
    TextView BTNRegister_now;
    SessionManager sessionManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set variable sesuai dengan widget yang digunakan
        progressDialog = new ProgressDialog(this);
        ETNama = findViewById(R.id.edt_nama);
        ETPass = findViewById(R.id.edt_pass);
        BTNLogin = findViewById(R.id.btn_login);
        CBCheckbox = findViewById(R.id.checkbox);
        BTNRegister_now = findViewById(R.id.btn_registernow);
        progressDialog = new ProgressDialog(this);

        sessionManager = new SessionManager(getApplicationContext());

        //pindah halaman
        BTNLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username    = ETNama.getText().toString();
                password    = ETPass.getText().toString();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(login.this);
                alertDialogBuilder.setMessage("Do yo want to submit your data ?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        validasiData();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        BTNRegister_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,register.class));
            }
        });
    }
    void validasiData(){
        if(username.equals("") ||password.equals("")){
            progressDialog.dismiss();
            Toast.makeText(login.this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }
    void kirimData(){
        AndroidNetworking.post("https://tkjbpnup.com/kelompok_3/api/login.php")
                .addBodyParameter("username",""+username)
                .addBodyParameter("password",""+password)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekLogin",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan   = response.getString("result");
                            String username = response.getString("username");
                            String email = response.getString("email");
                            String tanggal_lahir = response.getString("tanggal_lahir");
                            String id = response.getString("id");
                            String kamar = response.getString("kamar");
                            String telepon = response.getString("telepon");
                            String identitas = response.getString("identitas");
                            String alamat = response.getString("alamat");
                            String nama_rek = response.getString("nama_rek");
                            String nama_bank = response.getString("nama_bank");
                            String no_rek = response.getString("no_rek");
                            String photo = response.getString("photo");
                            Toast.makeText(login.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(login.this)
                                        .setMessage("Berhasil Login")
                                        .setCancelable(false)
                                        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                sessionManager.createSession_username(username);
                                                sessionManager.createSession_email(email);
                                                sessionManager.createSession_tanggal_lahir(tanggal_lahir);
                                                sessionManager.createSession_id(id);
                                                sessionManager.createSession_kamar(kamar);
                                                sessionManager.createSession_telepon(telepon);
                                                sessionManager.createSession_identitas(identitas);
                                                sessionManager.createSession_alamat(alamat);
                                                sessionManager.createSession_nama_rek(nama_rek);
                                                sessionManager.createSession_nama_bank(nama_bank);
                                                sessionManager.createSession_no_rek(no_rek);
                                                sessionManager.createSession_photo(photo);
                                                Intent intent = new Intent(login.this, dashboard.class);
                                                startActivity(intent);

                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(login.this)
                                        .setMessage("Gagal Mencocokkan Data !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(login.this, login.class);
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

}