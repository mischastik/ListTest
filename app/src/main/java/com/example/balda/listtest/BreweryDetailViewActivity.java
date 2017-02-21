package com.example.balda.listtest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.balda.listtest.DataPresentation.BeerListEntryAdapter;
import com.example.balda.listtest.DataPresentation.BeerListEntryViewHolder;
import com.example.balda.listtest.Models.BeerListEntry;
import com.example.balda.listtest.Models.Brewery;
import com.example.balda.listtest.Utilities.BasicUtilities;
import com.example.balda.listtest.Utilities.ImageUtilities;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class BreweryDetailViewActivity extends AppCompatActivity {
    public static final String EXTRA_BREWERY_ID = "BreweryID";

    private DatabaseReference mFirebaseDatabaseReference;
    private StorageReference mStorageRef;
    private RecyclerView mBeerListEntryRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<BeerListEntry, BeerListEntryViewHolder> mFirebaseAdapter;
    private TextView mBreweryDetailName;
    private TextView mBreweryDetailLocation;
    private ImageView mBreweryDetailLogo;
    private FloatingActionButton mAddBeerListEntryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_detail_view);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mBreweryDetailName = (TextView)findViewById(R.id.textViewBreweryDetailName);
        mBreweryDetailLocation = (TextView)findViewById(R.id.textViewBreweryDetailLocation);
        mBreweryDetailLogo = (ImageView)findViewById(R.id.imageViewBreyeryDetailLogo);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        // get id, load from DB and populate views
        Intent myIntent = getIntent();
        String breweryID = myIntent.getStringExtra(EXTRA_BREWERY_ID);

        Query query = mFirebaseDatabaseReference.child(getString(R.string.breweries_child)).child(breweryID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Brewery brewery = dataSnapshot.getValue(Brewery.class);
                mBreweryDetailName.setText(brewery.getName());
                mBreweryDetailLocation.setText(brewery.getLocation());
                ImageUtilities.setLogoImage(getApplicationContext(), brewery.getLogoFileID(), mBreweryDetailLogo, mStorageRef);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBeerListEntryRecyclerView = (RecyclerView) findViewById(R.id.beerListEntryRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        Query subDB = mFirebaseDatabaseReference.child(getString(R.string.beer_list_entries_child));//.equalTo(breweryID);
        mFirebaseAdapter = new BeerListEntryAdapter(subDB);

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int entryCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (entryCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mBeerListEntryRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mBeerListEntryRecyclerView.setLayoutManager(mLinearLayoutManager);
        mBeerListEntryRecyclerView.setAdapter(mFirebaseAdapter);

        mAddBeerListEntryButton = (FloatingActionButton)findViewById(R.id.button_add_beer_list_entry);
        mAddBeerListEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                //Intent intent = new Intent(MainActivity.this, ACTIVITY GOES HERE);
                //startActivity(intent);
            }
        });
    }
}
