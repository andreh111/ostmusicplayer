package com.store.anime.ostshow.ui;

/**
 * Created by Anonym on 10/27/2017.
 */

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.store.anime.ostshow.R;
import com.store.anime.ostshow.models.Song;

import java.util.ArrayList;



public class CustomAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<Song> songs;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context= context;
        songs=objects;

    }


    private class ViewHolder
    {
        TextView songName;
        TextView album;
        ImageView albumpic;
        //TextView carColor;
        //TextView carPlace;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.mylist, null);

            holder = new ViewHolder();
            holder.songName = (TextView) convertView.findViewById(R.id.songname);
            holder.album = (TextView) convertView.findViewById(R.id.albumname);
            holder.albumpic = (ImageView) convertView.findViewById(R.id.icon);
            //holder.carName = (TextView) convertView.findViewById(R.id.carName);
            //holder.carPlace=(TextView)convertView.findViewById(R.id.carPlace);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Song individualSong = songs.get(position);
        //holder.carPlace.setText("Car Place: " +  individualCar.getPlace() + "");
        holder.songName.setText(individualSong.getName());
        holder.album.setText(individualSong.getAlbum());
        Glide.with(context).load(Uri.parse(individualSong.getUrlPhoto())).into(holder.albumpic);

       // holder.albumpic.setImageURI(Uri.parse(individualSong.getUrlPhoto()));
        //holder.carColor.setText("Car Color: "+individualCar.getColor());
        return convertView;


    }
}
