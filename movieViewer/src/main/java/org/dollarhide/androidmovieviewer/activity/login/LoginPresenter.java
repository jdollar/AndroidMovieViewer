package org.dollarhide.androidmovieviewer.activity.login;

public interface LoginPresenter {
    public void onClearBanners();
    public void onUpdateSuccessBanner(String statusText, int visibility);
    public void onUpdateErrorBanner(String statusText, int visibility);
    public void login(String username, String password);
}
