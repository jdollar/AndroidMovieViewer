package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

public class ConfigurationService extends BaseService {

    private static final String CONFIG_SERVICE_URL = "configuration";
    private static final String CONFIG_CALL_URL = BASE_URL + CONFIG_SERVICE_URL + API_REQUEST_PARAM;

    public void getConfiguration(Activity activity, Response.Listener<JSONObject> configListener, Response.ErrorListener configErrorListener) {
        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CONFIG_CALL_URL, (String) null, configListener, configErrorListener);

        getRequestQueue(activity).add(jsonObjectRequest);
    }
}
