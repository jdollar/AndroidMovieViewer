package org.dollarhide.androidmovieviewer.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import org.dollarhide.androidmovieviewer.movieviewer.R;

public abstract class BaseMovieViewerActivity extends AppCompatActivity {

    protected static final String PREF_FILE = "movieViewPreferences";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
