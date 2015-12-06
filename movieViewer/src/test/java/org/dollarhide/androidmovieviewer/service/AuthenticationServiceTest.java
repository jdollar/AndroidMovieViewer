package org.dollarhide.androidmovieviewer.service;

import org.apache.http.client.methods.HttpGet;
import org.dollarhide.androidmovieviewer.model.RestResult;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;
import org.json.JSONObject;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.dollarhide.androidmovieviewer.service.AuthenticationService.*;
import static org.dollarhide.androidmovieviewer.util.LoggingUtil.DEBUG_ENABLED_PARAM;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest extends BaseServiceTestCase{
    private static final String TAG = "AuthenticationServiceTest";

    @Test
    public void testNewAuthenticationToken_successWithData() throws IOException {
        String expectedData = "requestTokenTest";
        RestResult expectedRestResult = new RestResult(true, expectedData);
        JSONObject expectedJSONObject = setupJSONData(true, REQUEST_TOKEN, expectedData);

        RestResult result = newAuthenticationTokenTest_common(200, expectedJSONObject);
        assertRestResult(expectedRestResult, result);
    }

    @Test
    public void testNewAuthenticiationToken_successWithBlankData() throws IOException {
        String expectedData = "";
        RestResult expectedRestResult = new RestResult(true, expectedData);
        JSONObject expectedJSONObject = setupJSONData(true, REQUEST_TOKEN, expectedData);

        RestResult result = newAuthenticationTokenTest_common(200, expectedJSONObject);
        assertRestResult(expectedRestResult, result);
    }

    @Test
    public void testNewAuthenticiationToken_badRequestCode() {
        RestResult expectedRestResult = new RestResult(false, null);
        RestResult result = newAuthenticationTokenTest_common(403, null);
        assertRestResult(expectedRestResult, result);
    }

    @Test
    public void testNewAuthenticiationToken_failureWithData() throws IOException {
        String expectedData = "Bad Data";
        RestResult expectedRestResult = new RestResult(false, expectedData);
        JSONObject expectedJSONObject = setupJSONData(false, REQUEST_TOKEN, expectedData);

        RestResult result = newAuthenticationTokenTest_common(200, expectedJSONObject);
        assertRestResult(expectedRestResult, result);
    }

    @Test
    public void testNewAuthenticiationToken_failureBlankData() throws IOException {
        String expectedData = "";
        RestResult expectedRestResult = new RestResult(false, expectedData);
        JSONObject expectedJSONObject = setupJSONData(false, REQUEST_TOKEN, expectedData);

        RestResult result = newAuthenticationTokenTest_common(200, expectedJSONObject);
        assertRestResult(expectedRestResult, result);
    }

    private RestResult newAuthenticationTokenTest_common(int statusCode, JSONObject expectedJSONObject) {
        try {
            Properties testApiProperties = setupPropertiesData(AUTHENTICATION_NEW_PARAM, "testUrl{0}");
            ResourcePropertyReader.setApiProperties(testApiProperties);

            when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);
            when(mockStatusLine.getStatusCode()).thenReturn(statusCode);
            if (expectedJSONObject != null) {
                when(mockHttpEntity.getContent())
                        .thenReturn(new ByteArrayInputStream(expectedJSONObject.toString().getBytes(UTF_8)));
            }

            return getService().getNewAuthenticationToken();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception has occurred when it should not.");
        }

        return null;
    }

    @Override
    protected BaseService createService() {
        return new AuthenticationService();
    }

    @Override
    protected AuthenticationService getService() {
        return (AuthenticationService) super.getService();
    }

    private JSONObject setupJSONData(boolean successValue, String dataName, String expectedData) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(SUCCESS_PARAM, successValue);
            jsonObject.put(dataName, expectedData);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred creating JSON test data");
        }

        return jsonObject;
    }

    private Properties setupPropertiesData(String serviceUrlParam, String serviceUrl) {
        Properties testApiProperties = new Properties();
        testApiProperties.put(serviceUrlParam, serviceUrl);
        testApiProperties.put(DEBUG_ENABLED_PARAM, "false");
        return testApiProperties;
    }
}
