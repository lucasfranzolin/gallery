package com.challenge.code.gallery.utils;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<String> links;

    public GalleryAdapter(Context context, List<String> links) {
        this.mContext = context;
        this.links = links;
    }

    @Override
    public int getCount() {
        return links.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setMaxHeight(300);
        imageView.setMinimumHeight(300);

        Glide.with(mContext)
                .load(links.get(position))
                .centerCrop()
                .into(imageView);
        return imageView;
    }
}