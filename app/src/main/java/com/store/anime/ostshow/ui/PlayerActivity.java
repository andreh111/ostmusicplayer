package com.store.anime.ostshow.ui;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.store.anime.ostshow.helpers.Config;
import com.store.anime.ostshow.helpers.DBHelper;
import com.store.anime.ostshow.R;
import com.store.anime.ostshow.models.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import nl.changer.audiowife.AudioWife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlayerActivity extends AppCompatActivity {

    //private static final String TAG = MainActivity.class.getSimpleName();
    int sid;
    String songname = "";
    String singername = "";
    String songurl = "";
    String songalbum = "";
    String albumname = "";
    private static final int INTENT_PICK_AUDIO = 1;

    private Context mContext;

    private View mPlayMedia;
    private View mPauseMedia;
    private SeekBar mMediaSeekBar;
    private ImageView cover;
    private ImageView stop;
    private TextView start, end;
    private ImageView download;
    private TextView mRunTime;
    private TextView mTotalTime;
    private TextView mPlaybackTime;
    private TextView songinf;
    private ImageView btnA;
    private long enqueue;
    private DownloadManager downloadManager;

    private void getSongByUrl(final String url) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Config.APP_URL + "getSongByUrl.php?url=" + url)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Song song = new Song(object.getInt("songid"), object.getString("songname"), object.getString("singername"),
                                 object.getString("songalbum"),  object.getString("songurl"), object.getString("albumname"));
                        sid = object.getInt("songid");
                        songname = object.getString("songname");
                        singername = object.getString("singername");
                        songalbum = object.getString("songalbum");
                        songurl = Config.APP_URL + object.getString("songurl");
                        albumname = object.getString("albumname");

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                songinf.setText(albumname + " - " + songname + "\n" + singername);
                if(songalbum!=null){
                    Glide.with(mContext).load(R.drawable.disc)
                            // override(1000,1000)
                            .into(cover);
                    Glide.with(mContext).load(songalbum).
                            override(1000, 1000)
                            .into(cover);
                } else

                {
                    Glide.with(mContext).load(R.drawable.disc)
                            // override(1000,1000)
                            .into(cover);
                }
                Uri uri = Uri.parse(songurl);
                AudioWife.getInstance().release();
                AudioWife.getInstance()
                        .init(mContext, uri)
                        .setPlayView(mPlayMedia)
                        .setPauseView(mPauseMedia)
                        .setSeekBar(mMediaSeekBar)
                        .setRuntimeView(start)
                        .setTotalTimeView(end)
                ;

            }
        };

        task.execute();
    }

    private void getSongById(final int songid) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Config.APP_URL + "getSongById.php?songid=" + songid)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Song song = new Song(object.getInt("songid"), object.getString("songname"), object.getString("singername"),
                                Config.APP_URL + object.getString("songalbum"), Config.APP_URL + object.getString("songurl"), object.getString("albumname"));
                        sid = object.getInt("songid");
                        songname = object.getString("songname");
                        singername = object.getString("singername");
                        songalbum = object.getString("songalbum");
                        songurl = object.getString("songurl");
                        albumname = object.getString("albumname");

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                songinf.setText(albumname + " - " + songname + "\n" + singername);
                if(songalbum!=null){
                    Glide.with(mContext).load(R.drawable.disc)
                            // override(1000,1000)
                            .into(cover);
                    Glide.with(mContext).load(songalbum).
                            override(1000, 1000)
                            .into(cover);
                } else

                {
                    Glide.with(mContext).load(R.drawable.disc)
                            // override(1000,1000)
                            .into(cover);
                }
                Uri uri = Uri.parse(songurl);
                AudioWife.getInstance().release();
                AudioWife.getInstance()
                        .init(mContext, uri)
                        .setPlayView(mPlayMedia)
                        .setPauseView(mPauseMedia)
                        .setSeekBar(mMediaSeekBar)
                        .setRuntimeView(start)
                        .setTotalTimeView(end)
                ;

            }
        };

        task.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        setTheme(R.style.AppTheme);
        // Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
