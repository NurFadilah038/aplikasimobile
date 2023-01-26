package com.example.padigreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class profile extends AppCompatActivity {

    public static final String DATA_TITLE = "TITLE";
    String username, email, kamar, telepon, tanggal_lahir, identitas, alamat, nama_rek, nama_bank, no_rek, id;
    String photo;
    de.hdodenhof.circleimageview.CircleImageView IBPhoto;
    TextView TVNama_user, TVKamar;
    TextView TVTitle;
    ExtendedFloatingActionButton FBEdit;
    com.rengwuxian.materialedittext.MaterialEditText ETKamar, ETTelepon, ETEmail, ETTgl_lahir, ETIdentitas, ETAlamat, ETNama_rek, ETBank, ETRekening;
    androidx.core.widget.NestedScrollView scroll;
    SessionManager sessionManager;
    ProgressDialog progressDialog;

    String pilihan;
    //private static final int PHOTO_REQUEST_GALLERY = 1;//gallery
    static final int REQUEST_TAKE_PHOTO = 1;
    final int CODE_GALLERY_REQUEST = 999;
    String rPilihan[]= {"Take a photo","From Album"};
    public final String APP_TAG = "MyApp";
    Bitmap bitMap = null;
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        IBPhoto = findViewById(R.id.IB);
        TVNama_user = findViewById(R.id.TVNama_user);
        TVKamar = findViewById(R.id.TVKamar);
        ETKamar = findViewById(R.id.ETKamar);
        ETTelepon = findViewById(R.id.ETTelepon);
        ETEmail = findViewById(R.id.ETEmail);
        ETTgl_lahir = findViewById(R.id.ETTgl_lahir);
        ETIdentitas = findViewById(R.id.ETIdentitas);
        ETAlamat = findViewById(R.id.ETAlamat);
        ETNama_rek = findViewById(R.id.ETNama_rek);
        ETBank = findViewById(R.id.ETBank);
        ETRekening = findViewById(R.id.ETRekening);
        TVTitle = findViewById(R.id.TVTitle);
        FBEdit  = findViewById(R.id.FBEdit);
        //scroll  = findViewById(R.id.scroll);


        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        username = user.get(SessionManager.KEY_USERNAME);
        email = user.get(SessionManager.KEY_EMAIL);
        tanggal_lahir = user.get(SessionManager.KEY_TANGGAL_LAHIR);
        kamar = user.get(SessionManager.KEY_KAMAR);
        telepon = user.get(SessionManager.KEY_TELEPON);
        identitas = user.get(SessionManager.KEY_IDENTITAS);
        alamat = user.get(SessionManager.KEY_ALAMAT);
        nama_rek = user.get(SessionManager.KEY_NAMA_REK);
        nama_bank = user.get(SessionManager.KEY_NAMA_BANK);
        no_rek = user.get(SessionManager.KEY_NO_REK);
        photo = user.get(SessionManager.KEY_PHOTO);
        id = user.get(SessionManager.KEY_ID);

        //set value ke dalam edit text
        TVNama_user.setText(username);
        ETEmail.setText(email);
        ETTgl_lahir.setText(tanggal_lahir);
        ETKamar.setText(kamar);
        ETTelepon.setText(telepon);
        ETIdentitas.setText(identitas);
        ETAlamat.setText(alamat);
        ETNama_rek.setText(nama_rek);
        ETBank.setText(nama_bank);
        ETRekening.setText(no_rek);
        Picasso.get().load("https://tkjbpnup.com/kelompok_3/image/"+photo).into(IBPhoto);

        progressDialog = new ProgressDialog(this);

        IBPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bitMap != null) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(profile.this);
                    alertDialogBuilder.setMessage("Do yo want to take photo again?");

                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Toast.makeText(context,"You clicked yes button",Toast.LENGTH_LONG).show();
                            //call fuction of TakePhoto
                            TakePhoto();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                } else {
                    TakePhoto();
                }
            }
        });

        FBEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Updating Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                kamar     = ETKamar.getText().toString();
                telepon    = ETTelepon.getText().toString();
                email    = ETEmail.getText().toString();
                tanggal_lahir = ETTgl_lahir.getText().toString();
                identitas = ETIdentitas.getText().toString();
                alamat     = ETAlamat.getText().toString();
                nama_rek    = ETNama_rek.getText().toString();
                nama_bank = ETBank.getText().toString();
                no_rek = ETRekening.getText().toString();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        validasiData();
                    }
                },1000);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    void validasiData(){
        if(kamar.equals("") ||telepon.equals("") || tanggal_lahir.equals("") || identitas.equals("") ){
            progressDialog.dismiss();
            Toast.makeText(profile.this, "Mohon lengkapi data!", Toast.LENGTH_SHORT).show();
        }else {
            updateData();
        }
    }

    // taking image
    public  void TakePhoto(){
        // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        AlertDialog.Builder builder = new AlertDialog.Builder(profile.this);
        builder.setTitle("Select");
        builder.setItems(rPilihan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                pilihan = String.valueOf(rPilihan[which]);

                if (pilihan.equals("Take a photo"))
                {
                    // create Intent to take a picture and return control to the calling application
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Create a File reference to access to future access
                    photoFile = getPhotoFileUri(photoFileName);

                    // wrap File object into a content provider
                    // required for API >= 24
                    // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                    String authorities = getPackageName() + ".fileprovider";
                    Uri fileProvider = FileProvider.getUriForFile(profile.this, authorities, photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                    // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                    // So as long as the result is not null, it's safe to use the intent.
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Start the image capture intent to take photo
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);

                    }
                }
                else
                {

                    ActivityCompat.requestPermissions(profile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_GALLERY_REQUEST);

                }
            }
        });
        builder.show();


    }

    //permission
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }else{
                Toast.makeText(getApplicationContext(), "You don't have permission to access gallery!", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //set photo size
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                // by this point we have the camera photo on disk
                //Bitmap takenImage = BitmapFactory.decodeFile(String.valueOf(photoFile));
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                bitMap = decodeSampledBitmapFromFile(String.valueOf(photoFile), 1000, 700);
                IBPhoto.setImageBitmap(bitMap);
            } else { // Result was a failure
                Toast.makeText(profile.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (intent != null) {
                Uri photoUri = intent.getData();
                // Do something with the photo based on Uri
                bitMap = null;
                try {
                    //InputStream inputStream = getContentResolver().openInputStream(photoUri);
                    //bitMap = BitmapFactory.decodeStream(inputStream);

                    //btnImage.setVisibility(View.VISIBLE);
                    bitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Load the selected image into a preview
                IBPhoto.setImageBitmap(bitMap);
            }
        }
    }

    //get data photo
    public File getPhotoFileUri(String fileName)  {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use getExternalFilesDir on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

            return file;

        }
        return null;
    }

    //set photo
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    // get encode image to minimize image
    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    void updateData(){
        String photo = getStringImage(bitMap);
        AndroidNetworking.post("http://tkjbpnup.com/kelompok_3/api/editProfile.php")
                .addBodyParameter("id",""+id)
                .addBodyParameter("kamar",""+kamar)
                .addBodyParameter("telepon",""+telepon)
                .addBodyParameter("email",""+email)
                .addBodyParameter("tanggal_lahir",""+tanggal_lahir)
                .addBodyParameter("identitas",""+identitas)
                .addBodyParameter("alamat",""+alamat)
                .addBodyParameter("nama_rek",""+nama_rek)
                .addBodyParameter("nama_bank",""+nama_bank)
                .addBodyParameter("no_rek",""+no_rek)
                .addBodyParameter("photo",""+photo)
                .setPriority(Priority.MEDIUM)
                .setTag("Update Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekUpdate",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(profile.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(profile.this)
                                        .setMessage("Anda berhasil mengupdate data !")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Intent i = new Intent(edit_fasilitas.this, fasilitas.class);
                                                //startActivity(i);
                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(profile.this)
                                        .setMessage("Gagal Menambahkan Data !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Intent i = new Intent(edit_fasilitas.this, fasilitas.class);
                                                //startActivity(i);
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
                        Log.d("Tidak dapat memperbarui data Anda",""+anError.getErrorBody());
                    }
                });
    }

}