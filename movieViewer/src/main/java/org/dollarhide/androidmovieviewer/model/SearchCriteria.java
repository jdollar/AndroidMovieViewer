package org.dollarhide.androidmovieviewer.model;

import java.net.URLEncoder;

public class SearchCriteria {
    private String movieTitle;
    private int pageNumber;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
