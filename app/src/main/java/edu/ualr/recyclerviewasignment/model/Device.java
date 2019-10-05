package edu.ualr.recyclerviewasignment.model;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * Created by irconde on 2019-10-04.
 */
public class Device extends DeviceListItem {
    public static final int DEFAULT_PORT = 33333;
    private String deviceId;
    private String deviceUri;
    private String hostAddress;
    private int port;
    private String name;
    private DeviceStatus deviceStatus;
    private DeviceType deviceType;
    private Date lastConnection;

    public Device(@NonNull String deviceId) {
        this.lastConnection = null;
        this.isSection = false;
        this.deviceId = deviceId;
        this.name = "";
        this.deviceUri = "";
        this.deviceStatus = DeviceStatus.Linked;
        this.deviceType = DeviceType.Unknown;
        this.hostAddress = "";
        this.port = DEFAULT_PORT;
    }

    public Device(final Device device) {
        isSection = device.isSection;
        deviceId = device.getDeviceId();
        deviceUri = device.getDeviceUri();
        hostAddress = device.getHostAddress();
        port = device.getPort();
        name = device.getName();
        deviceStatus = device.getDeviceStatus();
        deviceType = device.getDeviceType();
        Date timeStamp = device.getLastConnection();
        if (timeStamp == null) {
            lastConnection = null;
        } else {
            lastConnection = (Date) timeStamp.clone();
        }
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceUri() {
        return deviceUri;
    }

    public void setDeviceUri(String deviceUri) {
        this.deviceUri = deviceUri;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
        this.deviceUri = String.format("tcp://%s:%d", hostAddress, this.port);
    }

    public void setPort(int port) {
        this.port = port;
        this.deviceUri = String.format("tcp://%s:%d", this.hostAddress, port);
    }

    public int getPort() {
        return this.port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Date getLastConnection() {
        return this.lastConnection;
    }

    public void setLastConnection(Date timeStamp) {
        this.lastConnection = timeStamp;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Device &&
                deviceId.equals(((Device) obj).getDeviceId());
    }

    public enum DeviceType {
        Unknown,
        Desktop,
        Laptop,
        Tablet,
        Smartphone,
        SmartTV,
        GameConsole
    }

    public enum DeviceStatus {
        Linked,
        Ready,
        Connected,
    }
}
