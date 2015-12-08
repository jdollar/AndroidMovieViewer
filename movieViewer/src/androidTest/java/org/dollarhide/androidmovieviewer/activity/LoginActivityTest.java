package org.dollarhide.androidmovieviewer.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import org.dollarhide.androidmovieviewer.movieviewer.R;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity loginActivity;
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
        setActivityInitialTouchMode(true);

        loginActivity = getActivity();

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
}
