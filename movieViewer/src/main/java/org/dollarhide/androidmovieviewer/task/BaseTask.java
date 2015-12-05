package org.dollarhide.androidmovieviewer.task;

import android.os.AsyncTask;
import android.util.Log;

public abstract class BaseTask extends AsyncTask {

    protected abstract boolean checkParamTypes(Object[] params);

    protected boolean checkParamsViable(String tag, Object[] params, int expectedParamLength) {
        if (params != null && params.length == expectedParamLength) {
            return true;
        }

        Log.d(tag, "Incorrect number of params found. Expecting " + expectedParamLength + ", but actually " + params.length);
        return false;
    }
}
