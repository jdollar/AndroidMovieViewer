package org.dollarhide.androidmovieviewer.movieviewer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.dollarhide.androidmovieviewer.service.InformationService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class InformationActivity extends BaseMovieViewerActivity {
    private static String TAG = "InformationActivity";

    private Integer movieId;

    private TextView titleTextView;
    private TextView overviewTextView;
    private ImageView posterView;
    private InformationService informationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        overviewTextView = (TextView) findViewById(R.id.overviewTextView);
        posterView = (ImageView) findViewById(R.id.posterView);

        informationService = new InformationService();

        Bundle passedInformation = getIntent().getExtras();
        movieId = (Integer) passedInformation.get("selectedEntertainmentId");

        //FIXME: Check for this null pointer and return to search if found
        informationService.getBasicInfo(this, movieId, basicInfoListener(), basicInfoErrorListener());
        informationService.getCoverArt(this, movieId, coverArtListener(this), coverArtErrorListener());
    }

    private Response.Listener<JSONObject> basicInfoListener() {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, response.toString());

                    titleTextView.setText(response.get("original_title").toString());
                    overviewTextView.setText(response.get("overview").toString());
                } catch(Exception e) {
                    //TODO: Handle Server Error
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        };
    }

    private Response.ErrorListener basicInfoErrorListener() {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        };
    }

    private Response.Listener<JSONObject> coverArtListener(final Activity activity) {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                String imagePath = getImagePathFromResponse(response);
                try {
                    //FIXME: Won't work. Needs to get configuration synchronously
                    URL imageUrl = new URL(informationService.getConfigurationManager(activity).getBaseImageUrl() + cleanImagePath(imagePath));
                    Bitmap imageToDisplay = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                    posterView.setImageBitmap(imageToDisplay);
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        };
    }

    private Response.ErrorListener coverArtErrorListener() {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        };
    }

    private String getImagePathFromResponse(JSONObject response) {
        String imagePath = null;
        try {
            JSONArray posters = response.getJSONArray("posters");
            if (posters != null) {
                for(int i = 0; i < posters.length(); i++) {
                    JSONObject poster = posters.getJSONObject(i);
                    if (poster != null && !"".equals(poster.get("file_path"))) {
                        return poster.get("file_path").toString();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        return imagePath;
    }

    private String cleanImagePath(String imagePath) {
        if(imagePath != null && imagePath.matches("^/")) {
            imagePath = imagePath.substring(1);
        }

        return imagePath;
    }
}
