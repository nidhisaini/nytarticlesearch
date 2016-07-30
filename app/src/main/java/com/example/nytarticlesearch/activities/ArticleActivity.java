package com.example.nytarticlesearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.nytarticlesearch.R;
import com.example.nytarticlesearch.models.Article;

public class ArticleActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_article);

        Article article = (Article)getIntent().getSerializableExtra("article");

        webView = (WebView) findViewById(R.id.wvArticle);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(article.getWebUrl());


    }
}
