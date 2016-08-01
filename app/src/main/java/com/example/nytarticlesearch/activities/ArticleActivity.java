package com.example.nytarticlesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.nytarticlesearch.R;
import com.example.nytarticlesearch.models.Article;
/*
* http://guides.codepath.com/android/Sharing-Content-with-Intents#attach-share-for-a-webview-url
* https://developer.android.com/training/sharing/shareaction.html
* */

public class ArticleActivity extends AppCompatActivity {
    WebView webView;
    private ShareActionProvider miShareAction;
    Article article;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_article);

        url = getIntent().getStringExtra("article");//.getParcelableExtra("article");
        webView = (WebView) findViewById(R.id.wvArticle);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //webView.loadUrl(article.getWebUrl());
        webView.loadUrl(url);
       // url = article.getWebUrl();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_article, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);

        miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        // get reference to WebView
        webView = (WebView) findViewById(R.id.wvArticle);
        url = getIntent().getStringExtra("article");
        //article = (Article)getIntent().getSerializableExtra("article");
        //WebView wvArticle = (WebView) findViewById(R.id.wvArticle);


        if(webView.getUrl() != null) {
            //shareIntent.putExtra(Intent.EXTRA_TEXT, wvArticle.getUrl());
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        }

        if(shareIntent != null) {
            miShareAction.setShareIntent(shareIntent);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
