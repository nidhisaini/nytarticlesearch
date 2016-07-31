package com.example.nytarticlesearch.activities.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by NidhiSaini on 7/31/16.
 */
public class ViewHolder2 extends RecyclerView.ViewHolder {

    private TextView tvASTitle;

    public ViewHolder2(View itemView) {
        super(itemView);
    }

    public TextView getTvASTitle() {
        return tvASTitle;
    }

    public void setTvASTitle(TextView tvASTitle) {
        this.tvASTitle = tvASTitle;
    }


}
