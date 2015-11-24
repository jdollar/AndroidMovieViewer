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
import org.dollarhide.androidmovieviewer.model.SearchCriteria;
import org.dollarhide.androidmovieviewer.model.SearchResults;
import org.dollarhide.androidmovieviewer.movieviewer.R;
import org.dollarhide.androidmovieviewer.service.SearchService;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SearchActivity extends BaseMovieViewerActivity {

    private static String TAG = "SearchActivity";

    private static final int INITIAL_PAGE_NUMBER = 1;

    //activity members
    private SearchService searchService;
    private SearchCriteria searchCriteria;
    private SearchResults searchResults;

    //page inputs
    private EditText titleInput;
    private RadioGroup resultRadioGroup;
    private Button selectButton;
    private Button prevPageButton;
    private Button nextPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchService = new SearchService();

        titleInput = (EditText) findViewById(R.id.searchTitleText);
        resultRadioGroup = (RadioGroup) findViewById(R.id.result_radio_group);
        selectButton = (Button) findViewById(R.id.select_button);
        prevPageButton = (Button) findViewById(R.id.prev_page_button);
        nextPageButton = (Button) findViewById(R.id.next_page_button);

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
        populateSearchCriteria(titleInput.getText().toString(), INITIAL_PAGE_NUMBER);
        callMovieSearch();
    }

    public void searchForMovieWithPageNumber(int newPageNumber) {
        String previousTitleSearch = searchCriteria.getMovieTitle();
        populateSearchCriteria(previousTitleSearch, newPageNumber);
        callMovieSearch();
    }

    public void viewNextPage(View view) {
        searchForMovieWithPageNumber(searchCriteria.getPageNumber() + 1);
    }

    public void viewPrevPage(View view) {
        searchForMovieWithPageNumber(searchCriteria.getPageNumber() - 1);
    }

    private void callMovieSearch() {
        try {
            searchService.movieSearch(this, searchCriteria, movieSearchListener(), logStackTraceErrorListener());
        } catch (UnsupportedEncodingException e) {
            LoggingUtil.logException(TAG, e);
        }
    }

    private void populateSearchCriteria(String movieTitle, int pageNumber) {
        searchCriteria = new SearchCriteria();
        searchCriteria.setMovieTitle(movieTitle);
        searchCriteria.setPageNumber(pageNumber);
    }

    public void viewSelectionInformation(View view) {
        Intent informationIntent = new Intent(this, InformationActivity.class);
        Bundle selectedEntertainment = new Bundle();

        selectedEntertainment.putInt("selectedEntertainmentId", resultRadioGroup.getCheckedRadioButtonId());
        informationIntent.putExtras(selectedEntertainment);

        startActivity(informationIntent);
    }

    public void clearInputFields(View view) {
        titleInput.setText("");
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
                    searchResults = new SearchResults();

                    //clear out previous results and makes items not selectable
                    hideSelectButton();
                    resultRadioGroup.removeAllViews();

                    LoggingUtil.logDebug(TAG, "Response: " + response.toString());

                    //get the current page number and total number of pages possible
                    searchResults.setCurrentPageNumber(response.getInt("page"));
                    searchResults.setTotalPages(response.getInt("total_pages"));

                    //populate results radio buttons
                    JSONArray resultList = (JSONArray) response.get("results");
                    for(int i = 0; i < resultList.length(); i++) {
                        JSONObject result = resultList.getJSONObject(i);

                        searchResults.addResults(result.getString("id"), result.getString("title"));
                        resultRadioGroup.addView(createResultRadioButton(result.getInt("id"), result.getString("title")));
                    }

                    showHidePageButtons();
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

    private void showHidePageButtons() {
        if(searchResults.getTotalPages() <= searchResults.getCurrentPageNumber()) {
            nextPageButton.setVisibility(View.INVISIBLE);
        } else {
            nextPageButton.setVisibility(View.VISIBLE);
        }

        if (searchResults.getCurrentPageNumber() <= INITIAL_PAGE_NUMBER) {
            prevPageButton.setVisibility(View.INVISIBLE);
        } else {
            prevPageButton.setVisibility(View.VISIBLE);
        }
    }
}
