package org.dollarhide.androidmovieviewer.util;

import android.util.Log;

public class LoggingUtil {

    public static final String DEBUG_ENABLED_PARAM = "debug_enabled";

    private static Boolean debugEnabled;

    public static void logException(String tag, Exception e) {
        Log.e(tag, Log.getStackTraceString(e));
    }

    public static void logDebug(String tag, String message) {
        if (Boolean.TRUE.equals(getDebugEnabled())) {
            Log.d(tag, message);
        }
    }

    private static Boolean getDebugEnabled() {
        if (debugEnabled == null) {
            debugEnabled = Boolean.valueOf(ResourcePropertyReader.getProperty(DEBUG_ENABLED_PARAM));
        }

        return debugEnabled;
    }
}
