package com.example.balda.listtest.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.balda.listtest.Models.Brewery;
import com.example.balda.listtest.R;
import com.example.balda.listtest.Utilities.ImageUtilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddBeerActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private TextView mBreweryDetailName;

    public static final String EXTRA_BREWERY_ID = "BreweryID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);

        mBreweryDetailName = (TextView)findViewById(R.id.textViewAddBeerBrewery);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // get id, load from DB and populate views
        String breweryID = getIntent().getStringExtra(EXTRA_BREWERY_ID);

        Query query = mDatabaseReference.child(getString(R.string.breweries_child)).child(breweryID);
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
}
