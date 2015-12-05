package org.dollarhide.androidmovieviewer.service;

import org.apache.http.client.methods.HttpGet;
import org.dollarhide.androidmovieviewer.model.RestResult;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;
import org.dollarhide.androidmovieviewer.util.ResponseParserUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class AuthenticationServiceTest extends BaseServiceTestCase{

    private static final String TAG = "AuthenticationServiceTest";

    @PrepareForTest({ResourcePropertyReader.class, ResponseParserUtil.class})
    @Test
    public void testNewAuthenticationToken_successWithData() throws JSONException, IOException {
        String expectedData = "requestTokenTest";
        JSONObject expectedJSONObject = new JSONObject();
        expectedJSONObject.put(AuthenticationService.SUCCESS_PARAM, true);
        expectedJSONObject.put(AuthenticationService.REQUEST_TOKEN, expectedData);

        PowerMockito.mockStatic(ResponseParserUtil.class);
        PowerMockito.mockStatic(ResourcePropertyReader.class);

        when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);
        when(mockStatusLine.getStatusCode()).thenReturn(200);
        PowerMockito.when(ResponseParserUtil.readHttpResponseToJson(mockHttpResponse))
                .thenReturn(expectedJSONObject);
        PowerMockito.when(ResourcePropertyReader.getProperty(AuthenticationService.AUTHENTICATION_NEW_PARAM))
                .thenReturn("testUrl{0}");

        RestResult result = getService().getNewAuthenticationToken();
        assert result.getSuccessFlag();
        assert expectedData.equals(result.getData());
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
