package com.example.balda.listtest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by balda on 19.02.2017.
 */

public class BreweryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public interface BreweryViewHolderOnClickHandler {
        void onBreweryViewItemClick(String id);
    }

    public TextView breweryNameTextView;
    public TextView breweryLocationTextView;
    public ImageView breweryLogoImageView;
    public String id;
    public static BreweryViewHolderOnClickHandler mClickHandler = null;

    public BreweryViewHolder(View v) {
        super(v);
        breweryNameTextView = (TextView) itemView.findViewById(R.id.brewery_item_name);
        breweryLocationTextView = (TextView) itemView.findViewById(R.id.brewery_location);
        breweryLogoImageView = (ImageView) itemView.findViewById(R.id.imageViewLogo);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mClickHandler.onBreweryViewItemClick(id);
    }
}