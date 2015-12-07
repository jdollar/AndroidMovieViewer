package org.dollarhide.androidmovieviewer.activity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import org.dollarhide.androidmovieviewer.movieviewer.R;

public class HomeActivityTest extends ActivityUnitTestCase<Home> {

    private Home homeActivityTest;
    private Intent homeIntent;

    public HomeActivityTest() {
        super(Home.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        homeIntent = new Intent(getInstrumentation().getTargetContext(), Home.class);
        /*MovieViewApplication application = new MovieViewApplication();
        application.setTheme(R.style.Theme_AppCompat);
        setApplication(application);*/
        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.Theme_AppCompat);

        setActivityContext(context);
        setActivity(launchActivity("org.dollarhide.androidmovieviewer.movieviewer", Home.class, null));
        //startActivity(homeIntent, null, null);
        homeActivityTest = getActivity();
    }

    @MediumTest
    public void testHomeActivity_openSearchStartsSearchActivity() {
        Button searchButton = (Button) getActivity().findViewById(R.id.search_button);
        searchButton.performClick();

        final Intent launchedIntent = getStartedActivityIntent();
        assertNotNull(launchedIntent);
        assertEquals(SearchActivity.class.getName(), launchedIntent.getComponent().getClassName());
        getActivity().finish();
    }

    @UiThreadTest
    @MediumTest
    public void testHomeActivity_openLoginStartsLoginActivity() {
        Button loginButton = (Button) getActivity().findViewById(R.id.login_button_home);
        loginButton.performClick();

        final Intent launchedIntent = getStartedActivityIntent();
        assertNotNull(launchedIntent);
        assertEquals(LoginActivity.class.getName(), launchedIntent.getComponent().getClassName());
        getActivity().finish();
    }
}
