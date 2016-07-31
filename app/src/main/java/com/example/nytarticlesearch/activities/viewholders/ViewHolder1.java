package com.example.nytarticlesearch.activities.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nytarticlesearch.R;

/**
 * Created by NidhiSaini on 7/31/16.
 */
public class ViewHolder1 extends RecyclerView.ViewHolder {

    private TextView tvASTitle;
    private ImageView ivASImage;

    public ViewHolder1(View itemView) {
        super(itemView);
        tvASTitle = (TextView) itemView.findViewById(R.id.tvASTitle);
        ivASImage = (ImageView) itemView.findViewById(R.id.ivASImage);
    }

    public TextView getTvASTitle() {
        return tvASTitle;
    }

    public void setTvASTitle(TextView tvASTitle) {
        this.tvASTitle = tvASTitle;
    }

    public ImageView getIvASImage() {
        return ivASImage;
    }

    public void setIvASImage(ImageView ivASImage) {
        this.ivASImage = ivASImage;
    }


}
