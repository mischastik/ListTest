package com.example.balda.listtest;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.balda.listtest.DataPresentation.BeerListEntryAdapter;
import com.example.balda.listtest.DataPresentation.BeerListEntryViewHolder;
import com.example.balda.listtest.Models.BeerListEntry;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class BeerListActivity extends AppCompatActivity {
    private RecyclerView mBeerListEntryRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<BeerListEntry, BeerListEntryViewHolder> mFirebaseAdapter;
    private DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mBeerListEntryRecyclerView = (RecyclerView) findViewById(R.id.beerListActivityRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        Query subDB = mFirebaseDatabaseReference.child(getString(R.string.beer_list_entries_child));
        mFirebaseAdapter = new BeerListEntryAdapter(subDB, (ProgressBar)findViewById(R.id.progressBarBeerList));

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
    }
}
