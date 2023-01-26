package com.example.padigreen;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CLV_DataUser extends ArrayAdapter<String> {
    //Declarasi variable
    private final Activity context;
    private ArrayList<String> vId;
    private ArrayList<String> vNama_fasilitas;
    private ArrayList<String> vJumlah;
    private ArrayList<String> vStatus;
    private ArrayList<String> vPhoto;
    //Choose Delete

    public CLV_DataUser(Activity context, ArrayList<String> Id, ArrayList<String> Nama_fasilitas, ArrayList<String> Jumlah, ArrayList<String> Status,ArrayList<String> Photo)
    {
        super(context, R.layout.list_item_fasilitas,Nama_fasilitas);
        this.context    = context;
        this.vId      = Id;
        this.vNama_fasilitas     = Nama_fasilitas;
        this.vJumlah       = Jumlah;
        this.vStatus   = Status;
        this.vPhoto     = Photo;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_item_fasilitas, null, true);

        //Declarasi komponen
        TextView nama_fasilitas  = rowView.findViewById(R.id.idTXTNama_fasilitas);
        TextView status          = rowView.findViewById(R.id.idTXTStatus);
        de.hdodenhof.circleimageview.CircleImageView photo     = rowView.findViewById(R.id.idIVGambar);

        //Set Parameter Value sesuai widget textview
        nama_fasilitas.setText(vNama_fasilitas.get(position));
        status.setText(vStatus.get(position));

        if (vPhoto.get(position).equals(""))
        {
            Picasso.get().load("https://tkjbpnup.com/kelompok_3/noimage/image.png").into(photo);
            //Picasso.get().load("http://tekajeapunya.com/kelompok_11/image/noimage.png").into(photo);
        }
        else
        {
            Picasso.get().load("https://tkjbpnup.com/kelompok_3/image/"+vPhoto.get(position)).into(photo);
            //Picasso.get().load("http://tekajeapunya.com/kelompok_11/image/"+vPhoto.get(position)).into(photo);
        }

        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }


}