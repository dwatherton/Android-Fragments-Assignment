package edu.ualr.recyclerviewasignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import edu.ualr.recyclerviewasignment.R;
import edu.ualr.recyclerviewasignment.data.DeviceDataFormatTools;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;

/**
 * Created by irconde on 2019-10-04.
 */
public class DeviceListAdapter extends RecyclerView.Adapter {

    private static final int DEVICE_VIEW = 0;
    private static final int SECTION_VIEW = 1;

    private List<DeviceListItem> mItems;
    private Context mContext;

    public DeviceListAdapter(Context context, List<DeviceListItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == DEVICE_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_item, parent, false);
            vh = new DeviceViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_header_layout, parent, false);
            vh = new SectionViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceListItem item = mItems.get(position);
        if (holder instanceof DeviceViewHolder) {
            DeviceViewHolder view = (DeviceViewHolder) holder;
            Device device = (Device) item;
            view.name.setText(device.getName());
            view.elapsedTimeLabel.setText(DeviceDataFormatTools.getTimeSinceLastConnection(mContext, device));
            DeviceDataFormatTools.setDeviceStatusMark(mContext, view.statusMark, device);
            DeviceDataFormatTools.setDeviceThumbnail(mContext, view.image, device);
            DeviceDataFormatTools.setConnectionBtnLook(mContext, view.connectBtn, device.getDeviceStatus());
        } else {
            /*
            SectionViewHolder view = (SectionViewHolder) holder;
            AncoraDeviceSection section = (AncoraDeviceSection) p;
            int numItems = section.getNumItems();
            if (numItems == 0) {
                view.title_section.setVisibility(View.INVISIBLE);
                return;
            }
            view.title_section.setVisibility(View.VISIBLE);
            view.title_section_label.setText(section.getLabel());
            view.title_section_num_elems.setText(" - " + String.valueOf(section.getNumItems()));
             */
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.mItems.get(position).isSection() ? SECTION_VIEW : DEVICE_VIEW;
    }

    private class SectionViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout title_section;
        public TextView title_section_label;
        public TextView title_section_num_elems;

        public SectionViewHolder(View v) {
            super(v);
            title_section = v.findViewById(R.id.title_section);
            title_section_label = v.findViewById(R.id.title_section_label);
            title_section_num_elems = v.findViewById(R.id.title_section_num_elems);
        }
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout image;
        private ImageView statusMark;
        private TextView name;
        private TextView elapsedTimeLabel;
        private View deviceItemContainer;
        private ImageButton connectBtn;

        public DeviceViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            statusMark = v.findViewById(R.id.status_mark);
            name = v.findViewById(R.id.name);
            elapsedTimeLabel = v.findViewById(R.id.elapsed_time);
            deviceItemContainer = v.findViewById(R.id.device_item_container);
            connectBtn = v.findViewById(R.id.device_connect_btn);
            connectBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            toggleConnection();
        }

        private void toggleConnection() {
            Device device = (Device) mItems.get(getAdapterPosition());
            Device.DeviceStatus deviceStatus = device.getDeviceStatus();
            if (deviceStatus == Device.DeviceStatus.Connected) {
                device.setDeviceStatus(Device.DeviceStatus.Ready);
                device.setLastConnection(new Date());
            } else if (deviceStatus == Device.DeviceStatus.Ready) {
                device.setDeviceStatus(Device.DeviceStatus.Connected);
            }
            notifyItemChanged(getAdapterPosition());
        }
    }
}
