package org.dollarhide.androidmovieviewer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.dollarhide.androidmovieviewer.movieviewer.R;

public abstract class BaseMovieViewerActivity extends AppCompatActivity {

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

        if (id == R.id.action_login) {
            openLogin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openLogin(View view) {
        openLogin();
    }

    public void openLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
