package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchService extends BaseService {

    private static String MOVIE_SEARCH_URL = "search/movie";

    public void movieSearch(Activity activity,
                            String query,
                            Response.Listener<JSONObject> titleListener,
                            Response.ErrorListener titleErrorListener) throws UnsupportedEncodingException{
        String url = createMovieSearchUrl(activity.getBaseContext(), query);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, titleListener, titleErrorListener);
        getRequestQueue(activity).add(jsObjRequest);
    }

    private String createMovieSearchUrl(Context baseContext, String query) throws UnsupportedEncodingException {
        return getBaseUrl(baseContext) + MOVIE_SEARCH_URL + "?" + getApiKeyRequestParam(baseContext) + "&query=" + URLEncoder.encode(query, "UTF-8");
    }

}
