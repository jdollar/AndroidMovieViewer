package org.dollarhide.androidmovieviewer.service;

import org.apache.http.client.methods.HttpGet;
import org.dollarhide.androidmovieviewer.model.RestResult;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;
import org.json.JSONObject;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest extends BaseServiceTestCase{

    private static final String TAG = "AuthenticationServiceTest";

    @Test
    public void testNewAuthenticationToken_successWithData() {
        String expectedData = "requestTokenTest";
        JSONObject expectedJSONObject = new JSONObject();

        try {
            expectedJSONObject.put(AuthenticationService.SUCCESS_PARAM, true);
            expectedJSONObject.put(AuthenticationService.REQUEST_TOKEN, expectedData);

            Properties testApiProperties = new Properties();
            testApiProperties.put(AuthenticationService.AUTHENTICATION_NEW_PARAM, "testUrl{0}");
            testApiProperties.put(LoggingUtil.DEBUG_ENABLED_PARAM, "false");
            ResourcePropertyReader.setApiProperties(testApiProperties);
            when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);
            when(mockStatusLine.getStatusCode()).thenReturn(200);
            when(mockHttpEntity.getContent())
                    .thenReturn(new ByteArrayInputStream(expectedJSONObject.toString().getBytes(StandardCharsets.UTF_8)));

            RestResult result = getService().getNewAuthenticationToken();
            assert result.getSuccessFlag();
            assert expectedData.equals(result.getData());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception has occurred when it should not.");
        }
    }

    @Test
    public void testNewAuthenticiationToken_successWithBlankData() {
        assert false;
    }

    @Test
    public void testNewAuthenticiationToken_badRequestCode() {
        assert false;
    }

    @Test
    public void testNewAuthenticiationToken_failureWithData() {
        assert false;
    }

    @Test
    public void testNewAuthenticiationToken_failureBlankData() {
        assert false;
    }

    @Override
    protected BaseService createService() {
        return new AuthenticationService();
    }

    @Override
    protected AuthenticationService getService() {
        return (AuthenticationService) super.getService();
    }

}
