package com.store.anime.ostshow.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.store.anime.ostshow.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        WebView about = (WebView)findViewById(R.id.webview);
        about.loadUrl("file:///android_asset/about.html");
    }
}
