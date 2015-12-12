package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;
import org.json.JSONObject;

import java.text.MessageFormat;

public class MovieService extends BaseService {
    private static final String TAG = "MovieService";
    protected static String BASIC_INFO_MOVIE_SERVICE_URL = "basic_info_movie_service_url";

    public void getBasicInfo(Activity activity, String query, Response.Listener<JSONObject> basicInfoListener, Response.ErrorListener basicErrorListener) {
        String basicInfoServiceUrl = ResourcePropertyReader.getServiceUrl(BASIC_INFO_MOVIE_SERVICE_URL);
        String url = MessageFormat.format(basicInfoServiceUrl, query, ResourcePropertyReader.getApiKey());

        LoggingUtil.logDebug(TAG, "Sending: " + url);

        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, basicInfoListener, basicErrorListener);

        getRequestQueue(activity).add(jsonObjectRequest);
    }
}
