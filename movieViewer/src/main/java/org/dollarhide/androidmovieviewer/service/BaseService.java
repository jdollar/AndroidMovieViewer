package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public abstract class BaseService {
    protected static String API_KEY = "51dd2e08c99cf8915af97d62646b87bb";
    protected static String BASE_URL = "http://api.themoviedb.org/3/";

    private RequestQueue requestQueue;

    protected RequestQueue getRequestQueue(Activity activity) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        }

        return requestQueue;
    }
}
