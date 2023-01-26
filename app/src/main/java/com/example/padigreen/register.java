package com.example.padigreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    //Deklarasi variabel widget
    EditText ETNama, ETEmail, ETPass, ETRptPass;
    ImageButton IBIntent;
    Button BTNRegister;
    TextView TVLogin_now;
    String username, email, password, rptpass;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hubungkan widget di layout dengan java
        ETNama = findViewById(R.id.edt_nama);
        ETEmail = findViewById(R.id.edt_email);
        ETPass = findViewById(R.id.edt_pass);
        ETRptPass = findViewById(R.id.edt_rpt_pass);
        BTNRegister = findViewById(R.id.btn_register);
        TVLogin_now = findViewById(R.id.tv_login_now);

        progressDialog = new ProgressDialog(this);

        //pindah halaman
        BTNRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = ETNama.getText().toString();
                email = ETEmail.getText().toString();
                password = ETPass.getText().toString();
                rptpass = ETRptPass.getText().toString();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(register.this);
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

        TVLogin_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register.this, login.class));
            }
        });
    }

    void validasiData(){
        if(username.equals("") ||email.equals("") || password.equals("") || rptpass.equals("")){
            progressDialog.dismiss();
            Toast.makeText(register.this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }
    void kirimData(){
        AndroidNetworking.post("https://tkjbpnup.com/kelompok_3/api/register.php")
                .addBodyParameter("username",""+username)
                .addBodyParameter("email",""+email)
                .addBodyParameter("password",""+password)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan   = response.getString("result");
                            Toast.makeText(register.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(register.this)
                                        .setMessage("Berhasil!")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(register.this, login.class);
                                                startActivity(i);
                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(register.this)
                                        .setMessage("Gagal!")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(register.this, register.class);
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