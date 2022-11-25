package com.negd.umangwebview.ui.jeevan_pramaan.other_device_screen;

import com.negd.umangwebview.data.DataManager;
import com.negd.umangwebview.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;


@Module
public class OtherDeviceModule {

    @Provides
    OtherDeviceViewModel provideOtherDeviceViewModel(DataManager dataManager, SchedulerProvider provider){
        return new OtherDeviceViewModel(dataManager,provider);
    }
}
