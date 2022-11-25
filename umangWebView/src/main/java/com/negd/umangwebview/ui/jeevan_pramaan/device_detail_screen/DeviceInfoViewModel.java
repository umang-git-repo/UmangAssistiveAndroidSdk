package com.negd.umangwebview.ui.jeevan_pramaan.device_detail_screen;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;
import androidx.databinding.ObservableField;
import com.negd.umangwebview.BaseViewModel;
import com.negd.umangwebview.R;
import com.negd.umangwebview.data.DataManager;
import com.negd.umangwebview.data.model.jp.DeviceData;
import com.negd.umangwebview.utils.AppLogger;
import com.negd.umangwebview.utils.rx.SchedulerProvider;

public class DeviceInfoViewModel extends BaseViewModel<IDeviceInfoNavigator> {

    public final String TAG= DeviceInfoViewModel.class.getSimpleName();

    private DeviceData deviceData;
    private DeviceInfoActivity context;
    public ObservableField<String> observableFieldname;
    public ObservableField<String> img;
    public ObservableField<String> observableFieldmake;
    public ObservableField<String> appHeading;
    public ObservableField<String> appDesc;
    public ObservableField<String> note;
    public ObservableField<String> regDevHeading;
    public ObservableField<String> regDevDesc;
    public ObservableField<String> helpHeading;
    public ObservableField<String> helpDesc;
    public ObservableField<String> observableFieldemail;
    public ObservableField<String> phone;
    public ObservableField<String> userManual;

    public DeviceInfoViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setDeviceData(DeviceData deviceData,DeviceInfoActivity context){
        this.deviceData=deviceData;
        this.context=context;
        observableFieldname=new ObservableField<>(deviceData.getName());
        img=new ObservableField<>(deviceData.getImg());
        observableFieldmake=new ObservableField<>(deviceData.getMake());
        appHeading=new ObservableField<>(deviceData.getAppHeading());
        appDesc=new ObservableField<>(deviceData.getAppDesc());
        note=new ObservableField<>(deviceData.getNote());
        regDevHeading=new ObservableField<>(deviceData.getRegDevHeading());
        regDevDesc=new ObservableField<>(deviceData.getRegDevDesc());
        helpHeading=new ObservableField<>(deviceData.getHelpHeading());
        helpDesc=new ObservableField<>(deviceData.getHelpDesc());
        observableFieldemail=new ObservableField<>(deviceData.getEmail());
        phone=new ObservableField<>(deviceData.getPhone());
        userManual=new ObservableField<>(deviceData.getUserManual());
    }

    public void setInfoMessage(String msg){
        note.set(msg);
    }

    public void clickUserManual(){
        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(deviceData.getUserManual()));
            context.startActivity(i);
        } catch (Exception e) {
            AppLogger.e(TAG, "Error in clickUserManual",e);
        }
    }

    public void clickEmailLayout(){
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{deviceData.getEmail()});
            context.startActivity(intent);
        }catch (Exception ex){

        }
    }

    public void clickPhoneLayout(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + deviceData.getPhone()));
        context.startActivity(intent);
    }

    public void nextBtnClick(){
        for (int i = 0; i < deviceData.getAppList().size(); i++) {
            if (!appInstalledOrNot(deviceData.getAppList().get(i).getPkgName())) {
                Toast.makeText(context, context.getString(R.string.above_apps_needs_to_be_installed), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent i = new Intent();
        i.putExtra("successCallback", context.getIntent().getStringExtra("successCallback"));
        i.putExtra("failureCallback", context.getIntent().getStringExtra("failureCallback"));
        context.setResult(RESULT_OK, i);
        context.finish();
    }

    public boolean appInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}
