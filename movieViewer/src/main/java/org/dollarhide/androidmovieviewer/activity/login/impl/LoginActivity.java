package org.dollarhide.androidmovieviewer.activity.login.impl;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import org.dollarhide.androidmovieviewer.activity.BaseMovieViewerActivity;
import org.dollarhide.androidmovieviewer.activity.login.LoginPresenter;
import org.dollarhide.androidmovieviewer.activity.login.LoginView;
import org.dollarhide.androidmovieviewer.movieviewer.R;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;

public class LoginActivity extends BaseMovieViewerActivity implements LoginView {
    private static final String TAG = "LoginActivity";

    private LoginPresenter loginPresenter;

    private TableRow successRow;
    private TextView successTextView;
    private TableRow errorRow;
    private TextView errorTextView;
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenterImpl(this);

        successRow = (TableRow) findViewById(R.id.successRow);
        successTextView = (TextView) findViewById(R.id.successTextView);
        errorRow = (TableRow) findViewById(R.id.errorRow);
        errorTextView = (TextView) findViewById(R.id.errorTextView);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        clearBanners();
    }

    public void submitLogin(View view) {
        LoggingUtil.logDebug(TAG, "Submitting Login");

        //FIXME beginning will only use cleartext password
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        loginPresenter.login(username, password);
    }

    @Override
    public void clearBanners() {
        if (successRow.getVisibility() == View.VISIBLE) {
            updateSuccessBanner("", View.GONE);
        }

        if (errorRow.getVisibility() == View.VISIBLE) {
            updateErrorBanner("", View.GONE);
        }
    }

    @Override
    public void updateSuccessBanner(String statusText, int visibility) {
        updateBanner(successRow, successTextView, statusText, visibility);
    }

    @Override
    public void updateErrorBanner(String statusText, int visibility) {
        updateBanner(errorRow, errorTextView, statusText, visibility);
    }

    private void updateBanner(TableRow tableRow, TextView textView, String statusText, int visibility) {
        textView.setText(statusText);
        tableRow.setVisibility(visibility);
    }
}
