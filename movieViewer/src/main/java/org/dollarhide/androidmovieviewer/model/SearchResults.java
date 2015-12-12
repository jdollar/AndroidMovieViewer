package org.dollarhide.androidmovieviewer.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class SearchResults {

    private int currentPageNumber;
    private int totalPages;
    private Map<String, String> results = new LinkedHashMap<String, String>();


    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void addResults(String id, String name) {
        results.put(id, name);
    }

    public Map<String, String> getResults() { return results; }
}
