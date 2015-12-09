package org.dollarhide.androidmovieviewer.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;

public class MovieViewApplication extends Application{

    protected static final String PREF_FILE_PARAM = "preference_file";

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getContext() {
        return applicationContext;
    }

    public static SharedPreferences getMovieViewerSharedPreferences() {
        return getContext().getSharedPreferences(ResourcePropertyReader.getProperty(PREF_FILE_PARAM), 0);
    }
}
