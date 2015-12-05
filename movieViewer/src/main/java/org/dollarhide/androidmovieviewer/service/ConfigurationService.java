package org.dollarhide.androidmovieviewer.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.dollarhide.androidmovieviewer.util.ResponseParserUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ConfigurationService extends BaseService {

    private static final String TAG = "ConfigurationService";

    private static final String CONFIG_SERVICE_URL = "configuration";

    public JSONObject getConfiguration() throws IOException, JSONException {
        String configurationUrl = getBaseUrl() + CONFIG_SERVICE_URL + "?" + getApiKeyRequestParam();
        LoggingUtil.logDebug(TAG, "Sending: " + configurationUrl);

        HttpGet httpGet = new HttpGet(configurationUrl);
        HttpResponse httpResponse = getHttpClient().execute(httpGet);

        JSONObject jsonObject = ResponseParserUtil.readHttpResponseToJson(httpResponse);
        return jsonObject;
    }
}