//        ((AppCompatActivity) getApplicationContext()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getApplicationContext()).getSupportActionBar().setTitle("Castra Music Player");
        //  toolbar.setTitleTextColor(Color.WHITE);
        //setSupportActionBar(toolbar);
        mContext = this;
        final DBHelper dbHelper = new DBHelper(this);
        btnA = (ImageView) findViewById(R.id.btnAdd);
        //download = (ImageView)findViewById(R.id.download);
        //stop = (ImageView)findViewById(R.id.imageView2);
        start = (TextView) findViewById(R.id.st);
        end = (TextView) findViewById(R.id.end);
        songinf = (TextView) findViewById(R.id.textView);
        mPlayMedia = findViewById(R.id.playmusic);
        mPauseMedia = findViewById(R.id.pausemusic);
        mMediaSeekBar = (SeekBar) findViewById(R.id.seekBar);
        download = (ImageView) findViewById(R.id.download);
        cover = (ImageView) findViewById(R.id.imageView4);
        Glide.with(mContext).load(R.drawable.disc)
                // override(1000,1000)
                .into(cover);
        songinf.setText("");
        start.setText("00:00");
        end.setText("--:--");
//        Toast.makeText(mContext, "" + getIntent().getExtras().getInt("songid"), Toast.LENGTH_SHORT).show();
        getSongById(getIntent().getExtras().getInt("songid"));

        // initialize the player controls



        btnA.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                try {
                    if (!checkifSongAdded(getIntent().getExtras().getString("musicname"), dbHelper.getAllSongs())) {
                        dbHelper.addSong(new Song(songname, singername, songalbum, songurl, albumname));
                        Toast.makeText(mContext, "song added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "Song already added!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        download.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick(View view) {

                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getIntent().getExtras().getString("musicurl")));

                if (isSongExists(Environment.DIRECTORY_DOWNLOADS + "/Castra Songs/" + "/" + getIntent().getExtras().getString("musicname") + ".mp3")) {
                    Toast.makeText(mContext, "Song already downloaded", Toast.LENGTH_SHORT).show();
                } else {
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle("Downloading Music...");
                    request.setDescription(getIntent().getExtras().getString("musicname") + ".mp3");
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Castra Songs/" + "/" + getIntent().getExtras().getString("musicname") + ".mp3");

                    long referenceId = getIntent().getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    referenceId = downloadManager.enqueue(request);
                }

            }
        });

    }

    private boolean isSongExists(String filename) {

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.exists();


    }

    private boolean deleteSong(String filename) {

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.delete();


    }

    public void startDownload(String url) {
        File rootPath = new File(Environment.getExternalStorageDirectory(), "Castra");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, getIntent().getExtras().getString("musicname") + ".mp3");

        DownloadManager mManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request mRqRequest = new DownloadManager.Request(Uri.parse(url));
        mRqRequest.setDescription("This is castra song file");
        mRqRequest.setDestinationUri(Uri.parse(localFile.toString()));
        long idDownload = mManager.enqueue(mRqRequest);
    }

    public boolean checkifSongAdded(String songName, ArrayList<Song> songList) {
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getName().equals(songName)) return true;
        }
        return false;
    }

    private void downloadFile() {
        Toast.makeText(mContext, "Download Started....", Toast.LENGTH_SHORT).show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(getIntent().getExtras().getString("musicurl"));
        //StorageReference  islandRef = storageRef.child("file.txt");

        File rootPath = new File(Environment.getExternalStorageDirectory(), "Castra");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, getIntent().getExtras().getString("musicname") + ".mp3");

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PlayerActivity.this, "File downloaded to Castra Folder in phone memory", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AudioWife.getInstance().release();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent mainActivity = new Intent(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
    }


}