package com.example.balda.listtest.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.balda.listtest.DataPresentation.BeerListEntryAdapter;
import com.example.balda.listtest.DataPresentation.BeerListEntryViewHolder;
import com.example.balda.listtest.Models.BeerListEntry;
import com.example.balda.listtest.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BeerListActivity extends AppCompatActivity
        implements BeerListEntryViewHolder.BeerListItemViewHolderOnClickHandler {
    private RecyclerView mBeerListEntryRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<BeerListEntry, BeerListEntryViewHolder> mFirebaseAdapter;
    private DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        BeerListEntryViewHolder.mClickHandler = this;

        mBeerListEntryRecyclerView = (RecyclerView) findViewById(R.id.beerListActivityRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        mFirebaseAdapter = new BeerListEntryAdapter(mFirebaseDatabaseReference, this, (ProgressBar)findViewById(R.id.progressBarBeerList));

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

    @Override
    public void onBeerListItemViewItemClick(String id) {
        //create brewery detail intent
        Intent beerListEntryDetailIntent = new Intent(BeerListActivity.this, BeerDetailViewActivity.class);
        beerListEntryDetailIntent.putExtra(BeerDetailViewActivity.EXTRA_BEER_ID, id);
        startActivity(beerListEntryDetailIntent);
    }
}
