package com.example.balda.listtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BeerDetailViewActivity extends AppCompatActivity {
    public static final String EXTRA_BEER_ID = "BeerID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail_view);
    }
}
