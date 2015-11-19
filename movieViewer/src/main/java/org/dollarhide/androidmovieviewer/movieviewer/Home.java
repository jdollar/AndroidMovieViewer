package org.dollarhide.androidmovieviewer.movieviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Home extends BaseMovieViewerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openSearch(View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }
}
