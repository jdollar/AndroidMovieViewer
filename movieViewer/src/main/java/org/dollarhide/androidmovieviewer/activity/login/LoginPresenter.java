package org.dollarhide.androidmovieviewer.activity.login;

public interface LoginPresenter {
    void onClearBanners();
    void onUpdateSuccessBanner(String statusText, int visibility);
    void onUpdateErrorBanner(String statusText, int visibility);
    void login(String username, String password);
    void setLoginInteractor(LoginInteractor loginInteractor);
}
