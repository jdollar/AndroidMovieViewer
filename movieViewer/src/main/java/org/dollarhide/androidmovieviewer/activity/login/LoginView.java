package org.dollarhide.androidmovieviewer.activity.login;

public interface LoginView {
    void clearBanners();
    void updateSuccessBanner(String statusText, int visibility);
    void updateErrorBanner(String statusText, int visibility);
    void setLoginPresenter(LoginPresenter loginPresenter);
}
