package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import org.dollarhide.androidmovieviewer.util.ConfigurationManager;

public abstract class BaseService {
    protected static String API_KEY = "51dd2e08c99cf8915af97d62646b87bb";
    protected static String BASE_URL = "http://api.themoviedb.org/3/";
    protected static String API_REQUEST_PARAM = "api_key=" + API_KEY;

    private ConfigurationManager configurationManager;
    private RequestQueue requestQueue;

    protected RequestQueue getRequestQueue(Activity activity) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        }

        return requestQueue;
    }

    public ConfigurationManager getConfigurationManager(Activity baseActivity) {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager(baseActivity);
        }

        return configurationManager;
    }
}
