package com.example.nytarticlesearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nytarticlesearch.R;
import com.example.nytarticlesearch.models.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NidhiSaini on 7/31/16.
 */
public class SearchArticleAdapter extends RecyclerView.Adapter<SearchArticleAdapter.ViewHolder> {
    private List<Article> mArticle = new ArrayList<>();
    // Store the context for easy access
    private Context mContext;
    Article article;
    ArrayList<Article> articles;


   // ***** Creating OnItemClickListener *****/

    // Define listener member variable
    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public SearchArticleAdapter setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
        return null;
    }


    //Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivASImage;
        public TextView tvASTitle;
        private Context context;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.

            ivASImage = (ImageView) itemView.findViewById(R.id.ivASImage);
            tvASTitle = (TextView) itemView.findViewById(R.id.tvASTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });


        }

    }



    public SearchArticleAdapter(Context context, List<Article> articles){
        mContext = context;
        mArticle = articles;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext(){
        return mContext;
    }

    @Override
    public SearchArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.item_search_article_result, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchArticleAdapter.ViewHolder viewHolder, int position) {
        article = mArticle.get(position);


        viewHolder.tvASTitle.setText(article.getHeadline());

        if(!TextUtils.isEmpty(article.getThumbNail())){
            Glide.with(getContext()).load(article.getThumbNail()).into(viewHolder.ivASImage);
        }
        else{
            Glide
                    .with(getContext()).pauseRequests();
                    /*.cancelRequest(viewHolder.ivASImage);*/

            viewHolder.ivASImage.setImageDrawable(null);
        }


    }

    @Override
    public int getItemCount() {
        return mArticle.size();
    }



}
