package org.dollarhide.androidmovieviewer.movieviewer;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.dollarhide.androidmovieviewer.service.MovieService;
import org.dollarhide.androidmovieviewer.task.CoverArtImageTask;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.json.JSONObject;

public class InformationActivity extends BaseMovieViewerActivity {
    private static String TAG = "InformationActivity";
    private static final String CONFIG_SERVICE_URL = "configuration";

    private Integer movieId;

    private TextView titleTextView;
    private TextView overviewTextView;
    private ImageView posterView;
    private MovieService movieService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        overviewTextView = (TextView) findViewById(R.id.overviewTextView);
        posterView = (ImageView) findViewById(R.id.posterView);

        movieService = new MovieService();

        Bundle passedInformation = getIntent().getExtras();
        movieId = (Integer) passedInformation.get("selectedEntertainmentId");

        //FIXME: Check for this null pointer and return to search if found
        movieService.getBasicInfo(this, movieId, basicInfoListener(), basicInfoErrorListener());

        //Calls task for rest calls outside of UI thread
        CoverArtImageTask coverArtImageTask = new CoverArtImageTask();
        coverArtImageTask.execute(posterView, movieId.toString());
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
                LoggingUtil.logException(TAG, error);
            }
        };
    }
}
