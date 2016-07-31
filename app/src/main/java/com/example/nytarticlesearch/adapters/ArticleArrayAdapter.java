package com.example.nytarticlesearch.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nytarticlesearch.R;
import com.example.nytarticlesearch.models.Article;

import java.util.List;

/**
 * http://guides.codepath.com/android/Converting-JSON-to-Models
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get data item for the position
        Article article = getItem(position);

        //check to see if existing view  is being used
        //not using recycler view ->inflate the layout
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_article_result,parent, false);
        }

        //find the image View
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);

        //clear the recycled image from convertView from last time
        imageView.setImageResource(0);
        //find text view
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        //populate the textview
        tvTitle.setText(article.getHeadline());

        //populate the thumb nail image
        //remote download the image in the background
        String thumbnail = article.getThumbNail();

        if(!TextUtils.isEmpty(thumbnail)){
            Glide.with(getContext()).load(thumbnail).into(imageView);
        }
        else{
            Glide
                    .with(getContext()).pauseRequests();
                    //.cancelRequest(imageView);

            imageView.setImageDrawable(null);
        }

        return convertView;
    }
}
