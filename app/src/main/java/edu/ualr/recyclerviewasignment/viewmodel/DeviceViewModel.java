package edu.ualr.recyclerviewasignment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class DeviceViewModel extends ViewModel {

    private SavedStateHandle mState;
    private static final String DEVICE_NAME = "DeviceName";
    private static final String DEVICE_TYPE = "DeviceType";
    private static final String DEVICE_STATUS = "DeviceStatus";
    private static final String DEVICE_LAST_CONNECTION = "DeviceLastConnection";

    public DeviceViewModel(SavedStateHandle savedStateHandle) {
        this.mState = savedStateHandle;
    }

    public LiveData<String> getDeviceName() {
        return this.mState.getLiveData(DEVICE_NAME);
    }

    public void setDeviceName(String deviceName) {
        this.mState.set(DEVICE_NAME, deviceName);
    }

    public LiveData<String> getDeviceType() {
        return this.mState.getLiveData(DEVICE_TYPE);
    }

    public void setDeviceType(String deviceType) {
        this.mState.set(DEVICE_TYPE, deviceType);
    }

    public LiveData<String> getDeviceStatus() {
        return this.mState.getLiveData(DEVICE_STATUS);
    }

    public void setDeviceStatus(String deviceStatus) {
        this.mState.set(DEVICE_STATUS, deviceStatus);
    }

    public LiveData<String> getLastConnection() {
        return this.mState.getLiveData(DEVICE_LAST_CONNECTION);
    }

    public void setDeviceLastConnection(String deviceLastConnection) {
        this.mState.set(DEVICE_LAST_CONNECTION, deviceLastConnection);
    }
}
