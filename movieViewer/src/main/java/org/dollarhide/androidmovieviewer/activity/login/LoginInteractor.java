package org.dollarhide.androidmovieviewer.activity.login;

public interface LoginInteractor {
    public void login(String username, String password, LoginCallbackListener listener);
    public void onPostLogin(Object result, LoginCallbackListener listener);
}