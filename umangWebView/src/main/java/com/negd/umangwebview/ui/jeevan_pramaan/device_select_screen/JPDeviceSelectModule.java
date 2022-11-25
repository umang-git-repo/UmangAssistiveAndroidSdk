package com.negd.umangwebview.ui.jeevan_pramaan.device_select_screen;

import com.negd.umangwebview.data.DataManager;
import com.negd.umangwebview.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class JPDeviceSelectModule {

    @Provides
    JPDeviceSelectViewModel provideJpDeviceSelectViewModel(DataManager dataManager, SchedulerProvider provider){
        return new JPDeviceSelectViewModel(dataManager,provider);
    }

    @Provides
    JPDeviceSelectAdapter provideJpDeviceSelectAdapter(){
        return new JPDeviceSelectAdapter(new ArrayList<>());
    }

}
