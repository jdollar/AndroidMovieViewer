package org.dollarhide.androidmovieviewer.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import org.dollarhide.androidmovieviewer.model.RestResult;
import org.dollarhide.androidmovieviewer.movieviewer.R;
import org.dollarhide.androidmovieviewer.task.LoginTask;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;

public class LoginActivity extends BaseMovieViewerActivity {
    private static final String TAG = "LoginActivity";

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

        LoginTask loginTask = new LoginTask() {
            @Override
            protected void onPreExecute() {
                //TODO: do some progress bar display. Login action takes 3 rest calls.
                super.onPreExecute();
                clearBanners();
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);

                if (result instanceof RestResult && ((RestResult) result).getSuccessFlag()) {
                    //if it is a success should have a session id as a string in the data
                    String sessionId = (String) ((RestResult) result).getData();

                    LoggingUtil.logDebug(TAG, "Session id received: " + sessionId);
                    //save session id in shared preference file
                    updateSharedPreference(sessionId);

                    updateBanner(successRow, successTextView, "Logged in successfully!", View.VISIBLE);
                } else {
                    updateBanner(errorRow, errorTextView, "Error loggin in", View.VISIBLE);
                }
            }
        };

        loginTask.execute(username, password);
    }

    private void updateSharedPreference(String sessionId) {
        SharedPreferences sharedPreferences = getMovieViewerSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("session_id", sessionId);
        editor.commit();
    }

    private void clearBanners() {
        if (successRow.getVisibility() == View.VISIBLE) {
            updateBanner(successRow, successTextView, "", View.GONE);
        }

        if (errorRow.getVisibility() == View.VISIBLE) {
            updateBanner(errorRow, errorTextView, "", View.GONE);
        }
    }

    private void updateBanner(TableRow tableRow, TextView textView, String statusText, int visibility) {
        textView.setText(statusText);
        tableRow.setVisibility(visibility);
    }
}
