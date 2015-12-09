package org.dollarhide.androidmovieviewer.activity.login.impl;

import android.view.View;
import org.dollarhide.androidmovieviewer.activity.login.LoginCallbackListener;
import org.dollarhide.androidmovieviewer.activity.login.LoginInteractor;
import org.dollarhide.androidmovieviewer.activity.login.LoginPresenter;
import org.dollarhide.androidmovieviewer.activity.login.LoginView;

public class LoginPresenterImpl implements LoginPresenter, LoginCallbackListener {

    public static final String SUCCESS_LOGIN_MESSAGE = "Logged in successsfully!";
    public static final String FAILURE_LOGIN_MESSAGE = "Error logging in";

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void onClearBanners() {
        loginView.clearBanners();
    }

    @Override
    public void onUpdateSuccessBanner(String statusText, int visibility) {
        loginView.updateSuccessBanner(statusText, visibility);
    }

    @Override
    public void onUpdateErrorBanner(String statusText, int visibility) {
        loginView.updateErrorBanner(statusText, visibility);
    }

    @Override
    public void login(String username, String password) {
        loginInteractor.login(username, password, this);
    }

    @Override
    public void onPreExecute() {
        loginView.clearBanners();
    }

    @Override
    public void onPostLoginSuccess() {
        loginView.clearBanners();
        loginView.updateSuccessBanner(SUCCESS_LOGIN_MESSAGE, View.VISIBLE);
    }

    @Override
    public void onPostLoginError() {
        loginView.clearBanners();
        loginView.updateErrorBanner(FAILURE_LOGIN_MESSAGE, View.VISIBLE);
    }

    //used for testing
    public void setLoginInteractor(LoginInteractor loginInteractor) {
        this.loginInteractor = loginInteractor;
    }
}
