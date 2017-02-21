package com.example.balda.listtest.DataPresentation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.balda.listtest.R;

/**
 * Created by balda on 19.02.2017.
 */

public class BeerListEntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public interface BeerListItemViewHolderOnClickHandler {
        void onBeerListItemViewItemClick(String id);
    }

    public TextView beerName;
    public TextView testersNumber;
    public TextView beerType;
    public RatingBar ratingBar;
    public String id;
    public static BeerListEntryViewHolder.BeerListItemViewHolderOnClickHandler mClickHandler = null;

    public BeerListEntryViewHolder(View v) {
        super(v);
        beerName = (TextView) itemView.findViewById(R.id.textViewBeerListEntyName);
        testersNumber = (TextView) itemView.findViewById(R.id.textViewTestersNumber);
        beerType = (TextView)itemView.findViewById(R.id.textViewBeerListEntryItemType);
        ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBarBeerListEntry);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mClickHandler.onBeerListItemViewItemClick(id);
    }
}
