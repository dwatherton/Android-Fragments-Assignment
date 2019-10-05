package edu.ualr.recyclerviewasignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import edu.ualr.recyclerviewasignment.adapter.DeviceListAdapter;
import edu.ualr.recyclerviewasignment.data.DataGenerator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DeviceListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView(){
        mAdapter = new DeviceListAdapter(this);
        mRecyclerView = findViewById(R.id.devices_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addAll(DataGenerator.getDevicesDataset(5));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
