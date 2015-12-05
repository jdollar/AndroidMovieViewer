package org.dollarhide.androidmovieviewer.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.dollarhide.androidmovieviewer.util.ResponseParserUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ImageService extends MovieService {

    private static final String TAG = "ImageService";
    private static final String IMAGES_SERVICE_URL = "images";

    public String getBitmapUrl(String movieId) throws IOException, JSONException {
        String imageGetUrl = getBaseUrl() + MOVIE_SERVICE_URL + "/" + movieId + "/" + IMAGES_SERVICE_URL + "?" + getApiKeyRequestParam() + "&language=en";

        LoggingUtil.logDebug(TAG, "Sending: " + imageGetUrl);

        HttpGet httpGet = new HttpGet(imageGetUrl);
        HttpResponse imageResponse = getHttpClient().execute(httpGet);

        return getImagePathFromResponse(ResponseParserUtil.readHttpResponseToJson(imageResponse));
    }

    private String getImagePathFromResponse(JSONObject response) throws JSONException {
        String imagePath = null;
        if (response.has("posters")) {
            JSONArray posters = response.getJSONArray("posters");

            if (posters != null) {
                for (int i = 0; i < posters.length(); i++) {
                    JSONObject poster = posters.getJSONObject(i);

                    if (poster != null && !"".equals(poster.get("file_path"))) {
                        return poster.get("file_path").toString();
                    }
                }
            }
        }

        return imagePath;
    }

}
