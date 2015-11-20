package org.dollarhide.androidmovieviewer.util;

import android.app.Activity;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.dollarhide.androidmovieviewer.service.ConfigurationService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class ConfigurationManager {

    private static final String TAG = "ConfigurationManager";

    private Activity baseActivity;

    private String baseImageUrl;
    private Set<String> posterSizes = new HashSet<String>();

    private ConfigurationService configurationService;

    public ConfigurationManager(Activity baseActivity) {
        configurationService = new ConfigurationService();
        this.baseActivity = baseActivity;

        configurationService.getConfiguration(baseActivity, configurationListener(), configurationErrorListener());
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public Set<String> getPosterSizes() {
        return posterSizes;
    }

    private Response.Listener<JSONObject> configurationListener() {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    baseImageUrl = response.get("base_url").toString();
                    JSONArray posterSizesJson = (JSONArray) response.get("poster_sizes");
                    for(int i = 0; i < posterSizesJson.length(); i++) {
                        posterSizes.add(posterSizesJson.get(i).toString());
                    }
                } catch (JSONException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        };
    }

    private Response.ErrorListener configurationErrorListener() {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        };
    }

}
