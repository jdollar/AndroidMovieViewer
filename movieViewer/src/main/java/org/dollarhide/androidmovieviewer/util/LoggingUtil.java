package org.dollarhide.androidmovieviewer.util;

import android.util.Log;

public class LoggingUtil {
    public static void logException(String tag, Exception e) {
        Log.e(tag, Log.getStackTraceString(e));
    }

    public static void logDebug(String tag, String message) {
        Log.d(tag, message);
    }
}
