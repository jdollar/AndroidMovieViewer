package org.dollarhide.androidmovieviewer.task;

import android.os.AsyncTask;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;

public abstract class BaseTask extends AsyncTask {

    protected abstract boolean checkParamTypes(Object[] params);

    protected boolean checkParamsViable(String tag, Object[] params, int expectedParamLength) {
        if (params != null && params.length == expectedParamLength) {
            return true;
        }

        LoggingUtil.logDebug(tag, "Incorrect number of params found. Expecting " + expectedParamLength + ", but actually " + params.length);
        return false;
    }
}
