package edu.ualr.recyclerviewasignment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.viewmodel.DeviceViewModel;

public class EditDeviceFragment extends BottomSheetDialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String DEVICE_POSITION_KEY = "DevicePositionKey";
    private ImageView deviceImage;
    private EditText deviceName;
    private TextView deviceStatus;
    private Spinner deviceTypeSpinner;
    private DeviceViewModel mViewModel;
    private int position;
    private Device device;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(DEVICE_POSITION_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.device_detail_fragment, container, false);
        initBottomSheet(view);
        return view;
    }

    private void initBottomSheet(View view) {
        Button saveButton = view.findViewById(R.id.save_btn);
        saveButton.setOnClickListener(this);

        mViewModel = ViewModelProviders.of(this.getActivity()).get(DeviceViewModel.class);
        device = mViewModel.getDeviceAt(position);

        deviceTypeSpinner = view.findViewById(R.id.device_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.device_type_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deviceTypeSpinner.setAdapter(adapter);
        deviceTypeSpinner.setOnItemSelectedListener(this);

        deviceImage = view.findViewById(R.id.image_icon);
        if (device.getDeviceType().toString().equals("Unknown")) {
            deviceImage.setImageResource(R.drawable.ic_unknown_device);
            deviceTypeSpinner.setSelection(0);
        }
        else if (device.getDeviceType().toString().equals("Desktop")) {
            deviceImage.setImageResource(R.drawable.ic_pc);
            deviceTypeSpinner.setSelection(1);
        }
        else if (device.getDeviceType().toString().equals("Laptop")) {
            deviceImage.setImageResource(R.drawable.ic_laptop);
            deviceTypeSpinner.setSelection(2);
        }
        else if (device.getDeviceType().toString().equals("Smartphone")) {
            deviceImage.setImageResource(R.drawable.ic_phone_android);
            deviceTypeSpinner.setSelection(3);
        }
        else if (device.getDeviceType().toString().equals("Tablet")) {
            deviceImage.setImageResource(R.drawable.ic_tablet_android);
            deviceTypeSpinner.setSelection(4);
        }
        else if (device.getDeviceType().toString().equals("SmartTV")) {
            deviceImage.setImageResource(R.drawable.ic_tv);
            deviceTypeSpinner.setSelection(5);
        }
        else if (device.getDeviceType().toString().equals("GameConsole")) {
            deviceImage.setImageResource(R.drawable.ic_gameconsole);
            deviceTypeSpinner.setSelection(6);
        }

        ImageView deviceStatusMark = view.findViewById(R.id.detail_status_mark);
        if (device.getDeviceStatus().toString().equals("Connected")) {
            deviceStatusMark.setImageResource(R.drawable.status_mark_connected);
        }
        else if (device.getDeviceStatus().toString().equals("Ready")) {
            deviceStatusMark.setImageResource(R.drawable.status_mark_ready);
        }
        else {
            RelativeLayout deviceThumbnail = view.findViewById(R.id.detail_thumbnail_image);
            deviceThumbnail.setBackground(getResources().getDrawable(R.drawable.thumbnail_background_wire));
            deviceImage.setColorFilter(getResources().getColor(R.color.grey_60));
            deviceStatusMark.setVisibility(View.INVISIBLE);
        }

        deviceName = view.findViewById(R.id.detail_device_name_edittext);
        deviceName.setHint(device.getName());

        deviceStatus = view.findViewById(R.id.detail_device_status);
        deviceStatus.setText(device.getDeviceStatus().toString());

        TextView deviceLastConnection = view.findViewById(R.id.last_time_connection_textview);
        if (device.getDeviceStatus().toString().equals("Connected")) {
            deviceLastConnection.setText(R.string.currently_connected);
        }
        else {
            if (device.getLastConnection() != null) {
                deviceLastConnection.setText(R.string.recently);
            }
            else {
                deviceLastConnection.setText(R.string.never_connected);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(MainActivity.TAG, "Clicked on button: Save Button.");
        device.setName(deviceName.getText().toString());
        device.setDeviceType(Device.DeviceType.valueOf(deviceTypeSpinner.getSelectedItem().toString().replaceAll(" ", "").replaceAll("c", "C")));
        device.setDeviceStatus(Device.DeviceStatus.valueOf(deviceStatus.getText().toString()));
        device.setDeviceId(deviceName.getText().toString().concat("-").concat(String.valueOf(position)));
        mViewModel.setDeviceAt(position, device);
        this.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(MainActivity.TAG, "Device type spinner, current item selected: " + adapterView.getItemAtPosition(i).toString() + ".");
        if (adapterView.getItemAtPosition(i).toString().equals("Unknown"))
            deviceImage.setImageResource(R.drawable.ic_unknown_device);
        else if (adapterView.getItemAtPosition(i).toString().equals("Desktop"))
            deviceImage.setImageResource(R.drawable.ic_pc);
        else if (adapterView.getItemAtPosition(i).toString().equals("Laptop"))
            deviceImage.setImageResource(R.drawable.ic_laptop);
        else if (adapterView.getItemAtPosition(i).toString().equals("Tablet"))
            deviceImage.setImageResource(R.drawable.ic_tablet_android);
        else if (adapterView.getItemAtPosition(i).toString().equals("Smartphone"))
            deviceImage.setImageResource(R.drawable.ic_phone_android);
        else if (adapterView.getItemAtPosition(i).toString().equals("Smart TV"))
            deviceImage.setImageResource(R.drawable.ic_tv);
        else if (adapterView.getItemAtPosition(i).toString().equals("Gameconsole"))
            deviceImage.setImageResource(R.drawable.ic_gameconsole);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    static EditDeviceFragment newInstance(int pos) {
        EditDeviceFragment editDeviceFragment = new EditDeviceFragment();
        Bundle args = new Bundle();
        args.putInt(DEVICE_POSITION_KEY, pos);
        editDeviceFragment.setArguments(args);
        return editDeviceFragment;
    }
}
