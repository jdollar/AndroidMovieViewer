package org.dollarhide.androidmovieviewer.activity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public abstract class BaseActivityTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    private BaseMovieViewerActivity movieViewerActivity;

    public BaseActivityTestCase(Class classType) {
        super(classType);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        movieViewerActivity = (BaseMovieViewerActivity) super.getActivity();
    }

    protected BaseMovieViewerActivity getMovieViewerActivity() {
        return movieViewerActivity;
    }
}
