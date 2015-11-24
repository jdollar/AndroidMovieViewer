package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import android.content.SyncRequest;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.dollarhide.androidmovieviewer.util.ResponseParserUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class ConfigurationService extends BaseService {

    private static final String TAG = "ConfigurationService";

    private static final String CONFIG_SERVICE_URL = "configuration";
    private static final String CONFIG_CALL_URL = BASE_URL + CONFIG_SERVICE_URL + "?" + API_REQUEST_PARAM;

    public JSONObject getConfiguration() throws IOException, JSONException {
        LoggingUtil.logDebug(TAG, "Sending: " + CONFIG_CALL_URL);

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(CONFIG_CALL_URL);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        JSONObject jsonObject = ResponseParserUtil.readHttpResponseToJson(httpResponse);
        return jsonObject;
    }
}
