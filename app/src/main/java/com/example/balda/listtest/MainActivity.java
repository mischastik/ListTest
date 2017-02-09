package com.example.balda.listtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity implements ListAdapter.ListItemClickListener{
    private ListAdapter mAdapter;
    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (RecyclerView) findViewById(R.id.rv_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mList.setLayoutManager(layoutManager);
        mList.setHasFixedSize(true);

        mAdapter = new ListAdapter(10, this);
        mList.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        // TODO: Start new activity.
    }
}
