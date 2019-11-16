package edu.ualr.recyclerviewasignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Homework_8";
    private DeviceListFragment deviceListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        deviceListFragment = ((DeviceListFragment) getSupportFragmentManager().findFragmentById(R.id.device_list_fragment));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        menu.findItem(R.id.show_linked).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "Clicked on menu option: " + item.getTitle() + ".");
        switch (item.getItemId()) {
            case R.id.connect_all:
                deviceListFragment.connectAll();
                return true;
            case R.id.disconnect_all:
                deviceListFragment.disconnectAll();
                return true;
            case R.id.show_linked:
                item.setChecked(!item.isChecked());
                deviceListFragment.toggleLinked(item.isChecked());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
