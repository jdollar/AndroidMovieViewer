package org.dollarhide.androidmovieviewer.application;

import android.app.Application;
import android.content.Context;

public class MovieViewApplication extends Application{
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getContext() {
        return applicationContext;
    }
}
