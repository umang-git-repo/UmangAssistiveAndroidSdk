package com.negd.umangwebview.ui.jeevan_pramaan.device_detail_screen;

import com.negd.umangwebview.data.DataManager;
import com.negd.umangwebview.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;


@Module
public class DeviceInfoModule {

    @Provides
    DeviceInfoViewModel provideDeviceInfoViewModel(DataManager dataManager, SchedulerProvider provider){
        return new DeviceInfoViewModel(dataManager,provider);
    }
}
