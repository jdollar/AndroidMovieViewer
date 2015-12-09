package org.dollarhide.androidmovieviewer.activity;

import android.app.Instrumentation;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.Button;
import org.dollarhide.androidmovieviewer.activity.login.impl.LoginActivity;
import org.dollarhide.androidmovieviewer.movieviewer.R;

public class HomeActivityTest extends BaseActivityTestCase<Home> {

    private Home homeActivityTest;
    private Button searchButton;
    private Button loginButton;
    private Button settingsButton;

    public HomeActivityTest() {
        super(Home.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        homeActivityTest = getMovieViewerActivity();
        searchButton = (Button) homeActivityTest.findViewById(R.id.search_button);
        loginButton = (Button) homeActivityTest.findViewById(R.id.login_button_home);
        settingsButton = (Button) homeActivityTest.findViewById(R.id.settings_button);
    }

    @MediumTest
    public void testHomeActivity_checkViewGenerations() {
        final View decorView = homeActivityTest.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, searchButton);
        ViewAsserts.assertOnScreen(decorView, settingsButton);
        ViewAsserts.assertOnScreen(decorView, loginButton);
    }

    @MediumTest
    public void testHomeActivity_openSearchStartsSearchActivity() {
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(SearchActivity.class.getName(), null, false);
        TouchUtils.clickView(this, searchButton);

        SearchActivity searchActivity = (SearchActivity) getInstrumentation().waitForMonitorWithTimeout(monitor, 3);
        assertNotNull(searchActivity);
        searchActivity.finish();
    }

    @MediumTest
    public void testHomeActivity_openLoginStartsLoginActivity() {
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);
        TouchUtils.clickView(this, loginButton);

        LoginActivity loginActivity = (LoginActivity) getInstrumentation().waitForMonitorWithTimeout(monitor, 3);
        assertNotNull(loginActivity);
        loginActivity.finish();
    }

    @Override
    protected Home getMovieViewerActivity() {
        return (Home) super.getMovieViewerActivity();
    }
}
