package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.dollarhide.androidmovieviewer.model.SearchCriteria;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

public class SearchService extends BaseService {

    private static String TAG = "SearchService";
    private static String MOVIE_SEARCH_URL = "movie_search_service_url";

    public void movieSearch(Activity activity,
                            SearchCriteria searchCriteria,
                            Response.Listener<JSONObject> titleListener,
                            Response.ErrorListener titleErrorListener) throws UnsupportedEncodingException{
        String url = createMovieSearchUrl(getUrlEncodedQuery(searchCriteria));
        LoggingUtil.logDebug(TAG, "Sending: " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, titleListener, titleErrorListener);
        getRequestQueue(activity).add(jsObjRequest);
    }

    private String createMovieSearchUrl(String query) throws UnsupportedEncodingException {
        String movieSearchUrl = ResourcePropertyReader.getServiceUrl(MOVIE_SEARCH_URL);
        return MessageFormat.format(movieSearchUrl, ResourcePropertyReader.getApiKey(), query);
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
