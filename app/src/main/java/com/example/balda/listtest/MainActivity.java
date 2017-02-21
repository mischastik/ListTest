package com.example.balda.listtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button breweriesButton = (Button)findViewById(R.id.buttonShowBreweriesList);
        breweriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(MainActivity.this, BreweryListActivity.class);
                startActivity(intent);
            }
        });

        Button beerListButton = (Button)findViewById(R.id.buttonShowBeersList);
        // TODO Complete this once beer list activity is done
        //beerListButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        // Click action
        //        Intent intent = new Intent(MainActivity.this, .class);
        //        startActivity(intent);
        //    }
        //});
    }
}
