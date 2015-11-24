package org.dollarhide.androidmovieviewer.util;

import android.content.Context;

import java.io.IOException;
import java.util.Properties;

public class ResourcePropertyReader {

    private static final String TAG = "ResourcePropertyReader";

    private static final String API_PROPERTIES_FILE_NAME = "api.properties";
    private static final String API_KEY_PARAM = "api_key";
    private static final String BASE_URL_PARAM = "base_url";

    private static Properties apiProperties;

    public static String getApiKey(Context baseContext) {
        return getApiProperties(baseContext).getProperty(API_KEY_PARAM);
    }

    public static String getBaseApiUrl(Context baseContext) {
        return getApiProperties(baseContext).getProperty(BASE_URL_PARAM);
    }

    private static Properties getApiProperties(Context baseContext) {
        if (apiProperties == null) {
            try {
                apiProperties = new Properties();
                apiProperties.load(baseContext.getAssets().open(API_PROPERTIES_FILE_NAME));
            } catch (IOException e) {
                LoggingUtil.logException(TAG, e);
            }
        }

        return apiProperties;
    }


}
