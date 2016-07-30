package com.example.nytarticlesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nytarticlesearch.R;
import com.example.nytarticlesearch.adapters.ArticleArrayAdapter;
import com.example.nytarticlesearch.fragments.SettingsFragment;
import com.example.nytarticlesearch.models.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class SearchActivity extends AppCompatActivity {
    EditText editQuery;
    GridView gvResults;
    Button btnSearch;
    ArrayList<Article> articles;
    ArrayAdapter<Article> adapter;
    AsyncHttpClient client = new AsyncHttpClient();
    String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    String checkboxValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
    }

    private void setupViews() {

        editQuery = (EditText) findViewById(R.id.etQuery);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        gvResults = (GridView) findViewById(R.id.gvResults);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(SearchActivity.this, articles);
        gvResults.setAdapter(adapter);

        //listener for gridview item click
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create intent to display the article
                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                //get article to display
                Article article = articles.get(position);
                //pass in the article in the intent
                intent.putExtra("article", article);
                //launch the activity
                startActivity(intent);


            }
        });

        String sortOrder = getIntent().getStringExtra("sortOrder");
        checkboxValue = getIntent().getStringExtra("checkboxValue");
        Toast.makeText(getApplicationContext(), sortOrder, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),checkboxValue, Toast.LENGTH_LONG).show();


        if(sortOrder != ""){
            onAdvnaceSearch(sortOrder, checkboxValue);
        }
    }

    private void onAdvnaceSearch(String sortOrder, String checkboxValue) {
        RequestParams params = new RequestParams();
        params.put("api-key", "707891098d8b435e864aca343d92a4ff");
        params.put("page", 0);
        params.put("sort", sortOrder);
        params.put("fq","news_desk : (" + checkboxValue + ")");

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJSONArray(articleJsonResults));
                    Log.d("DEBUG", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(SearchActivity.this, "FAIL", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void onArticleSearch(View view) {
        String query  = editQuery.getText().toString();
        //Toast.makeText(this, "Searching for" + query, Toast.LENGTH_SHORT).show();

        /*AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";*/
        RequestParams params = new RequestParams();
        params.put("api-key", "707891098d8b435e864aca343d92a4ff");
        params.put("page", 0);
        params.put("q", query);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJSONArray(articleJsonResults));
                    Log.d("DEBUG", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(SearchActivity.this, "FAIL", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miSettings:
                showSettings();
                return true;
            case R.id.settings:
                showDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SettingsFragment SettingsFragment = com.example.nytarticlesearch.fragments.SettingsFragment.newInstance("Title");
        SettingsFragment.show(fm, "fragment_edit_name");

    }

    private void showSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

}
