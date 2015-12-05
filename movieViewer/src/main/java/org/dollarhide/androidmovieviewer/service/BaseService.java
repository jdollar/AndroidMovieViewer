package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;

public abstract class BaseService {
    private RequestQueue requestQueue;
    private HttpClient httpClient;

    protected RequestQueue getRequestQueue(Activity activity) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        }

        return requestQueue;
    }

    protected String getApiKeyRequestParam(Context baseContext) {
        return "api_key=" + ResourcePropertyReader.getApiKey(baseContext);
    }

    protected String getBaseUrl(Context baseContext) {
        return ResourcePropertyReader.getBaseApiUrl(baseContext);
    }

    protected HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }

        return httpClient;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
