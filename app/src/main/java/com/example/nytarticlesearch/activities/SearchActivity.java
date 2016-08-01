package com.example.nytarticlesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nytarticlesearch.R;
import com.example.nytarticlesearch.activities.utils.EndlessScrollListener;
import com.example.nytarticlesearch.activities.utils.NetworkUtil;
import com.example.nytarticlesearch.adapters.ArticleArrayAdapter;
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


    GridView gvResults;
    ArrayList<Article> articles;
    ArrayAdapter<Article> adapter;
    AsyncHttpClient client = new AsyncHttpClient();
    String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    String query;
    String checkboxValue;
    String selectedDate;
    String sortOrder;
    int page = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //check network connection
        if (NetworkUtil.getInstance(this).isNetworkAvailable()) {
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not connected to Internet", Toast.LENGTH_LONG).show();
        }
        setupViews();
    }

    private void setupViews() {

        gvResults = (GridView) findViewById(R.id.gvResults);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(SearchActivity.this, articles);
        gvResults.setAdapter(adapter);

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });


        //listener for gridview item click
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create intent to display the article
                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                //get article to display
                Article article = articles.get(position);
                //pass in the article in the intent
              //  intent.putExtra("article", article);
                //launch the activity
                startActivity(intent);
            }
        });


        sortOrder = getIntent().getStringExtra("sortOrder");
        checkboxValue = getIntent().getStringExtra("checkboxValue");
        selectedDate = getIntent().getStringExtra("beginDate");

        if ((sortOrder != null) || (checkboxValue != null) || (selectedDate != null)) {
            onAdvnaceSearch(sortOrder, checkboxValue, selectedDate, page);
        } /*else {
            sortOrder = "Newest";
            page = 0;
            onAdvnaceSearch(sortOrder, checkboxValue, beginDate, page);
        }*/
    }

    private void customLoadMoreDataFromApi(int page) {
        page++;
        onAdvnaceSearch(sortOrder, checkboxValue, selectedDate, page);

    }

    //Advance search
    private void onAdvnaceSearch(String sortOrder, String checkboxValue, String selectedDate, int page) {
        RequestParams params = new RequestParams();
        params.put("api-key", "707891098d8b435e864aca343d92a4ff");
        params.put("page", page);


        if (sortOrder != null && !sortOrder.isEmpty() && !sortOrder.equals("null")) {
            params.put("sort", sortOrder);
        }
        if (checkboxValue != null && !checkboxValue.isEmpty() && !checkboxValue.equals("null")) {
            params.put("fq", "news_desk:(" + checkboxValue + ")");
        }
        if (selectedDate != null && !selectedDate.isEmpty() && !selectedDate.equals("null")) {
            params.put("begin_date", selectedDate);
        }

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

    //ArticleSearch
    private void onArticleSearch(String query) {
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
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
                    adapter.clear();
                    adapter.addAll(Article.fromJSONArray(articleJsonResults));
                    adapter.notifyDataSetChanged();
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
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                onArticleSearch(query);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miSettings:
                showSettings();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialog() {


        /*FragmentManager fm = getSupportFragmentManager();
        SettingsFragment SettingsFragment = com.example.nytarticlesearch.fragments.SettingsFragment.newInstance("Title");
        SettingsFragment.show(fm, "fragment_edit_name");*/
    }

    private void showSettings() {
       /* Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);*/

        Intent intent = new Intent(getApplicationContext(), SearchArticleActivity.class);
        startActivity(intent);
    }

   /* private void SearchArticleActivity(){
        Intent intent = new Intent(getApplicationContext(),  Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }*/

}
