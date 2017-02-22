package com.example.balda.listtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.balda.listtest.DataPresentation.BreweryAdapter;
import com.example.balda.listtest.DataPresentation.BreweryViewHolder;
import com.example.balda.listtest.Models.Brewery;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BreweryListActivity extends AppCompatActivity
        implements BreweryViewHolder.BreweryViewHolderOnClickHandler{

    private static final String TAG = "BreweryListActivity";

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Brewery, BreweryViewHolder> mFirebaseAdapter;
    private StorageReference mStorageRef;

    private RecyclerView mBreweryRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton mAddBreweryButton;
    private ProgressBar mProgressBar;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_list);

        BreweryViewHolder.mClickHandler = this;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // Initialize ProgressBar and RecyclerView.
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mBreweryRecyclerView = (RecyclerView) findViewById(R.id.breweryRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new BreweryAdapter(mFirebaseDatabaseReference.child(getString(R.string.breweries_child)).orderByChild("name"),
                mProgressBar, this, mStorageRef);
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
             @Override
             public void onItemRangeInserted(int positionStart, int itemCount) {
                 super.onItemRangeInserted(positionStart, itemCount);
                 int breweryCount = mFirebaseAdapter.getItemCount();
                 int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                 // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                 // to the bottom of the list to show the newly added message.
                 if (lastVisiblePosition == -1 ||
                         (positionStart >= (breweryCount - 1) && lastVisiblePosition == (positionStart - 1))){
                     mBreweryRecyclerView.scrollToPosition(positionStart);
                 }
             }
         });

        mBreweryRecyclerView.setLayoutManager(mLinearLayoutManager);
        mBreweryRecyclerView.setAdapter(mFirebaseAdapter);

        mAddBreweryButton = (FloatingActionButton)findViewById(R.id.button_add_brewery);
        mAddBreweryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(BreweryListActivity.this, AddBreweryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBreweryViewItemClick(String id) {
        //create brewery detail intent
        Intent breweryDetailIntent = new Intent(BreweryListActivity.this, BreweryDetailViewActivity.class);
        breweryDetailIntent.putExtra(BreweryDetailViewActivity.EXTRA_BREWERY_ID, id);
        startActivity(breweryDetailIntent);
    }
}
