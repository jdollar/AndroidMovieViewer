package org.dollarhide.androidmovieviewer.activity.login;

public interface LoginView {
    public void clearBanners();
    public void updateSuccessBanner(String statusText, int visibility);
    public void updateErrorBanner(String statusText, int visibility);
}
