package org.dollarhide.androidmovieviewer.activity;

import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import org.dollarhide.androidmovieviewer.activity.login.LoginPresenter;
import org.dollarhide.androidmovieviewer.activity.login.impl.LoginActivity;
import org.dollarhide.androidmovieviewer.movieviewer.R;
import org.mockito.Mockito;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class LoginActivityTest extends BaseActivityTestCase<LoginActivity> {

    private LoginActivity loginActivity;
    private LoginPresenter mockLoginPresenter;

    private TableRow successRow;
    private TextView successTextView;
    private TableRow errorRow;
    private TextView errorTextView;
    private EditText usernameInput;
    private EditText passwordInput;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockLoginPresenter = Mockito.mock(LoginPresenter.class);
        loginActivity = getMovieViewerActivity();
        loginActivity.setLoginPresenter(mockLoginPresenter);

        successRow = (TableRow) loginActivity.findViewById(R.id.successRow);
        successTextView = (TextView) loginActivity.findViewById(R.id.successTextView);
        errorRow = (TableRow) loginActivity.findViewById(R.id.errorRow);
        errorTextView = (TextView) loginActivity.findViewById(R.id.errorTextView);
        usernameInput = (EditText) loginActivity.findViewById(R.id.usernameInput);
        passwordInput = (EditText) loginActivity.findViewById(R.id.passwordInput);

    }

    @MediumTest
    public void testLoginActivityTest_testGenerateView() {
        final View decorView = loginActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, successRow);
        assertEquals(View.GONE, successRow.getVisibility());
        ViewAsserts.assertOnScreen(decorView, successTextView);
        ViewAsserts.assertOnScreen(decorView, errorRow);
        assertEquals(View.GONE, errorRow.getVisibility());
        ViewAsserts.assertOnScreen(decorView, errorTextView);
        ViewAsserts.assertOnScreen(decorView, usernameInput);
        ViewAsserts.assertOnScreen(decorView, passwordInput);
    }

    @UiThreadTest
    @MediumTest
    public void testLoginActivityTest_clearBannersSuccessShowing() {
        successRow.setVisibility(View.VISIBLE);
        errorRow.setVisibility(View.GONE);

        successTextView.setText("Test Text");
        errorTextView.setText("");

        testLoginActivityTest_clearBannersCommon();
    }

    @UiThreadTest
    @MediumTest
    public void testLoginActivityTest_clearBannersErrorShowing() {
        successRow.setVisibility(View.GONE);
        errorRow.setVisibility(View.VISIBLE);

        successTextView.setText("");
        errorTextView.setText("Test Text");

        testLoginActivityTest_clearBannersCommon();
    }

    @UiThreadTest
    @MediumTest
    public void testLoginActivityTest_clearBannersBothShowing() {
        successRow.setVisibility(View.VISIBLE);
        errorRow.setVisibility(View.VISIBLE);

        successTextView.setText("Test Text");
        errorTextView.setText("Test Text");

        testLoginActivityTest_clearBannersCommon();
    }

    @UiThreadTest
    @MediumTest
    public void testLoginActivityTest_clearBannersNoneShowing() {
        successRow.setVisibility(View.GONE);
        errorRow.setVisibility(View.GONE);

        successTextView.setText("");
        errorTextView.setText("");

        testLoginActivityTest_clearBannersCommon();
    }

    private void testLoginActivityTest_clearBannersCommon() {
        loginActivity.clearBanners();

        assertEquals(View.GONE, successRow.getVisibility());
        assertEquals("", successTextView.getText());

        assertEquals(View.GONE, errorRow.getVisibility());
        assertEquals("", errorTextView.getText());
    }

    @UiThreadTest
    @MediumTest
    public void testLoginActivityTest_loginAction() {
        String expectedUsername = "testUsername";
        String expectedPassword = "testPassword";
        usernameInput.setText(expectedUsername);
        passwordInput.setText(expectedPassword);

        doNothing().when(mockLoginPresenter).login(expectedUsername, expectedPassword);
        loginActivity.submitLogin(null);
        verify(mockLoginPresenter, times(1)).login(expectedUsername, expectedPassword);
    }

    @UiThreadTest
    @MediumTest
    public void testLoginActivityTest_updateSuccessBanner() {
        String expectedStatusText = "testStatus";
        int expectedVisibility = View.VISIBLE;
        loginActivity.updateSuccessBanner(expectedStatusText, expectedVisibility);

        assertEquals(expectedVisibility, successRow.getVisibility());
        assertEquals(expectedStatusText, successTextView.getText());
    }

    @UiThreadTest
    @MediumTest
    public void testLoginActivityTest_updateErrorBanner() {
        String expectedStatusText = "testStatus";
        int expectedVisibility = View.VISIBLE;
        loginActivity.updateErrorBanner(expectedStatusText, expectedVisibility);

        assertEquals(expectedVisibility, errorRow.getVisibility());
        assertEquals(expectedStatusText, errorTextView.getText());
    }

    @Override
    protected LoginActivity getMovieViewerActivity() {
        return (LoginActivity) super.getMovieViewerActivity();
    }
}
