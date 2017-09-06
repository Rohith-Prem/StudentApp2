package com.project.iedc.studentapp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

public class webview extends AppCompatActivity {
    WebView w;
    ProgressBar prog;
    Button back,reload;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        w = (WebView)findViewById(R.id.webview);
        prog = (ProgressBar)findViewById(R.id.progress);
        back = (Button)findViewById(R.id.back);
        reload = (Button)findViewById(R.id.refresh);
        Bundle bun = getIntent().getExtras();
        url = bun.getString("url");
        w.setWebViewClient((new WebViewClient(){
            @Override public void onPageStarted(WebView w, String url,Bitmap favicon){
                super.onPageStarted(w,url,favicon);
                prog.setVisibility(View.VISIBLE);
            }
            @Override public void onPageFinished(WebView w,String url){
                prog.setVisibility(w.GONE);
            }
                }));
        w.loadUrl(url);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w.goBack();
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w.loadUrl(url);
            }
        });

    }


}
