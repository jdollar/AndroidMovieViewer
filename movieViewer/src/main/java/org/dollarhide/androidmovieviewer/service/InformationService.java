package org.dollarhide.androidmovieviewer.service;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class InformationService extends BaseService {

    private static final String TAG = "InformationService";

    private static String MOVIE_SERVICE_URL = "movie/";
    private static String IMAGE_SERVICE_URL = "images";


    public void getBasicInfo(Activity activity, int query, Response.Listener<JSONObject> basicInfoListener, Response.ErrorListener basicErrorListener) {
        String url = BASE_URL + MOVIE_SERVICE_URL + query + "?api_key=" + API_KEY;

        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, basicInfoListener, basicErrorListener);

        getRequestQueue(activity).add(jsonObjectRequest);
    }

    public void getCoverArt(Activity activity, int movieId, Response.Listener<JSONObject> coverArtListener, Response.ErrorListener coverArtErrorListener) {

        String url = BASE_URL + MOVIE_SERVICE_URL + movieId + "/" + IMAGE_SERVICE_URL + "?" + API_REQUEST_PARAM + "&language=en";

        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                                                    url,
                                                    (String) null,
                                                    coverArtListener,
                                                    coverArtErrorListener);

        getRequestQueue(activity).add(jsonObjectRequest);
    }




}
