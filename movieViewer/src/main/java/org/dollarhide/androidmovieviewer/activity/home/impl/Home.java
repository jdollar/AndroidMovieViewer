package org.dollarhide.androidmovieviewer.activity.home.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import org.dollarhide.androidmovieviewer.activity.BaseMovieViewerActivity;
import org.dollarhide.androidmovieviewer.activity.login.impl.LoginActivity;
import org.dollarhide.androidmovieviewer.activity.search.impl.SearchActivity;
import org.dollarhide.androidmovieviewer.movieviewer.R;

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

    public void openLogin(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
