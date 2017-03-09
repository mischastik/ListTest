package com.example.balda.listtest.Activities;

import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddBeerActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;

    private TextView mBreweryDetailName;
    private EditText mBeerName;
    private Spinner mBeerType;
    private RatingBar mRatingBar;

    private String mBreweryID;

    public static final String EXTRA_BREWERY_ID = "BreweryID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);

        mBreweryDetailName = (TextView)findViewById(R.id.textViewAddBeerBrewery);
        mBeerName = (EditText)findViewById(R.id.editTextAddBeerName);
        mBeerType = (Spinner)findViewById(R.id.spinnerAddBeerType);
        mRatingBar = (RatingBar)findViewById(R.id.ratingBarAddBeerRatingBar);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // get id, load from DB and populate views
        mBreweryID = getIntent().getStringExtra(EXTRA_BREWERY_ID);

        Query query = mDatabaseReference.child(getString(R.string.breweries_child)).child(mBreweryID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Brewery brewery = dataSnapshot.getValue(Brewery.class);
                mBreweryDetailName.setText(brewery.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void confirmAddBeer(View view) {
        BeerListEntry beer = new BeerListEntry(mBeerName.getText().toString(),
                BasicUtilities.getBeerTypeIDforTypeName(mBeerType.getSelectedItem().toString()),
                mBreweryID);
        List<String> ratings = new ArrayList<String>();
        ratings.add(String.valueOf(mRatingBar.getRating()));
        beer.setRatings(ratings);

        List<String> dates = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        dates.add(dateFormat.format(date));
        beer.setDates(dates);

        mDatabaseReference.child(getString(R.string.beer_list_entries_child)).push().setValue(beer);
        mBeerName.setText("");
        finish();
    }
}
