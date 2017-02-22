package com.example.balda.listtest.DataPresentation;

import android.content.Context;
import android.widget.ProgressBar;

import com.example.balda.listtest.Models.Brewery;
import com.example.balda.listtest.R;
import com.example.balda.listtest.Utilities.ImageUtilities;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

/**
 * Created by balda on 21.02.2017.
 */

public class BreweryAdapter extends FirebaseRecyclerAdapter<Brewery, BreweryViewHolder> {
    private ProgressBar mProgressBar;
    private Context mContext;
    private StorageReference mStorageRef;

    public BreweryAdapter(Query dbRef, ProgressBar progressBar, Context context, StorageReference storageReference) {
        super(Brewery.class, R.layout.brewery_list_item, BreweryViewHolder.class, dbRef);
        mProgressBar = progressBar;
        mContext = context;
        mStorageRef = storageReference;
    }

    @Override
    protected Brewery parseSnapshot(DataSnapshot snapshot) {
        Brewery brewery = super.parseSnapshot(snapshot);
        if (brewery != null) {
            brewery.setId(snapshot.getKey());
        }
        return brewery;
    }

    @Override
    protected void populateViewHolder(final BreweryViewHolder viewHolder, Brewery brewery, int position) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        }
        viewHolder.breweryNameTextView.setText(brewery.getName());
        viewHolder.breweryLocationTextView.setText(brewery.getLocation());
        viewHolder.id = brewery.getId();
        ImageUtilities.setLogoImage(mContext, brewery.getLogoFileID(), viewHolder.breweryLogoImageView, mStorageRef);
    }
}
