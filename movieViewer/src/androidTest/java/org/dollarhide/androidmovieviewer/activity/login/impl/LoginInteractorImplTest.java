package org.dollarhide.androidmovieviewer.activity.login.impl;


import android.content.SharedPreferences;
import android.test.suitebuilder.annotation.MediumTest;
import org.dollarhide.androidmovieviewer.activity.BaseActivityTestCase;
import org.dollarhide.androidmovieviewer.activity.login.LoginCallbackListener;
import org.dollarhide.androidmovieviewer.activity.login.LoginInteractor;
import org.dollarhide.androidmovieviewer.application.MovieViewApplication;
import org.dollarhide.androidmovieviewer.model.RestResult;
import org.mockito.Mockito;

public class LoginInteractorImplTest extends BaseActivityTestCase<LoginActivity> {

    private LoginInteractor loginInteractor;
    private LoginCallbackListener loginCallback;

    public LoginInteractorImplTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        loginCallback = Mockito.mock(LoginCallbackListener.class);
        loginInteractor = new LoginInteractorImpl();
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
        String expectedSessionId = "success";
        loginInteractor.onPostLogin(new RestResult(true, expectedSessionId), loginCallback);
        Mockito.verify(loginCallback, Mockito.times(1)).onPostLoginSuccess();

        SharedPreferences sharedPreferences = MovieViewApplication.getMovieViewerSharedPreferences();
        String actualSessionId = sharedPreferences.getString(LoginInteractorImpl.SESSION_ID_PREF_PARAM, null);
        assertNotNull(actualSessionId);
        assertEquals("Session Id is not equal", expectedSessionId, actualSessionId);
    }
}
