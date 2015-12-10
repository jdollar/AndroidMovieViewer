package org.dollarhide.androidmovieviewer.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;
import org.dollarhide.androidmovieviewer.util.ResponseParserUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;

public class ConfigurationService extends BaseService {

    private static final String TAG = "ConfigurationService";

    private static final String CONFIG_SERVICE_URL_PARAM = "configuration_service_url";

    public JSONObject getConfiguration() throws IOException, JSONException {
        String configurationServiceUrl = ResourcePropertyReader.getServiceUrl(CONFIG_SERVICE_URL_PARAM);
        String url = MessageFormat.format(configurationServiceUrl, ResourcePropertyReader.getApiKey());
        LoggingUtil.logDebug(TAG, "Sending: " + url);

        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = getHttpClient().execute(httpGet);

        JSONObject jsonObject = ResponseParserUtil.readHttpResponseToJson(httpResponse);
        return jsonObject;
    }
}
