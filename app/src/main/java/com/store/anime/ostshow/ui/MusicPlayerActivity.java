package com.store.anime.ostshow.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.store.anime.ostshow.R;

import java.io.File;
import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity implements Runnable,
        SeekBar.OnSeekBarChangeListener {

    ImageView playpause;
    SeekBar seekBar;
    ImageView download;
    MediaPlayer mp = null;
    int len = 0;
    boolean isPlaying = false;


    public MusicPlayerActivity() throws IOException {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        playpause = (ImageView)findViewById(R.id.playmusic);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        download = (ImageView)findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile();
            }
        });

        final String url = getIntent().getExtras().getString("musicurl");

        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isPlaying){
                    playpause.setImageResource(R.drawable.pause);
                    mp.pause();
                    len = mp.getCurrentPosition();
                    seekBar.setEnabled(false);


                }else{
                    playpause.setImageResource(R.drawable.play);
                    mp.seekTo(len);
                    mp.start();
                    seekBar.setEnabled(true);

                }
                isPlaying = !isPlaying;
            }
        });

        mp = new MediaPlayer();
        try {
            mp.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //mediaPlayer.stop();
                mediaPlayer.reset();

                seekBar.setProgress(0);

                playpause.setImageResource(R.drawable.pause);
                len = 0;
                mp = new MediaPlayer();
                try{
                    mp.setDataSource(url);
                }catch(IOException e){

                }try{
                    mp.prepare();
                }catch(IOException e){

                }
                isPlaying=false;


            }
        });
        mp.start();
        seekBar.setMax(mp.getDuration());

        new Thread(this).start();
        seekBar.setOnSeekBarChangeListener(this);


       // Toast.makeText(this, mp.getDuration(), Toast.LENGTH_SHORT).show();


    }
    private void downloadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/ost-show.appspot.com/o/27S8182m.mp3?alt=media&token=7ed5cc48-c1dc-4cc5-96e6-c4754a1fe580");
        //StorageReference  islandRef = storageRef.child("file.txt");

        File rootPath = new File(Environment.getExternalStorageDirectory(), "Castra");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath,"imageName.mp3");

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MusicPlayerActivity.this, "File downloaded to Castra Folder", Toast.LENGTH_SHORT).show();
               /* AlertDialog alertDialog = new AlertDialog.Builder(MusicPlayerActivity.this).create();
                alertDialog.setTitle("Notif");
                alertDialog.setMessage("Song successfully downloaded to device storage");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                Log.e("firebase ",";local tem file created  created " +localFile.toString());
                //  updateDb(timestamp,localFile.toString(),position);*/
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
               /* AlertDialog alertDialog = new AlertDialog.Builder(MusicPlayerActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Fail to download song");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                Log.e("firebase ",";local tem file not created  created " +exception.toString());*/
            }
        });
    }
    //if(mp.isPlaying()){}
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        try {
            if (mp.isPlaying() || mp != null) {
                if (fromUser)
                    mp.seekTo(progress);
            } else if (mp == null) {
                Toast.makeText(getApplicationContext(), "Media is not running",
                        Toast.LENGTH_SHORT).show();
                seekBar.setProgress(0);
            }
            //if(progress==seekBar.getMax()) seekBar.setProgress(0);

        } catch (Exception e) {
            Log.e("seek bar", "" + e);
            seekBar.setEnabled(false);

        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void run() {
        int currentPosition = mp.getCurrentPosition();
        int total = mp.getDuration();

        while (mp != null && currentPosition < total) {
            try {
                Thread.sleep(1000);
                currentPosition = mp.getCurrentPosition();
               // if(currentPosition==total) mp.stop(); seekBar.setProgress(0);

            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }
            seekBar.setProgress(currentPosition);


        }
        seekBar.setProgress(0);
        //playpause.setImageResource(R.drawable.pause);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mp.stop();
                startActivity(new Intent(this,MainActivity.class));
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
