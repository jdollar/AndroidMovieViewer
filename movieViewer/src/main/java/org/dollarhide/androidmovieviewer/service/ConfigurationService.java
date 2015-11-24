package org.dollarhide.androidmovieviewer.service;

import android.content.Context;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.dollarhide.androidmovieviewer.util.ResponseParserUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ConfigurationService extends BaseService {

    private static final String TAG = "ConfigurationService";

    private static final String CONFIG_SERVICE_URL = "configuration";

    public JSONObject getConfiguration(Context baseContext) throws IOException, JSONException {
        String configurationUrl = getBaseUrl(baseContext) + CONFIG_SERVICE_URL + "?" + getApiKeyRequestParam(baseContext);
        LoggingUtil.logDebug(TAG, "Sending: " + configurationUrl);

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(configurationUrl);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        JSONObject jsonObject = ResponseParserUtil.readHttpResponseToJson(httpResponse);
        return jsonObject;
    }
}
