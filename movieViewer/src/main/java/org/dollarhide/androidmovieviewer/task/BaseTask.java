package org.dollarhide.androidmovieviewer.task;

import android.os.AsyncTask;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;

import java.util.concurrent.CountDownLatch;

public abstract class BaseTask extends AsyncTask {

    private CountDownLatch signal;

    public BaseTask() {
        this.signal = new CountDownLatch(1);
    }

    public BaseTask(CountDownLatch signal) {
        this.signal = signal;
    }

    @Override
    protected void onPostExecute(Object result) {
        if (signal != null) {
            signal.countDown();
        }
    }

    protected abstract boolean checkParamTypes(Object[] params);

    protected boolean checkParamsViable(String tag, Object[] params, int expectedParamLength) {
        if (params != null && params.length == expectedParamLength) {
            return true;
        }

        LoggingUtil.logDebug(tag, "Incorrect number of params found. Expecting " + expectedParamLength + ", but actually " + params.length);
        return false;
    }

    public void setSignal(CountDownLatch signal) {
        this.signal = signal;
    }

    public CountDownLatch getSignal() {
        return signal;
    }
}
