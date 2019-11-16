package edu.ualr.recyclerviewasignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import edu.ualr.recyclerviewasignment.adapter.DeviceListAdapter;
import edu.ualr.recyclerviewasignment.data.DataGenerator;
import edu.ualr.recyclerviewasignment.viewmodel.DeviceViewModel;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Homework_8";
    private RecyclerView mRecyclerView;
    private DeviceListAdapter mAdapter;
    private DeviceViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SavedStateViewModelFactory vmFactory = new SavedStateViewModelFactory(this.getApplication(), this);
        mViewModel = new ViewModelProvider(this, vmFactory).get(DeviceViewModel.class);
        initRecyclerView();
    }

    private void initRecyclerView(){
        mAdapter = new DeviceListAdapter(this, mViewModel, getSupportFragmentManager(), this);
        mRecyclerView = findViewById(R.id.devices_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addAll(DataGenerator.getDevicesDataset(5));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        menu.findItem(R.id.show_linked).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "Clicked on menu option: " + item.getTitle() + "!");
        switch (item.getItemId()) {
            case R.id.connect_all:
                // Set All Devices Status To Connected
                mAdapter.connectAll();
                return true;
            case R.id.disconnect_all:
                // Set All Devices Status To Disconnected
                mAdapter.disconnectAll();
                return true;
            case R.id.show_linked:
                // Update The "Checked" Status Of The Menu Item To The Opposite Of What It Was
                item.setChecked(!item.isChecked());
                // Show/Hide Devices With Status Of Linked
                mAdapter.showLinked(item.isChecked());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
