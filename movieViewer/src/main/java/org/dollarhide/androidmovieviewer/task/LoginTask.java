package org.dollarhide.androidmovieviewer.task;

import org.dollarhide.androidmovieviewer.model.RestResult;
import org.dollarhide.androidmovieviewer.service.AuthenticationService;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;

public class LoginTask extends BaseTask {

    private static final String TAG = "LoginTask";
    private static int EXPECTED_PARAM_LENGTH = 2;

    private AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected RestResult doInBackground(Object[] params) {
        //default rest result with false for success flag to tell UI there has been an error
        RestResult result = new RestResult(Boolean.FALSE, null);

        //check params and log/return if errors found
        if (!checkParamsViable(TAG, params, EXPECTED_PARAM_LENGTH)) {
            return new RestResult(Boolean.FALSE, null);
        }

        if (!checkParamTypes(params)) {
            LoggingUtil.logDebug(TAG, "Incorrect Param types for specified task. Expecting String, String");
            return new RestResult(Boolean.FALSE, null);
        }

        //Start processing of login
        String username = (String) params[0];
        String password = (String) params[1];

        try {
            //get request token for authentication actions (60 minute expiration)
            result = authenticationService.getNewAuthenticationToken();

            //if error return to UI
            if (!result.getSuccessFlag()) {
                return result;
            }

            //send username and password to api. This will validate the request token.
            String requestToken = (String) result.getData();
            result = authenticationService.login(username, password, (String) result.getData());

            if (!result.getSuccessFlag()) {
                return result;
            }

            //using the validated request token get a sessionId which will be needed for logged in functions
            //on rest api
            return  authenticationService.getSessionId(requestToken);
        } catch (Exception e) {
            LoggingUtil.logException(TAG, e);
        }

        //send back a result with a false to tell the UI that a error has happened
        return new RestResult(Boolean.FALSE, null);
    }

    @Override
    protected boolean checkParamTypes(Object[] params) {
        return params[0] instanceof String
                && params[1] instanceof String;
    }
}
