package com.example.balda.listtest.DataPresentation;

import android.widget.ProgressBar;

import com.example.balda.listtest.Models.BeerListEntry;
import com.example.balda.listtest.R;
import com.example.balda.listtest.Utilities.BasicUtilities;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.util.List;

/**
 * Created by balda on 21.02.2017.
 */

public class BeerListEntryAdapter extends FirebaseRecyclerAdapter<BeerListEntry, BeerListEntryViewHolder> {
    private ProgressBar mProgressBar;

    public BeerListEntryAdapter(Query dbRef) {
        super(BeerListEntry.class, R.layout.beer_list_entry_item, BeerListEntryViewHolder.class, dbRef);
    }
    public BeerListEntryAdapter(Query dbRef, ProgressBar progressBar) {
        this(dbRef);
        mProgressBar = progressBar;
    }
    @Override
    protected BeerListEntry parseSnapshot(DataSnapshot snapshot) {
        BeerListEntry beerListEntry = super.parseSnapshot(snapshot);
        if (beerListEntry != null) {
            beerListEntry.setId(snapshot.getKey());
        }
        return beerListEntry;
    }

    @Override
    protected void populateViewHolder(final BeerListEntryViewHolder viewHolder, BeerListEntry beerListEntry, int position) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        }
        viewHolder.beerName.setText(beerListEntry.getName());
        //List<String> testers = beerListEntry.getUserIDs();
        List<String> ratings = beerListEntry.getRatings();
        float avgRating = 0.0f;
        int nRatings = 0;
        if (ratings != null) {
            for (String ratingStr : ratings) {
                float rating = (float)Integer.parseInt(ratingStr);
                if (rating == 0.0f) {
                    continue;
                }
                nRatings++;
                avgRating += rating;
            }
            if (nRatings > 0)
                avgRating /= nRatings;
            else
                avgRating = 0.0f;
        }
        viewHolder.ratingBar.setRating(avgRating);
        viewHolder.testersNumber.setText(String.valueOf(nRatings));
        viewHolder.beerType.setText(BasicUtilities.getNameForBeerTypeID(beerListEntry.getType()));
    }
}
