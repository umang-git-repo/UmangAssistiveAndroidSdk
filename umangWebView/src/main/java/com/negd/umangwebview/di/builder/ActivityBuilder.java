package com.negd.umangwebview.di.builder;


import com.negd.umangwebview.ui.jeevan_pramaan.device_detail_screen.DeviceInfoActivity;
import com.negd.umangwebview.ui.jeevan_pramaan.device_detail_screen.DeviceInfoModule;
import com.negd.umangwebview.ui.jeevan_pramaan.device_select_screen.JPDeviceSelectActivity;
import com.negd.umangwebview.ui.jeevan_pramaan.device_select_screen.JPDeviceSelectModule;
import com.negd.umangwebview.ui.jeevan_pramaan.other_device_screen.OtherDeviceActivity;
import com.negd.umangwebview.ui.jeevan_pramaan.other_device_screen.OtherDeviceModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {JPDeviceSelectModule.class})
    abstract JPDeviceSelectActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {DeviceInfoModule.class})
    abstract DeviceInfoActivity bindDeviceInfoActivity();

    @ContributesAndroidInjector(modules = {OtherDeviceModule.class})
    abstract OtherDeviceActivity bindOtherdeviceActivity();

}
