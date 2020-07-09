package com.store.anime.ostshow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.store.anime.ostshow.helpers.DBHelper;
import com.store.anime.ostshow.R;
import com.store.anime.ostshow.models.Song;

import java.util.ArrayList;


public class AandHFragment extends Fragment {

    CustomAdapter myCustomAdapter=null;
    ListView listView=null;
    DBHelper db=null;
    ArrayList<Song> songs=null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_aand_h, container, false);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setCol
       /* WebView about = (WebView)v.findViewById(R.id.webview);
        about.loadUrl("file:///android_asset/about.html");*/
        db= new DBHelper(v.getContext());
        songs=db.getAllSongs();
        myCustomAdapter= new CustomAdapter(this.getContext(),R.layout.mylist,songs);

        listView = (ListView)v.findViewById(R.id.songslist);
        listView.setAdapter(myCustomAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    // Toast.makeText(getContext(), songs.get(i).getUrlPhoto(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), PlayerActivity.class);
                    intent.putExtra("musicname", songs.get(i).getName());
                    intent.putExtra("singername", songs.get(i).getSingername());
                    intent.putExtra("musicurl", songs.get(i).getUrlSong());
                    intent.putExtra("musicalbum", songs.get(i).getUrlPhoto());
                    intent.putExtra("album", songs.get(i).getAlbum());
                    startActivity(intent);

               // Toast.makeText(getContext(), songs.get(i).getSingername(), Toast.LENGTH_SHORT).show();
            }
        });
        //Toast.makeText(v.getContext(), db.getSong(1).getName(), Toast.LENGTH_SHORT).show();
        return v;
    }


}
