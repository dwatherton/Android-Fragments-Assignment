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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import edu.ualr.recyclerviewasignment.viewmodel.DeviceViewModel;

public class EditDeviceActivity extends BottomSheetDialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button saveButton;
    private RelativeLayout deviceThumbnail;
    private ImageView deviceImage;
    private ImageView deviceStatusMark;
    private EditText deviceName;
    private TextView deviceStatus;
    private Spinner deviceTypeSpinner;
    private TextView deviceLastConnection;
    private DeviceViewModel mViewModel;
    private MainActivity mainActivity;

    public EditDeviceActivity(DeviceViewModel mViewModel, MainActivity mainActivity) {
        this.mViewModel = mViewModel;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.device_detail_fragment, container, false);
        initBottomSheet(view);
        return view;
    }

    private void initBottomSheet(View view) {
        saveButton = view.findViewById(R.id.save_btn);
        saveButton.setOnClickListener(this);

        deviceTypeSpinner = view.findViewById(R.id.device_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.device_type_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deviceTypeSpinner.setAdapter(adapter);
        deviceTypeSpinner.setOnItemSelectedListener(this);

        deviceImage = view.findViewById(R.id.image_icon);
        if (Objects.equals(mViewModel.getDeviceType().getValue(), "Unknown")) {
            deviceImage.setImageResource(R.drawable.ic_unknown_device);
            deviceTypeSpinner.setSelection(0);
        }
        else if (Objects.equals(mViewModel.getDeviceType().getValue(), "Desktop")) {
            deviceImage.setImageResource(R.drawable.ic_pc);
            deviceTypeSpinner.setSelection(1);
        }
        else if (Objects.equals(mViewModel.getDeviceType().getValue(), "Laptop")) {
            deviceImage.setImageResource(R.drawable.ic_laptop);
            deviceTypeSpinner.setSelection(2);
        }
        else if (Objects.equals(mViewModel.getDeviceType().getValue(), "Tablet")) {
            deviceImage.setImageResource(R.drawable.ic_tablet_android);
            deviceTypeSpinner.setSelection(3);
        }
        else if (Objects.equals(mViewModel.getDeviceType().getValue(), "Smartphone")) {
            deviceImage.setImageResource(R.drawable.ic_phone_android);
            deviceTypeSpinner.setSelection(4);
        }
        else if (Objects.equals(mViewModel.getDeviceType().getValue(), "SmartTV")) {
            deviceImage.setImageResource(R.drawable.ic_tv);
            deviceTypeSpinner.setSelection(5);
        }
        else if (Objects.equals(mViewModel.getDeviceType().getValue(), "GameConsole")) {
            deviceImage.setImageResource(R.drawable.ic_gameconsole);
            deviceTypeSpinner.setSelection(6);
        }

        deviceStatusMark = view.findViewById(R.id.detail_status_mark);
        if (Objects.equals(mViewModel.getDeviceStatus().getValue(), "Connected")) {
            deviceStatusMark.setImageResource(R.drawable.status_mark_connected);
        }
        else if (Objects.equals(mViewModel.getDeviceStatus().getValue(), "Ready")) {
            deviceStatusMark.setImageResource(R.drawable.status_mark_ready);
        }
        else {
            deviceThumbnail = view.findViewById(R.id.detail_thumbnail_image);
            deviceThumbnail.setBackground(getResources().getDrawable(R.drawable.thumbnail_background_wire));
            deviceImage.setColorFilter(getResources().getColor(R.color.grey_60));
            deviceStatusMark.setVisibility(View.INVISIBLE);
        }

        deviceName = view.findViewById(R.id.detail_device_name_edittext);
        deviceName.setHint(mViewModel.getDeviceName().getValue());

        deviceStatus = view.findViewById(R.id.detail_device_status);
        deviceStatus.setText(mViewModel.getDeviceStatus().getValue());

        deviceLastConnection = view.findViewById(R.id.last_time_connection_textview);
        deviceLastConnection.setText(mViewModel.getLastConnection().getValue());
    }

    @Override
    public void onClick(View view) {
        Log.d(MainActivity.TAG, "Clicked on button: Save Button!");
        // TODO: Save Device Info
        mViewModel.setDeviceName(deviceName.getText().toString());
        mViewModel.setDeviceType(deviceTypeSpinner.getSelectedItem().toString());
        mViewModel.setDeviceStatus(deviceStatus.getText().toString());
        mViewModel.setDeviceLastConnection(deviceLastConnection.getText().toString());

        mViewModel.getDeviceName().removeObservers(mainActivity);
        mViewModel.getDeviceType().removeObservers(mainActivity);

        this.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(MainActivity.TAG, "Device Type Spinner Item Selected: " + adapterView.getItemAtPosition(i).toString());
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
}
