package com.example.nytarticlesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nytarticlesearch.R;
import com.example.nytarticlesearch.activities.utils.EndlessRecyclerViewScrollListener;
import com.example.nytarticlesearch.activities.utils.NetworkUtil;
import com.example.nytarticlesearch.adapters.SearchArticleAdapter;
import com.example.nytarticlesearch.models.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchArticleActivity extends AppCompatActivity {

    AsyncHttpClient client = new AsyncHttpClient();
    String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    ArrayList<Article> articles = new ArrayList<Article>();
    RecyclerView rvArticles;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    SearchArticleAdapter adapter;
    String query;
    String checkboxValue;
    String beginDate;
    String endDate;
    String sortOrder;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);
        //check network connection
        if (NetworkUtil.getInstance(this).isNetworkAvailable()) {
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not connected to Internet", Toast.LENGTH_LONG).show();
        }

        setupViews();

    }

    private void setupViews() {

        rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
        staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        rvArticles.setLayoutManager(staggeredGridLayoutManager);

        adapter = new SearchArticleAdapter(this, articles);
        rvArticles.setAdapter(adapter);

        rvArticles.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });

        sortOrder = getIntent().getStringExtra("sortOrder");
        checkboxValue = getIntent().getStringExtra("checkboxValue");
        beginDate = getIntent().getStringExtra("beginDate");
        endDate = getIntent().getStringExtra("endDate");

        if ((sortOrder != null) || (checkboxValue != null) || (beginDate != null) || (endDate != null)) {
            onAdvnaceSearch(sortOrder, checkboxValue, beginDate, page, endDate);
        }

        adapter.setOnItemClickListener(new SearchArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Article article = articles.get(position);
                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                intent.putExtra("article", article.getWebUrl());
                startActivity(intent);

            }
        });
    }




    private void customLoadMoreDataFromApi(int page) {
        onAdvnaceSearch(sortOrder, checkboxValue, beginDate, page, endDate);
        page++;
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
                    articles.clear();
                    articles.addAll(Article.fromJSONArray(articleJsonResults));
                    //adapter.notifyDataSetChanged();
                    int size = adapter.getItemCount();
                    adapter.notifyItemRangeInserted(size, Article.fromJSONArray(articleJsonResults).size());
                    Log.d("DEBUG", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(SearchArticleActivity.this, "FAIL", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Advance search
    private void onAdvnaceSearch(String sortOrder, String checkboxValue, String beginDate, int page, String endDate) {
        RequestParams params = new RequestParams();
        params.put("api-key", "707891098d8b435e864aca343d92a4ff");
        params.put("page", page);


        if (sortOrder != null && !sortOrder.isEmpty() && !sortOrder.equals("null")) {
            params.put("sort", sortOrder);
        }
        if (checkboxValue != null && !checkboxValue.isEmpty() && !checkboxValue.equals("null")) {
            params.put("fq", "news_desk:(" + checkboxValue + ")");
        }
        if (beginDate != null && !beginDate.isEmpty() && !beginDate.equals("null")) {
            params.put("begin_date", beginDate);
        }
        if (endDate != null && !endDate.isEmpty() && !endDate.equals("null")) {
            params.put("end_date", endDate);
        }

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    int size = adapter.getItemCount();
                    articles.addAll(Article.fromJSONArray(articleJsonResults));
                    adapter.notifyItemRangeInserted(size, Article.fromJSONArray(articleJsonResults).size());
                    Log.d("DEBUG", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(SearchArticleActivity.this, "FAIL", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
               articles.clear();
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

    private void showSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }
}
