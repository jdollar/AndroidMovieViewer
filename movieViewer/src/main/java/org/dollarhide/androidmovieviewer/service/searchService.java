package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchService extends BaseService {

    private static String KEYWORD_SEARCH_URL = "search/keyword";

    public void keywordSearch(Activity activity,
                              String query,
                              Response.Listener<JSONObject> keywordListener,
                              Response.ErrorListener keywordErrorListener) throws UnsupportedEncodingException {
        String url = createKeywordSearchUrl(activity.getBaseContext(), query);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, keywordListener, keywordErrorListener);
        getRequestQueue(activity).add(jsObjRequest);
    }

    private String createKeywordSearchUrl(Context baseContext, String query) throws UnsupportedEncodingException {
        return getBaseUrl(baseContext) + KEYWORD_SEARCH_URL + "?" + getApiKeyRequestParam(baseContext) + "&query=" + URLEncoder.encode(query, "UTF-8");
    }

}
