package com.example.nytarticlesearch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.nytarticlesearch.models.Article;

import java.util.List;

/**
 * Created by NidhiSaini on 7/31/16.
 */
public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> mArticle;

    private final int IMAGEARTICLE = 0, TEXTARTICLE = 1;


    public ComplexRecyclerViewAdapter(List<Article> mArticle) {
        this.mArticle = mArticle;
    }

    @Override
    public int getItemCount() {
        return this.mArticle.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mArticle.get(position) instanceof Article) {
            return IMAGEARTICLE;
        } /*else if (mArticle.get(position) instanceof String) {
            return TEXTARTICLE;
        }*/
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


}
