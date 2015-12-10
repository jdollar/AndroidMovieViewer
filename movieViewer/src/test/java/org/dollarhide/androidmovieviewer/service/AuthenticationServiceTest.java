package org.dollarhide.androidmovieviewer.service;

import org.apache.http.client.methods.HttpGet;
import org.dollarhide.androidmovieviewer.model.RestResult;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;
import org.json.JSONObject;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.dollarhide.androidmovieviewer.service.AuthenticationService.*;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest extends BaseServiceTestCase{
    private static final String TAG = "AuthenticationServiceTest";

    @Test
    public void testNewAuthenticationToken_successWithData() throws IOException {
        String expectedData = "requestTokenTest";
        RestResult expectedRestResult = new RestResult(true, expectedData);
        JSONObject expectedJSONObject = setupJSONData(true, REQUEST_TOKEN, expectedData);

        newAuthenticationTokenTest_common(200, expectedJSONObject, expectedRestResult);
    }

    @Test
    public void testNewAuthenticiationToken_successWithBlankData() throws IOException {
        String expectedData = "";
        RestResult expectedRestResult = new RestResult(true, expectedData);
        JSONObject expectedJSONObject = setupJSONData(true, REQUEST_TOKEN, expectedData);

        newAuthenticationTokenTest_common(200, expectedJSONObject, expectedRestResult);
    }

    @Test
    public void testNewAuthenticiationToken_failureWithData() throws IOException {
        String expectedData = "Bad Data";
        RestResult expectedRestResult = new RestResult(false, expectedData);
        JSONObject expectedJSONObject = setupJSONData(false, REQUEST_TOKEN, expectedData);

        newAuthenticationTokenTest_common(200, expectedJSONObject, expectedRestResult);
    }

    @Test
    public void testNewAuthenticiationToken_failureBlankData() throws IOException {
        String expectedData = "";
        RestResult expectedRestResult = new RestResult(false, expectedData);
        JSONObject expectedJSONObject = setupJSONData(false, REQUEST_TOKEN, expectedData);

        newAuthenticationTokenTest_common(200, expectedJSONObject, expectedRestResult);
    }

    @Test
    public void testNewAuthenticiationToken_badRequestCode() {
        RestResult expectedRestResult = new RestResult(false, null);
        newAuthenticationTokenTest_common(403, null, expectedRestResult);
    }

    private void newAuthenticationTokenTest_common(int statusCode, JSONObject expectedJSONObject, RestResult expectedRestResult) {
        try {
            String testUrl = "testUrl{0}";
            commonMockSetup(statusCode, AUTHENTICATION_NEW_PARAM, testUrl, expectedJSONObject);
            assertRestResult(expectedRestResult, getService().getNewAuthenticationToken());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception has occurred when it should not.");
        }
    }

    @Test
    public void testLogin_successWithData() {
        String expectedData = "true";
        RestResult expectedRestResult = new RestResult(Boolean.TRUE, expectedData);
        JSONObject expectedJSONObject = setupJSONData(true, SUCCESS_PARAM, expectedData);
        testLogin_common(200, expectedJSONObject, expectedRestResult);
    }

    @Test
    public void testLogin_failureWithData() {
        String expectedData = "false";
        RestResult expectedRestResult = new RestResult(Boolean.FALSE, expectedData);
        JSONObject expectedJSONObject = setupJSONData(false, SUCCESS_PARAM, expectedData);
        testLogin_common(200, expectedJSONObject, expectedRestResult);
    }

    @Test
    public void testLogin_badRequestCode() {
        RestResult expectedRestResult = new RestResult(Boolean.FALSE, null);
        testLogin_common(403, null, expectedRestResult);
    }

    private void testLogin_common(int statusCode, JSONObject expectedJSONObject, RestResult expectedResult) {
        try {
            String testUrl = "test{0}{1}{2}{3}";
            commonMockSetup(statusCode, AUTHENTICATION_VALIDATE_LOGIN_PARAM, testUrl, expectedJSONObject);
            assertRestResult(expectedResult, getService().login("username", "password", "requestToken"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception has occurred when it should not.");
        }
    }

    private void commonMockSetup(int statusCode, final String serviceProp, final String serviceUrl, JSONObject expectedJSONObject) {
        try {
            Properties testApiProperties = setupPropertiesData(createPropertyMap(serviceProp, serviceUrl));
            ResourcePropertyReader.setApiProperties(testApiProperties);

            when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);
            when(mockStatusLine.getStatusCode()).thenReturn(statusCode);
            if (expectedJSONObject != null) {
                when(mockHttpEntity.getContent())
                        .thenReturn(new ByteArrayInputStream(expectedJSONObject.toString().getBytes(UTF_8)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception has occurred when it should not.");
        }
    }

    @Test
    public void testGetSessionId_successWithData() throws IOException {
        String expectedData = "sessionIdTest";
        RestResult expectedRestResult = new RestResult(true, expectedData);
        JSONObject expectedJSONObject = setupJSONData(true, SESSION_TOKEN, expectedData);

        testGetSessionId_common(200, expectedJSONObject, expectedRestResult);
    }

    @Test
    public void testGetSessionId_successWithBlankData() throws IOException {
        String expectedData = "";
        RestResult expectedRestResult = new RestResult(true, expectedData);
        JSONObject expectedJSONObject = setupJSONData(true, SESSION_TOKEN, expectedData);

        testGetSessionId_common(200, expectedJSONObject, expectedRestResult);
    }

    @Test
    public void testGetSessionId_failureWithData() throws IOException {
        String expectedData = "sessionIdTest";
        RestResult expectedRestResult = new RestResult(false, expectedData);
        JSONObject expectedJSONObject = setupJSONData(false, SESSION_TOKEN, expectedData);

        testGetSessionId_common(200, expectedJSONObject, expectedRestResult);
    }

    @Test
    public void testGetSessionId_failureBlankData() throws IOException {
        String expectedData = "";
        RestResult expectedRestResult = new RestResult(false, expectedData);
        JSONObject expectedJSONObject = setupJSONData(false, SESSION_TOKEN, expectedData);

        testGetSessionId_common(200, expectedJSONObject, expectedRestResult);
    }

    @Test
    public void testGetSessionId_badRequestCode() {
        RestResult expectedRestResult = new RestResult(false, null);
        testGetSessionId_common(403, null, expectedRestResult);
    }

    private void testGetSessionId_common(int statusCode, JSONObject expectedJSONObject, RestResult expectedRestResult) {
        try {
            String testUrl = "testUrl{0}{1}";
            commonMockSetup(statusCode, AUTHENTICATION_SESSION_NEW_PARAM, testUrl, expectedJSONObject);
            assertRestResult(expectedRestResult, getService().getSessionId("requestTokenTest"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception has occurred when it should not.");
        }
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

    private Map<String, String> createPropertyMap(String serviceProp, String serviceUrl) {
        Map<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put(serviceProp, serviceUrl);
        return propertyMap;
    }
}
