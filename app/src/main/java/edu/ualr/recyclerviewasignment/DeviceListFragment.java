package edu.ualr.recyclerviewasignment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ualr.recyclerviewasignment.adapter.DeviceListAdapter;
import edu.ualr.recyclerviewasignment.data.DataGenerator;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;
import edu.ualr.recyclerviewasignment.model.DeviceSection;
import edu.ualr.recyclerviewasignment.viewmodel.DeviceViewModel;

public class DeviceListFragment extends Fragment {

    private static final String EDIT_DEVICE_FRAGMENT_TAG = "EditDeviceFragment";
    private List<DeviceListItem> mLinkedItems = new ArrayList<>();
    private DeviceListAdapter mAdapter;
    private DeviceViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.device_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this.getActivity()).get(DeviceViewModel.class);
        mAdapter = new DeviceListAdapter(getActivity().getApplicationContext());
        mViewModel.setDevices(mAdapter.getDevices());
        RecyclerView mRecyclerView = view.findViewById(R.id.devices_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addAll(DataGenerator.getDevicesDataset(5));
        mAdapter.setOnItemClickListener(new DeviceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(MainActivity.TAG, String.format("The user has tapped on %s at position %s.", ((Device) mViewModel.getDevices().getValue().get(position)).getName(), position));
                EditDeviceFragment dialog = EditDeviceFragment.newInstance(position);
                dialog.show(getActivity().getSupportFragmentManager(), EDIT_DEVICE_FRAGMENT_TAG);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mViewModel.getDevices().observe(getViewLifecycleOwner(), new Observer<SortedList<DeviceListItem>>() {
            @Override
            public void onChanged(SortedList<DeviceListItem> deviceListItemSortedList) {
                Log.d(MainActivity.TAG, "Device list changed, viewmodel observer is updating the recycler view.");
            }
        });
    }

    void connectAll() {
        SortedList<DeviceListItem> devices = mViewModel.getDevices().getValue();
        for (int i = 0; i < devices.size(); i++) {
            if (!devices.get(i).isSection()) {
                Device device = (Device) devices.get(i);
                if (devices.get(i).getDeviceStatus() == Device.DeviceStatus.Ready) {
                    device.setDeviceStatus(Device.DeviceStatus.Connected);
                    devices.updateItemAt(i, device);
                }
            }
        }
        mViewModel.setDevices(devices);
    }

    void disconnectAll() {
        SortedList<DeviceListItem> devices = mViewModel.getDevices().getValue();
        for (int i = devices.size() - 1; i >= 0; i--) {
            if (!devices.get(i).isSection()) {
                Device device = (Device) devices.get(i);
                if (devices.get(i).getDeviceStatus() == Device.DeviceStatus.Connected) {
                    device.setDeviceStatus(Device.DeviceStatus.Ready);
                    device.setLastConnection(new Date());
                    devices.updateItemAt(i, device);
                }
            }
        }
        mViewModel.setDevices(devices);
    }

    void toggleLinked(boolean showLinked) {
        SortedList<DeviceListItem> devices = mViewModel.getDevices().getValue();
        if (showLinked) {
            if (!mLinkedItems.isEmpty()) {
                devices.addAll(mLinkedItems);
                mLinkedItems.clear();
            }
        }
        else {
            for (int i = devices.size() - 1; i >= 0; i--) {
                if ((devices.get(i).isSection() && ((DeviceSection) devices.get(i)).getLabel().equals("Linked")) || devices.get(i).getDeviceStatus() == Device.DeviceStatus.Linked) {
                    mLinkedItems.add(devices.get(i));
                    devices.removeItemAt(i);
                }
            }
        }
        mViewModel.setDevices(devices);
    }
}
