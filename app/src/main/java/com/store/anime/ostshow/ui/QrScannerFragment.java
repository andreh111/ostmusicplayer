package com.store.anime.ostshow.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.store.anime.ostshow.R;

import me.dm7.barcodescanner.zbar.ZBarScannerView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QrScannerFragment extends Fragment implements ZBarScannerView.ResultHandler {
    private ZXingScannerView zXingScannerView;
    private ZBarScannerView mScannerView;
    private MediaPlayer mp;
    private static String url = "";
    String songname = "";
    String singername = "";
    String songurl = "";
    String songalbum = "";
    String albumname = "";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);


    }
    // Get the results:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mScannerView = new ZBarScannerView(getActivity());

        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setAutoFocus(true);
    }



    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);  // Use filter.xml from step 1
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //Do whatever you want to do
            Intent i = new Intent(getActivity(), HelpActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {
        //Toast.makeText(getContext(), result.getContents().toString(), Toast.LENGTH_SHORT).show();
        url = result.getContents().toString();
        //if(MusicPlayerActivity.mp.isPlaying()) MusicPlayerActivity.mp.stop();
        if (isNetworkAvailable()) {
            // url = url.substring(url.indexOf("songs"));
            //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();
            //load_data_from_server(url);
            //FirebaseDatabase sls = Firebase.getInstance();

            Intent i = new Intent(getActivity().getApplicationContext(), PlayerActivity.class);
            i.putExtra("musicurl", url);//url  as excpected
            startActivity(i);


        } else {
            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }


}
