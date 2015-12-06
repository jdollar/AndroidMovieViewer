package org.dollarhide.androidmovieviewer.util;

import android.content.Context;
import org.dollarhide.androidmovieviewer.application.MovieViewApplication;

import java.io.IOException;
import java.util.Properties;

public class ResourcePropertyReader {

    private static final String TAG = "ResourcePropertyReader";

    private static final String API_PROPERTIES_FILE_NAME = "api.properties";
    private static final String API_KEY_PARAM = "api_key";
    private static final String BASE_URL_PARAM = "base_url";

    private static Properties apiProperties;

    public static String getApiKey() {
        return getApiProperties().getProperty(API_KEY_PARAM);
    }

    public static String getProperty(String param) {
        return getApiProperties().getProperty(param);
    }

    public static String getBaseApiUrl() {
        return getApiProperties().getProperty(BASE_URL_PARAM);
    }

    private static Properties getApiProperties() {
        if (apiProperties == null) {
            try {
                Context baseContext = MovieViewApplication.getContext();
                apiProperties = new Properties();
                apiProperties.load(baseContext.getAssets().open(API_PROPERTIES_FILE_NAME));
            } catch (IOException e) {
                LoggingUtil.logException(TAG, e);
            }
        }

        return apiProperties;
    }

    //used for testing
    public static void setApiProperties(Properties apiProperties) {
        ResourcePropertyReader.apiProperties = apiProperties;
    }

}
