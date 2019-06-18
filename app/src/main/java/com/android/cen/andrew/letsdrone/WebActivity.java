package com.android.cen.andrew.letsdrone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private WebView mWebView;
    private static final String URL="urlll";
    private static final String TITLE="titleee";

    public static Intent newIntent(String url, String title, Context context) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mToolbar = findViewById(R.id.web_toolbar);
        mToolbar.setTitle(getIntent().getStringExtra(TITLE));

        mWebView = findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(getIntent().getStringExtra(URL));
    }
}
