package edu.ualr.recyclerviewasignment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.SortedList;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;

public class DeviceViewModel extends ViewModel {

    private MutableLiveData<SortedList<DeviceListItem>> devices;

    public DeviceViewModel() {
        this.devices = new MutableLiveData<>();
    }

    public LiveData<SortedList<DeviceListItem>> getDevices() {
        return this.devices;
    }

    public void setDevices(SortedList<DeviceListItem> deviceList) {
        this.devices.setValue(deviceList);
    }

    public Device getDeviceAt(int position) {
        return ((Device) this.devices.getValue().get(position));
    }

    public void setDeviceAt(int position, Device device) {
        SortedList<DeviceListItem> devices = this.devices.getValue();
        devices.updateItemAt(position, device);
        setDevices(devices);
    }
}
