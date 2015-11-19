package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

public class SearchService extends BaseService {

    private static String KEYWORD_SEARCH_URL = "search/keyword";
    private static String KEYWORD_SEARCH = BASE_URL + KEYWORD_SEARCH_URL + "?api_key=" + API_KEY;

    public void keywordSearch(Activity activity,
                              String query,
                              Response.Listener<JSONObject> keywordListener,
                              Response.ErrorListener keywordErrorListener) {
        String url = createKeywordSearchUrl(query);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, keywordListener, keywordErrorListener);
        getRequestQueue(activity).add(jsObjRequest);
    }

    private String createKeywordSearchUrl(String query) {
        return KEYWORD_SEARCH + "&query=" + query;
    }

}
