package org.dollarhide.androidmovieviewer.util;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResponseParserUtil {
    private static final String TAG = "ResponseParserUtil";

    public static JSONObject readHttpResponseToJson(HttpResponse response) throws IOException, JSONException {
        //return null if response isn't set properly
        if (response == null || response.getEntity() == null) {
            return null;
        }

        LoggingUtil.logDebug(TAG, "Response data: " + response.toString());
        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder sb = new StringBuilder();

        String readLine;
        while((readLine = in.readLine()) != null) {
            LoggingUtil.logDebug(TAG, "Line read: " + readLine);
            sb.append(readLine).append("\n");
        }

        JSONTokener tokener = new JSONTokener(sb.toString());
        return new JSONObject(tokener);
    }
}
