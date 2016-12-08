package com.android.waldoapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.waldoapp.R;
import com.android.waldoapp.fragments.ImageListFragment;
import com.android.waldoapp.rest.model.PhotoObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kaplanf on 08/12/2016.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private ArrayList<PhotoObject> photos;
    private Context context;
    private ImageListFragment.ListType type;

    public ImageListAdapter(ArrayList<PhotoObject> photoObjects, Context c, ImageListFragment.ListType listType) {
        photos = photoObjects;
        context = c;
        type = listType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (type) {
            case LIST:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.photo_item, parent, false);
                break;
            case GRID:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.photo_item_grid, parent, false);
                break;
        }
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (photos.get(position).url != null) {
            Picasso.with(context).load(photos.get(position).url).placeholder(R.drawable.placeholder_location).into(holder.imageView);
        } else {
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder_location));
        }
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.imageViewAdapter);
        }
    }
}
