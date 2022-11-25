package com.negd.umangwebview.ui.jeevan_pramaan.device_select_screen;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ObservableField;

import com.negd.umangwebview.R;
import com.negd.umangwebview.data.model.jp.DeviceData;
import com.negd.umangwebview.ui.jeevan_pramaan.device_detail_screen.DeviceInfoActivity;
import com.negd.umangwebview.ui.jeevan_pramaan.other_device_screen.OtherDeviceActivity;


public class JPDeviceSelectItemViewModel {

    private final DeviceData deviceData;
    public ObservableField<String> name;
    public ObservableField<String> image;
    public ObservableField<String> make;
    private Context context;

    public JPDeviceSelectItemViewModel(DeviceData deviceData, Context context) {
        this.deviceData = deviceData;
        name=new ObservableField<>(deviceData.getName());
        image=new ObservableField<>(deviceData.getImg());
        make=new ObservableField<>(deviceData.getMake());
        this.context=context;
    }

    public void itemClick(int pos, DeviceData deviceData){
        if(deviceData.getName().equalsIgnoreCase(context.getString(R.string.other_devices))){
            Intent i = new Intent(context, OtherDeviceActivity.class);
            i.putExtra("successCallback", ((JPDeviceSelectActivity)context).getIntent().getStringExtra("successCallback"));
            i.putExtra("failureCallback", ((JPDeviceSelectActivity)context).getIntent().getStringExtra("failureCallback"));
            ((JPDeviceSelectActivity)context).startActivityForResult(i, 100);
        }else{
            Intent i = new Intent(context, DeviceInfoActivity.class);
            i.putExtra("bioDevice", this.deviceData);
            i.putExtra("successCallback", ((JPDeviceSelectActivity)context).getIntent().getStringExtra("successCallback"));
            i.putExtra("failureCallback", ((JPDeviceSelectActivity)context).getIntent().getStringExtra("failureCallback"));
            ((JPDeviceSelectActivity)context).startActivityForResult(i, 100);
        }
    }
}
