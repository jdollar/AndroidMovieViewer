package org.dollarhide.androidmovieviewer.activity.login.impl;

import android.content.SharedPreferences;
import org.dollarhide.androidmovieviewer.activity.login.LoginCallbackListener;
import org.dollarhide.androidmovieviewer.activity.login.LoginInteractor;
import org.dollarhide.androidmovieviewer.application.MovieViewApplication;
import org.dollarhide.androidmovieviewer.model.RestResult;
import org.dollarhide.androidmovieviewer.task.LoginTask;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;

public class LoginInteractorImpl implements LoginInteractor {
    private static String TAG = "LoginInteractorImpl";

    public static final String SESSION_ID_PREF_PARAM = "session_id";

    @Override
    public void login(String username, String password, final LoginCallbackListener listener) {
        LoginTask loginTask = createNewLoginTask(listener);
        loginTask.execute(username, password);
    }

    public LoginTask createNewLoginTask(final LoginCallbackListener listener) {
         return new LoginTask() {
            @Override
            protected void onPreExecute() {
                //TODO: do some progress bar display. Login action takes 3 rest calls.
                super.onPreExecute();
                listener.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object result) {
                onPostLogin(result, listener);
                super.onPostExecute(result);
            }
         };
    }

    public void onPostLogin(Object result, LoginCallbackListener listener) {
        if (result instanceof RestResult && ((RestResult) result).getSuccessFlag()) {
            //if it is a success should have a session id as a string in the data
            String sessionId = (String) ((RestResult) result).getData();

            LoggingUtil.logDebug(TAG, "Session id received: " + sessionId);
            //save session id in shared preference file
            updateSharedPreference(sessionId);
            listener.onPostLoginSuccess();
        }else{
            listener.onPostLoginError();
        }
    }

    private void updateSharedPreference(String sessionId) {
        SharedPreferences sharedPreferences = MovieViewApplication.getMovieViewerSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_ID_PREF_PARAM, sessionId);
        editor.commit();
    }
}
