package org.dollarhide.androidmovieviewer.activity.login.impl;

import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import org.dollarhide.androidmovieviewer.activity.BaseActivityTestCase;
import org.dollarhide.androidmovieviewer.activity.login.LoginCallbackListener;
import org.dollarhide.androidmovieviewer.activity.login.LoginInteractor;
import org.dollarhide.androidmovieviewer.activity.login.LoginPresenter;
import org.dollarhide.androidmovieviewer.activity.login.LoginView;
import org.mockito.Mockito;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LoginPresenterImplTest extends BaseActivityTestCase<LoginActivity> {

    private LoginPresenter loginPresenter;
    private LoginView mockLoginView;
    private LoginInteractor mockLoginInteractor;

    public LoginPresenterImplTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockLoginView = Mockito.mock(LoginView.class);
        mockLoginInteractor = Mockito.mock(LoginInteractor.class);
        loginPresenter = new LoginPresenterImpl(mockLoginView);
        loginPresenter.setLoginInteractor(mockLoginInteractor);
    }

    @MediumTest
    public void testLoginPresenter_onClearBanners() {
        doNothing().when(mockLoginView).clearBanners();
        loginPresenter.onClearBanners();
        verify(mockLoginView, times(1)).clearBanners();
    }

    @MediumTest
    public void testLoginPresenter_onUpdateSuccesBanner() {
        String expectedStatus = "testBanner";
        int expectedVisibility = View.VISIBLE;

        doNothing().when(mockLoginView).updateSuccessBanner(expectedStatus, expectedVisibility);
        loginPresenter.onUpdateSuccessBanner(expectedStatus, expectedVisibility);
        verify(mockLoginView, times(1)).updateSuccessBanner(expectedStatus, expectedVisibility);
    }

    @MediumTest
    public void testLoginPresenter_onUpdateErrorBanner() {
        String expectedStatus = "testBanner";
        int expectedVisibility = View.VISIBLE;

        doNothing().when(mockLoginView).updateErrorBanner(expectedStatus, expectedVisibility);
        loginPresenter.onUpdateErrorBanner(expectedStatus, expectedVisibility);
        verify(mockLoginView, times(1)).updateErrorBanner(expectedStatus, expectedVisibility);
    }

    @MediumTest
    public void testLoginPresenter_login() {
        String expectedUsername = "testUsername";
        String expectedPassword = "testPass";

        doNothing().when(mockLoginInteractor)
                .login(expectedUsername, expectedPassword, getCallbackListener());

        loginPresenter.login(expectedUsername, expectedPassword);

        verify(mockLoginInteractor, times(1))
                .login(expectedUsername, expectedPassword, getCallbackListener());
    }

    @MediumTest
    public void testLoginPresenter_onPreExecute() {
        doNothing().when(mockLoginView).clearBanners();
        getCallbackListener().onPreExecute();
        verify(mockLoginView, times(1)).clearBanners();
    }

    @MediumTest
    public void testLoginPresenter_onPostLoginSuccess() {
        int expectedVisibility = View.VISIBLE;
        doNothing().when(mockLoginView).clearBanners();
        doNothing().when(mockLoginView).updateSuccessBanner(LoginPresenterImpl.SUCCESS_LOGIN_MESSAGE, expectedVisibility);
        getCallbackListener().onPostLoginSuccess();
        verify(mockLoginView).clearBanners();
        verify(mockLoginView).updateSuccessBanner(LoginPresenterImpl.SUCCESS_LOGIN_MESSAGE, expectedVisibility);
    }

    @MediumTest
    public void testLoginPresenter_onPostLoginError() {
        int expectedVisibility = View.VISIBLE;
        doNothing().when(mockLoginView).clearBanners();
        doNothing().when(mockLoginView).updateErrorBanner(LoginPresenterImpl.FAILURE_LOGIN_MESSAGE, expectedVisibility);
        getCallbackListener().onPostLoginError();
        verify(mockLoginView).clearBanners();
        verify(mockLoginView).updateErrorBanner(LoginPresenterImpl.FAILURE_LOGIN_MESSAGE, expectedVisibility);
    }

    private LoginCallbackListener getCallbackListener() {
        return (LoginCallbackListener) loginPresenter;
    }
}
