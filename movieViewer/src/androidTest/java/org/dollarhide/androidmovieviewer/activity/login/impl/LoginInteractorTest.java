package org.dollarhide.androidmovieviewer.activity.login.impl;


import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import org.dollarhide.androidmovieviewer.activity.login.LoginCallbackListener;
import org.dollarhide.androidmovieviewer.activity.login.LoginInteractor;
import org.dollarhide.androidmovieviewer.model.RestResult;
import org.mockito.Mockito;

import java.util.concurrent.CountDownLatch;

public class LoginInteractorTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginInteractor loginInteractor;
    private LoginCallbackListener loginCallback;
    private CountDownLatch signal;

    public LoginInteractorTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        setActivityInitialTouchMode(true);
        loginCallback = Mockito.mock(LoginCallbackListener.class);
        loginInteractor = new LoginInteractorImpl();
        getActivity();
    }

    @MediumTest
    public void testLoginInteractor_errorWhenLoginAndNullResults(){
        loginInteractor.onPostLogin(null, loginCallback);
        Mockito.verify(loginCallback, Mockito.times(1)).onPostLoginError();
    }

    @MediumTest
    public void testLoginInteractor_errorWhenLoginAndBadResult() {
        loginInteractor.onPostLogin(new RestResult(false, ""), loginCallback);
        Mockito.verify(loginCallback, Mockito.times(1)).onPostLoginError();
    }

    @MediumTest
    public void testLoginInteractor_successWhenLogin() {
        loginInteractor.onPostLogin(new RestResult(false, ""), loginCallback);
        Mockito.verify(loginCallback, Mockito.times(1)).onPostLoginSuccess();
    }
}
