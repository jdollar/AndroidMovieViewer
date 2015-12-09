package org.dollarhide.androidmovieviewer.activity.login;

public interface LoginCallbackListener {
    public void onPreExecute();
    public void onPostLoginSuccess();
    public void onPostLoginError();
}
