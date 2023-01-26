package com.example.padigreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    //session names
    private static final String pref_name = "session";

    private static final String is_login = "islogin";

    public static final String KEY_PHOTO = "photo";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TANGGAL_LAHIR = "tanggal_lahir";
    public static final String KEY_ID = "id";
    public static final String KEY_KAMAR = "kamar";
    public static final String KEY_TELEPON = "telepon";
    public static final String KEY_IDENTITAS = "identitas";
    public static final String KEY_ALAMAT = "alamat";
    public static final String KEY_NAMA_REK = "nama_rek";
    public static final String KEY_NAMA_BANK = "nama_bank";
    public static final String KEY_NO_REK = "no_rek";

    public SessionManager (Context context){
        this.context = context;
        //pref = context.getActivity().getSharedPreferences(pref_name, mode);
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSession_photo(String photo){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_PHOTO, photo);
        editor.apply();
    }

    public void createSession_username(String username){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public void createSession_email(String email){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public void createSession_tanggal_lahir(String tanggal_lahir){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_TANGGAL_LAHIR, tanggal_lahir);
        editor.apply();
    }

    public void createSession_id(String id){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_ID, id);
        editor.apply();
    }

    public void createSession_kamar(String kamar){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_KAMAR, kamar);
        editor.apply();
    }

    public void createSession_telepon(String telepon){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_TELEPON, telepon);
        editor.apply();
    }

    public void createSession_identitas(String identitas){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_IDENTITAS, identitas);
        editor.apply();
    }

    public void createSession_alamat(String alamat){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_ALAMAT, alamat);
        editor.apply();
    }

    public void createSession_nama_rek(String nama_rek){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_NAMA_REK, nama_rek);
        editor.apply();
    }

    public void createSession_nama_bank(String nama_bank){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_NAMA_BANK, nama_bank);
        editor.apply();
    }

    public void createSession_no_rek(String no_rek){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_NO_REK, no_rek);
        editor.apply();
    }

    public void logOut(){
        editor.clear();
        editor.apply();
        Intent i = new Intent(context, login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_TANGGAL_LAHIR, pref.getString(KEY_TANGGAL_LAHIR, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_KAMAR, pref.getString(KEY_KAMAR, null));
        user.put(KEY_TELEPON, pref.getString(KEY_TELEPON, null));
        user.put(KEY_IDENTITAS, pref.getString(KEY_IDENTITAS, null));
        user.put(KEY_ALAMAT, pref.getString(KEY_ALAMAT, null));
        user.put(KEY_NAMA_REK, pref.getString(KEY_NAMA_REK, null));
        user.put(KEY_NAMA_BANK, pref.getString(KEY_NAMA_BANK, null));
        user.put(KEY_NO_REK, pref.getString(KEY_NO_REK, null));
        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, null));
        return user;
    }


}