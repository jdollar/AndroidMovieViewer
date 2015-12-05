package org.dollarhide.androidmovieviewer.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.dollarhide.androidmovieviewer.movieviewer.R;
import org.dollarhide.androidmovieviewer.service.MovieService;
import org.dollarhide.androidmovieviewer.task.CoverArtImageTask;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.json.JSONObject;

public class InformationActivity extends BaseMovieViewerActivity {
    private static String TAG = "InformationActivity";
    private static final String CONFIG_SERVICE_URL = "configuration";

    private Integer movieId;

    private ProgressBar progressBar;
    private TextView titleTextView;
    private TextView overviewTextView;
    private ImageView posterView;
    private MovieService movieService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        progressBar = (ProgressBar) findViewById(R.id.informationProgressBar);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        overviewTextView = (TextView) findViewById(R.id.overviewTextView);
        posterView = (ImageView) findViewById(R.id.posterView);

        movieService = new MovieService();

        Bundle passedInformation = getIntent().getExtras();
        movieId = (Integer) passedInformation.get("selectedEntertainmentId");

        if (movieId == null) {
            LoggingUtil.logDebug(TAG, "Movie ID is invalid");
            this.finish();
        }

        movieService.getBasicInfo(this, movieId, basicInfoListener(this), basicInfoErrorListener());

        //Calls task for rest calls outside of UI thread
        CoverArtImageTask coverArtImageTask = new CoverArtImageTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                LoggingUtil.logDebug(TAG, "Image Url Result: " + result);
                posterView.setImageBitmap((Bitmap) result);
                progressBar.setVisibility(View.GONE);
            }
        };

        coverArtImageTask.execute(posterView, movieId.toString());
    }

    private Response.Listener<JSONObject> basicInfoListener(final Activity activity) {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    LoggingUtil.logDebug(TAG, "Response: " + response.toString());

                    titleTextView.setText(response.get("original_title").toString());
                    overviewTextView.setText(response.get("overview").toString());
                } catch(Exception e) {
                    LoggingUtil.logDebug(TAG, "Error has occurred");
                    activity.finish();
                    LoggingUtil.logException(TAG, e);
                }
            }
        };
    }

    private Response.ErrorListener basicInfoErrorListener() {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                LoggingUtil.logException(TAG, error);
            }
        };
    }
}
