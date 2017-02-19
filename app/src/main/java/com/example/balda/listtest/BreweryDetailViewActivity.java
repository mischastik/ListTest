package com.example.balda.listtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.balda.listtest.Models.Brewery;
import com.example.balda.listtest.Utilities.ImageUtilities;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class BreweryDetailViewActivity extends AppCompatActivity {
    public static final String EXTRA_BREWERY_ID = "BreweryID";

    private DatabaseReference mFirebaseDatabaseReference;
    private StorageReference mStorageRef;
    private TextView mBreweryDetailName;
    private TextView mBreweryDetailLocation;
    private ImageView mBreweryDetailLogo;

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
        String id = myIntent.getStringExtra(EXTRA_BREWERY_ID);

        Query query = mFirebaseDatabaseReference.child(MainActivity.BREWERIES_CHILD).child(id);
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


    }
}
