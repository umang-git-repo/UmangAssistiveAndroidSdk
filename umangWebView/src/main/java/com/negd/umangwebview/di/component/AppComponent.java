package com.negd.umangwebview.di.component;

import android.app.Application;
import com.negd.umangwebview.UmangApplication;
import com.negd.umangwebview.di.builder.ActivityBuilder;
import com.negd.umangwebview.di.module.AppModule;
import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(UmangApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}

