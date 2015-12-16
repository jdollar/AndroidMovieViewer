package org.dollarhide.androidmovieviewer.activity.search.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.dollarhide.androidmovieviewer.activity.BaseMovieViewerActivity;
import org.dollarhide.androidmovieviewer.activity.Information.impl.InformationActivity;
import org.dollarhide.androidmovieviewer.adapter.ResultAdapter;
import org.dollarhide.androidmovieviewer.model.SearchCriteria;
import org.dollarhide.androidmovieviewer.model.SearchResults;
import org.dollarhide.androidmovieviewer.movieviewer.R;
import org.dollarhide.androidmovieviewer.service.SearchService;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

public class SearchActivity extends BaseMovieViewerActivity {

    private static String TAG = "SearchActivity";

    private static final int INITIAL_PAGE_NUMBER = 1;

    //activity members
    private SearchService searchService;
    private SearchCriteria searchCriteria;
    private SearchResults searchResults;

    private ResultAdapter resultAdapter;

    //page inputs
    private EditText titleInput;
    private ListView resultsListView;
    private Button selectButton;
    private Button prevPageButton;
    private Button nextPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchService = new SearchService();

        titleInput = (EditText) findViewById(R.id.searchTitleText);
        resultsListView = (ListView) findViewById(R.id.resultsView);
        selectButton = (Button) findViewById(R.id.select_button);
        prevPageButton = (Button) findViewById(R.id.prev_page_button);
        nextPageButton = (Button) findViewById(R.id.next_page_button);

        resultsListView.setOnItemClickListener(createItemClickListener());
    }

    public void searchForMovie(View view) {
        populateSearchCriteria(titleInput.getText().toString(), INITIAL_PAGE_NUMBER);
        callMovieSearch();
    }

    public void viewNextPage(View view) {
        searchForMovieWithPageNumber(searchCriteria.getPageNumber() + 1);
    }

    public void viewPrevPage(View view) {
        searchForMovieWithPageNumber(searchCriteria.getPageNumber() - 1);
    }

    public void searchForMovieWithPageNumber(int newPageNumber) {
        String previousTitleSearch = searchCriteria.getMovieTitle();
        populateSearchCriteria(previousTitleSearch, newPageNumber);
        callMovieSearch();
    }

    public void viewSelectionInformation(int position) {
        Intent informationIntent = new Intent(this, InformationActivity.class);
        Bundle selectedEntertainment = new Bundle();

        selectedEntertainment.putString("selectedEntertainmentId", resultAdapter.getItemKey(position));
        informationIntent.putExtras(selectedEntertainment);

        startActivity(informationIntent);
    }

    public void clearInputFields(View view) {
        titleInput.setText("");
        searchCriteria = null;
        searchResults = null;
        resultsListView.setAdapter(new ResultAdapter(Collections.EMPTY_MAP));
        hideSelectButton();
        showHidePageButtons();
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
                    resultsListView.setAdapter(new ResultAdapter(Collections.EMPTY_MAP));

                    LoggingUtil.logDebug(TAG, "Response: " + response.toString());

                    //get the current page number and total number of pages possible
                    searchResults.setCurrentPageNumber(response.getInt("page"));
                    searchResults.setTotalPages(response.getInt("total_pages"));

                    //populate results radio buttons
                    JSONArray resultList = (JSONArray) response.get("results");
                    for(int i = 0; i < resultList.length(); i++) {
                        JSONObject result = resultList.getJSONObject(i);

                        searchResults.addResults(result.getString("id"), result.getString("title"));
                    }
                    resultAdapter = new ResultAdapter(searchResults.getResults());
                    resultsListView.setAdapter(resultAdapter);

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
                LoggingUtil.logException(TAG, error);
            }
        };
    }

    private void showHidePageButtons() {
        if(searchResults == null || searchResults.getTotalPages() <= searchResults.getCurrentPageNumber()) {
            nextPageButton.setVisibility(View.INVISIBLE);
        } else {
            nextPageButton.setVisibility(View.VISIBLE);
        }

        if (searchResults == null || searchResults.getCurrentPageNumber() <= INITIAL_PAGE_NUMBER) {
            prevPageButton.setVisibility(View.INVISIBLE);
        } else {
            prevPageButton.setVisibility(View.VISIBLE);
        }
    }

    private AdapterView.OnItemClickListener createItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewSelectionInformation(position);
            }
        };
    }

    //Getters and Setters
    public EditText getTitleInput() {
        return titleInput;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public Button getNextPageButton() {
        return nextPageButton;
    }

    public Button getPrevPageButton() {
        return prevPageButton;
    }
}
