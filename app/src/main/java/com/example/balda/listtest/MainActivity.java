package com.example.balda.listtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

import com.example.balda.listtest.Models.Brewery;
import com.example.balda.listtest.Utilities.ImageUtilities;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, BreweryViewHolder.BreweryViewHolderOnClickHandler{

    public static final String ANONYMOUS = "anonymous";
    private static final String TAG = "MainActivity";

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Brewery, BreweryViewHolder> mFirebaseAdapter;
    private StorageReference mStorageRef;

    private String mUsername;
    private String mPhotoUrl;
    private RecyclerView mBreweryRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton mAddBreweryButton;
    private ProgressBar mProgressBar;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BreweryViewHolder.mClickHandler = this;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = ANONYMOUS;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, 1, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        // Initialize ProgressBar and RecyclerView.
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mBreweryRecyclerView = (RecyclerView) findViewById(R.id.breweryRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Brewery, BreweryViewHolder>(
                Brewery.class,
                R.layout.brewery_list_item,
                BreweryViewHolder.class,
                mFirebaseDatabaseReference.child(getString(R.string.breweries_child)).orderByChild("name")) {

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
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.breweryNameTextView.setText(brewery.getName());
                viewHolder.breweryLocationTextView.setText(brewery.getLocation());
                viewHolder.id = brewery.getId();
                ImageUtilities.setLogoImage(getApplicationContext(), brewery.getLogoFileID(), viewHolder.breweryLogoImageView, mStorageRef);
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int breweryCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (breweryCount - 1) && lastVisiblePosition == (positionStart - 1))) {
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
                Intent intent = new Intent(MainActivity.this, AddBreweryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBreweryViewItemClick(String id) {
        //create brewery detail intent
        Intent breweryDetailIntent = new Intent(MainActivity.this, BreweryDetailViewActivity.class);
        breweryDetailIntent.putExtra(BreweryDetailViewActivity.EXTRA_BREWERY_ID, id);
        startActivity(breweryDetailIntent);
    }
}
