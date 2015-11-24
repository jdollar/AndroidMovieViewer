package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dollarhide.androidmovieviewer.util.ConfigurationManager;

public abstract class BaseService {
    public static String API_KEY = "51dd2e08c99cf8915af97d62646b87bb";
    public static String BASE_URL = "http://api.themoviedb.org/3/";
    public static String API_REQUEST_PARAM = "api_key=" + API_KEY;

    private ConfigurationManager configurationManager;
    private RequestQueue requestQueue;
    private HttpClient httpClient;

    protected RequestQueue getRequestQueue(Activity activity) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        }

        return requestQueue;
    }

    protected HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }

        return httpClient;
    }
}
