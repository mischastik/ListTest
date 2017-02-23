package com.example.balda.listtest.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.balda.listtest.Models.BeerListEntry;
import com.example.balda.listtest.Models.Brewery;
import com.example.balda.listtest.R;
import com.example.balda.listtest.Utilities.BasicUtilities;
import com.example.balda.listtest.Utilities.ImageUtilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BeerDetailViewActivity extends AppCompatActivity {
    public static final String EXTRA_BEER_ID = "BeerID";

    private DatabaseReference mDatabaseReference;

    private TextView mBreweryName;
    private TextView mBeerName;
    private TextView mBeerType;
    private RatingBar mBeerRating;
    private ImageView mBreweryLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail_view);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mBeerName = (TextView)findViewById(R.id.textViewBeerDetailBeerName);
        mBreweryName = (TextView)findViewById(R.id.textViewBeerDetailBreweryName);
        mBeerType = (TextView)findViewById(R.id.textViewBeerDetailBeerType);
        mBeerRating = (RatingBar) findViewById(R.id.ratingBarBeerDetailAverageRating);
        mBreweryLogo = (ImageView)findViewById(R.id.imageViewBeerDetailBreweryLogo);

        String beerID = getIntent().getStringExtra(EXTRA_BEER_ID);
        Query beerEntryQuery = mDatabaseReference.child(getString(R.string.beer_list_entries_child)).child(beerID);
        beerEntryQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BeerListEntry beer = dataSnapshot.getValue(BeerListEntry.class);
                mBeerName.setText(beer.getName());
                mBeerType.setText(BasicUtilities.getNameForBeerTypeID(beer.getType()));
                mBeerRating.setRating(BasicUtilities.calculateAvgRating(beer.getRatings()));
                Query query = mDatabaseReference.child(getBaseContext().getString(R.string.breweries_child)).child(beer.getBrewery());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Brewery brewery = dataSnapshot.getValue(Brewery.class);
                        mBreweryName.setText(brewery.getName());
                        mBreweryLogo.setImageBitmap(ImageUtilities.loadImageFromExternalStorage(getBaseContext(), brewery.getLogoFileID()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
