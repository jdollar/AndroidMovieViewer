package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

public class MovieService extends BaseService {
    private static final String TAG = "MovieService";
    protected static String MOVIE_SERVICE_URL = "movie";

    public void getBasicInfo(Activity activity, int query, Response.Listener<JSONObject> basicInfoListener, Response.ErrorListener basicErrorListener) {
        String url = BASE_URL + MOVIE_SERVICE_URL + "/" + query + "?api_key=" + API_KEY;

        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, basicInfoListener, basicErrorListener);

        getRequestQueue(activity).add(jsonObjectRequest);
    }
}
