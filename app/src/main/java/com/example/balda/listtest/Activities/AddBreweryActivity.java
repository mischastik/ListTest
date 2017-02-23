package com.example.balda.listtest.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.balda.listtest.Models.Brewery;
import com.example.balda.listtest.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBreweryActivity extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabaseReference;
    private EditText mBreweryName;
    private EditText mBreweryLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_brewery);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mBreweryName = (EditText)findViewById(R.id.editTextBreweryName);
        mBreweryLocation = (EditText)findViewById(R.id.editTextBreweryLocation);
    }

    public void confirmAddBrewery(View view) {
        Brewery brewery = new Brewery(mBreweryName.getText().toString(),
                mBreweryLocation.getText().toString());
        mFirebaseDatabaseReference.child(getString(R.string.breweries_child))
                .push().setValue(brewery);
        mBreweryName.setText("");
        mBreweryLocation.setText("");
        finish();
    }
}
