package org.dollarhide.androidmovieviewer.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;
import org.dollarhide.androidmovieviewer.util.ResponseParserUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;

public class ImageService extends MovieService {

    private static final String TAG = "ImageService";
    private static final String IMAGES_SERVICE_URL = "bitmap_image_service_url";
    private static final String POSTERS_PARAM = "posters";
    private static final String FILE_PATH_PARAM = "file_path";

    public String getBitmapUrl(String movieId) throws IOException, JSONException {
        String imageServiceUrl = ResourcePropertyReader.getServiceUrl(IMAGES_SERVICE_URL);
        String url = MessageFormat.format(imageServiceUrl, movieId, ResourcePropertyReader.getApiKey(), "en");

        LoggingUtil.logDebug(TAG, "Sending: " + url);

        HttpGet httpGet = new HttpGet(url);
        HttpResponse imageResponse = getHttpClient().execute(httpGet);

        return getImagePathFromResponse(ResponseParserUtil.readHttpResponseToJson(imageResponse));
    }

    private String getImagePathFromResponse(JSONObject response) throws JSONException {
        String imagePath = null;
        if (response.has(POSTERS_PARAM)) {
            JSONArray posters = response.getJSONArray(POSTERS_PARAM);

            if (posters != null) {
                for (int i = 0; i < posters.length(); i++) {
                    JSONObject poster = posters.getJSONObject(i);

                    if (poster != null && !"".equals(poster.get(FILE_PATH_PARAM))) {
                        return poster.get(FILE_PATH_PARAM).toString();
                    }
                }
            }
        }

        return imagePath;
    }

}
