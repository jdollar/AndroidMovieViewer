package org.dollarhide.androidmovieviewer.movieviewer;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.dollarhide.androidmovieviewer.service.InformationService;
import org.json.JSONObject;

public class InformationActivity extends BaseMovieViewerActivity {
    private static String TAG = "InformationActivity";

    private Integer movieId;

    private TextView overviewTextView;
    private InformationService informationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        overviewTextView = (TextView) findViewById(R.id.overviewTextView);
        informationService = new InformationService();

        Bundle passedInformation = getIntent().getExtras();
        movieId = (Integer) passedInformation.get("selectedEntertainmentId");

        //FIXME: Check for this null pointer and return to search if found
        informationService.getBasicInfo(this, movieId, basicInfoListener(), basicInfoErrorListener());
    }

    private Response.Listener<JSONObject> basicInfoListener() {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, response.toString());
                } catch(Exception e) {
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
}
