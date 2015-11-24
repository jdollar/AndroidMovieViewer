package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.dollarhide.androidmovieviewer.model.SearchCriteria;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class SearchService extends BaseService {

    private static String TAG = "SearchService";
    private static String MOVIE_SEARCH_URL = "search/movie";

    public void movieSearch(Activity activity,
                            SearchCriteria searchCriteria,
                            Response.Listener<JSONObject> titleListener,
                            Response.ErrorListener titleErrorListener) throws UnsupportedEncodingException{
        String url = createMovieSearchUrl(activity.getBaseContext(), getUrlEncodedQuery(searchCriteria));
        LoggingUtil.logDebug(TAG, "Sending: " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, titleListener, titleErrorListener);
        getRequestQueue(activity).add(jsObjRequest);
    }

    private String createMovieSearchUrl(Context baseContext, String query) throws UnsupportedEncodingException {
        return getBaseUrl(baseContext) + MOVIE_SEARCH_URL + "?" + getApiKeyRequestParam(baseContext) + query;
    }

    private String getUrlEncodedQuery(SearchCriteria criteria) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();

        sb.append("&query=");
        sb.append(URLEncoder.encode(criteria.getMovieTitle(), "UTF-8"));
        sb.append("&page=");
        sb.append(criteria.getPageNumber());

        LoggingUtil.logDebug(TAG, "Generated Query: " + sb.toString());

        return sb.toString();
    }

}
