package org.dollarhide.androidmovieviewer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.dollarhide.androidmovieviewer.movieviewer.R;
import org.dollarhide.androidmovieviewer.service.SearchService;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SearchActivity extends BaseMovieViewerActivity {

    private static String TAG = "SearchActivity";

    private SearchService searchService;

    private EditText titleInput;
    private RadioGroup resultRadioGroup;
    private Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchService = new SearchService();

        titleInput = (EditText) findViewById(R.id.searchTitleText);
        resultRadioGroup = (RadioGroup) findViewById(R.id.result_radio_group);
        selectButton = (Button) findViewById(R.id.select_button);

        resultRadioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (View.INVISIBLE == selectButton.getVisibility()) {
                            selectButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
    }

    public void searchForMovie(View view) {
        try {
            searchService.movieSearch(this, titleInput.getText().toString(), movieSearchListener(), logStackTraceErrorListener());
        } catch (UnsupportedEncodingException e) {
            LoggingUtil.logException(TAG, e);
        }
    }

    public void viewSelectionInformation(View view) {
        Intent informationIntent = new Intent(this, InformationActivity.class);
        Bundle selectedEntertainment = new Bundle();

        selectedEntertainment.putInt("selectedEntertainmentId", resultRadioGroup.getCheckedRadioButtonId());
        informationIntent.putExtras(selectedEntertainment);

        startActivity(informationIntent);
    }

    public void clearInputFields(View view) {
        hideSelectButton();
        resultRadioGroup.removeAllViews();
    }

    private RadioButton createResultRadioButton(int id, String label) {
        RadioButton resultRadioButton = new RadioButton(this);
        resultRadioButton.setId(id);
        resultRadioButton.setText(label);
        return resultRadioButton;
    }

    private void hideSelectButton() {
        selectButton.setVisibility(View.INVISIBLE);
    }

    private Response.Listener<JSONObject> movieSearchListener() {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //clear out previous results and makes items not selectable
                    hideSelectButton();
                    resultRadioGroup.removeAllViews();

                    LoggingUtil.logDebug(TAG, "Response: " + response.toString());

                    //populate results radio buttons
                    JSONArray resultList = (JSONArray) response.get("results");
                    for(int i = 0; i < resultList.length(); i++) {
                        JSONObject result = resultList.getJSONObject(i);
                        resultRadioGroup.addView(createResultRadioButton(Integer.parseInt(result.get("id").toString()), result.get("title").toString()));
                    }
                } catch(Exception e) {
                    LoggingUtil.logException(TAG, e);
                }
            }
        };
    }

    private Response.ErrorListener logStackTraceErrorListener() {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        };
    }
}
