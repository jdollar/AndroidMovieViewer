package org.dollarhide.androidmovieviewer.service;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.dollarhide.androidmovieviewer.model.RestResult;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;
import org.dollarhide.androidmovieviewer.util.ResponseParserUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;

public class AuthenticationService extends BaseService {
    private static final String TAG = "AuthenticationService";

    private static final String AUTHENTICATION_NEW_PARAM = "authentication_new_service_url";
    private static final String AUTHENTICATION_VALIDATE_LOGIN_PARAM = "authentication_validate_with_login_url";
    private static final String AUTHENTICATION_SESSION_NEW_PARAM = "authentication_session_new_url";


    private static final String REQUEST_TOKEN = "request_token";
    private static final String SESSION_TOKEN = "session_id";
    private static final String SUCCESS_PARAM = "success";

    public RestResult getNewAuthenticationToken() throws IOException, JSONException {
        return callService(AUTHENTICATION_NEW_PARAM, REQUEST_TOKEN, ResourcePropertyReader.getApiKey());
    }

    public RestResult login(String username, String password, String requestToken) throws IOException, JSONException {
        String[] paramArray = new String[] {ResourcePropertyReader.getApiKey(), requestToken, username, password};
        return callService(AUTHENTICATION_VALIDATE_LOGIN_PARAM, SUCCESS_PARAM, paramArray);
    }

    public RestResult getSessionId(String requestToken) throws IOException, JSONException {
        String[] paramArray = new String[] {ResourcePropertyReader.getApiKey(), requestToken};
        return callService(AUTHENTICATION_SESSION_NEW_PARAM, SESSION_TOKEN, paramArray);
    }

    private RestResult callService(String serviceUrl, String returnData, Object... params) throws IOException, JSONException {
        String urlProperty = ResourcePropertyReader.getProperty(serviceUrl);
        String url = getBaseUrl() + MessageFormat.format(urlProperty, params);

        Log.d(TAG, "Sending url: " + url);

        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = getHttpClient().execute(httpGet);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            JSONObject jsonObject = ResponseParserUtil.readHttpResponseToJson(httpResponse);
            return new RestResult(jsonObject.getBoolean(SUCCESS_PARAM), jsonObject.getString(returnData));
        }

        LoggingUtil.logDebug(TAG, "Bad response. Status code: " + httpResponse.getStatusLine().getStatusCode());
        return new RestResult(Boolean.FALSE, null);
    }
}
