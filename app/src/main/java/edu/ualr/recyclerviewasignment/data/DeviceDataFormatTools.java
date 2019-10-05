package edu.ualr.recyclerviewasignment.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Date;

import edu.ualr.recyclerviewasignment.R;
import edu.ualr.recyclerviewasignment.model.Device;

/**
 * Created by irconde on 2019-10-05.
 */
public class DeviceDataFormatTools {
    public static final int NO_SELECTION = -1;

    public static void setDeviceThumbnail(Context context, RelativeLayout thumbnail, Device device) {
        setDeviceThumbnail(context, thumbnail, device.getDeviceType(), device.getDeviceStatus(), -1);
    }

    public static void setConnectionBtnLook(Context context, ImageButton button, Device.DeviceStatus deviceStatus) {
        switch (deviceStatus) {
            case Connected:
                button.setVisibility(View.VISIBLE);
                button.setImageDrawable(context.getDrawable(R.drawable.ic_btn_disconnect));
                break;
            case Ready:
                button.setVisibility(View.VISIBLE);
                button.setImageDrawable(context.getDrawable(R.drawable.ic_btn_connect));
                break;
            case Linked:
                button.setVisibility(View.GONE);
        }
    }

    public static void setDeviceThumbnail(
            Context context, RelativeLayout thumbnail,
            Device.DeviceType deviceType, Device.DeviceStatus deviceStatus, int color) {
        int deviceTypeId = deviceType.ordinal();
        ImageView thumbnailIcon = thumbnail.findViewById(R.id.image_icon);
        TypedArray drw_arr = context.getResources().obtainTypedArray(R.array.device_images);
        int iconId = drw_arr.getResourceId(deviceTypeId, NO_SELECTION);
        // Thumbnail icon
        if (iconId == NO_SELECTION) {
            thumbnailIcon.setImageDrawable(context.getDrawable(R.drawable.ic_unknown_device));
        } else {
            thumbnailIcon.setImageDrawable(context.getDrawable(iconId));
        }
        // Thumbnail background
        if (deviceStatus == Device.DeviceStatus.Ready || deviceStatus == Device.DeviceStatus.Connected) {
            thumbnail.setBackground(context.getDrawable(R.drawable.thumbnail_background_solid));
            thumbnailIcon.setColorFilter(context.getResources().getColor(android.R.color.white));
        } else {
            thumbnail.setBackground(context.getDrawable(R.drawable.thumbnail_background_wire));
            thumbnailIcon.setColorFilter(context.getResources().getColor(R.color.grey_40));
        }
        if (color != -1) {
            thumbnailIcon.setColorFilter(context.getResources().getColor(R.color.secondaryColor_700));
        }
    }

    public static void setDeviceStatusMark(Context context, ImageView markView, Device device) {
        Device.DeviceStatus status = device.getDeviceStatus();
        if (status == Device.DeviceStatus.Linked) {
            markView.setVisibility(View.INVISIBLE);
            return;
        }
        if (status == Device.DeviceStatus.Connected) {
            markView.setImageDrawable(context.getDrawable(R.drawable.status_mark_connected));
        } else if (status == Device.DeviceStatus.Ready) {
            markView.setImageDrawable(context.getDrawable(R.drawable.status_mark_ready));
        }
        markView.setVisibility(View.VISIBLE);
    }

    public static String getTimeSinceLastConnection(Context context, Device device) {
        if (device == null) {
            return null;
        }
        if (device.getDeviceStatus() == Device.DeviceStatus.Connected) {
            return context.getResources().getString(R.string.currently_connected);
        }
        Date lastConnectionTime = device.getLastConnection();
        if (lastConnectionTime == null) {
            return context.getResources().getString(R.string.never_connected);
        }
        return context.getResources().getString(R.string.recently);
    }
}
