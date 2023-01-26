package com.example.padigreen;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CLV_DataLaporan extends ArrayAdapter<String> {
    //Declarasi variable
    private final Activity context;
    private ArrayList<String> vId;
    private ArrayList<String> vNama_pelapor;
    private ArrayList<String> vKamar;
    private ArrayList<String> vTanggal;
    private ArrayList<String> vIsi_laporan;
    private ArrayList<String> vKategori;
    private ArrayList<String> vPhoto;

    public CLV_DataLaporan(Activity context, ArrayList<String> Id, ArrayList<String> Nama_pelapor, ArrayList<String> Kamar, ArrayList<String> Tanggal,ArrayList<String> Isi_laporan,ArrayList<String> Kategori,ArrayList<String> Photo)
    {
        super(context, R.layout.list_item_history,Nama_pelapor);
        this.context    = context;
        this.vId      = Id;
        this.vNama_pelapor    = Nama_pelapor;
        this.vKamar       = Kamar;
        this.vTanggal   = Tanggal;
        this.vIsi_laporan  = Isi_laporan;
        this.vKategori  = Kategori;
        this.vPhoto     = Photo;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_item_history, null, true);

        //Declarasi komponen
        TextView kategori = rowView.findViewById(R.id.TVKategori);
        TextView nama_pelapor  = rowView.findViewById(R.id.TVNama_pelapor);
        TextView isi_laporan          = rowView.findViewById(R.id.TVIsi_laporan);
        TextView tanggal          = rowView.findViewById(R.id.TVTanggal);


        //Set Parameter Value sesuai widget textview
        kategori.setText(vKategori.get(position));
        nama_pelapor.setText("Nama: " + vNama_pelapor.get(position));
        isi_laporan.setText("Laporan: " + vIsi_laporan.get(position));
        tanggal.setText("Tanggal: " + vTanggal.get(position));


        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }




}