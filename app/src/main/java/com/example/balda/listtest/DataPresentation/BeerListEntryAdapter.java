package com.example.balda.listtest.DataPresentation;

import android.content.Context;
import android.provider.ContactsContract;
import android.widget.ProgressBar;

import com.example.balda.listtest.Models.BeerListEntry;
import com.example.balda.listtest.Models.Brewery;
import com.example.balda.listtest.R;
import com.example.balda.listtest.Utilities.BasicUtilities;
import com.example.balda.listtest.Utilities.ImageUtilities;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by balda on 21.02.2017.
 */

public class BeerListEntryAdapter extends FirebaseRecyclerAdapter<BeerListEntry, BeerListEntryViewHolder> {
    private ProgressBar mProgressBar;
    private DatabaseReference mDatabaseReference;
    private Context mContext;

    public BeerListEntryAdapter(DatabaseReference dbRef, Context context) {
        super(BeerListEntry.class, R.layout.beer_list_entry_item, BeerListEntryViewHolder.class, dbRef.child(context.getString(R.string.beer_list_entries_child)));
        mDatabaseReference = dbRef;
        mContext = context;
    }
    public BeerListEntryAdapter(DatabaseReference dbRef, Context context, ProgressBar progressBar) {
        this(dbRef, context);
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
        viewHolder.id = beerListEntry.getId();
        viewHolder.beerName.setText(beerListEntry.getName());
        //List<String> testers = beerListEntry.getUserIDs();
        List<String> ratings = beerListEntry.getRatings();
        viewHolder.ratingBar.setRating(BasicUtilities.calculateAvgRating(ratings));
        if (ratings == null) {
            viewHolder.testersNumber.setText("0");
        } else {
            viewHolder.testersNumber.setText(String.valueOf(ratings.size()));
        }

        viewHolder.beerType.setText(BasicUtilities.getNameForBeerTypeID(beerListEntry.getType()));
        Query query = mDatabaseReference.child(mContext.getString(R.string.breweries_child)).child(beerListEntry.getBrewery());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Brewery brewery = dataSnapshot.getValue(Brewery.class);
                viewHolder.breweryName.setText(brewery.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
