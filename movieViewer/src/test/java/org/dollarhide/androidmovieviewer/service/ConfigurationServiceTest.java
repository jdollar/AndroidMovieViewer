package org.dollarhide.androidmovieviewer.service;

import junit.framework.Assert;
import org.apache.http.client.methods.HttpGet;
import org.dollarhide.androidmovieviewer.util.ResourcePropertyReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class ConfigurationServiceTest extends BaseServiceTestCase {

    @Override
    public void doSetUp() {
        super.doSetUp();
        setupPropertyData();
    }

    @Test
    public void testConfigurationService_getConfigurationThrowsIOException() {
        try {
            when(mockHttpClient.execute(any(HttpGet.class))).thenThrow(new IOException());

            getService().getConfiguration();
            fail();
        } catch (IOException e) {
            //Test passes if error was thrown
        } catch (JSONException e) {
            fail();
        }
    }

    @Test
    public void testConfigurationService_getConfigurationReturnsExpectedJson() {
        try {
            JSONObject expectedJsonObject = new JSONObject();
            expectedJsonObject.put("test", "testValue");
            byte[] expectedJsonBytes = expectedJsonObject.toString().getBytes(UTF_8);

            when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);
            when(mockHttpEntity.getContent()).thenReturn(new ByteArrayInputStream(expectedJsonBytes));

            JSONObject actualJosnObject = getService().getConfiguration();
            Assert.assertEquals(expectedJsonObject.get("test"), actualJosnObject.get("test"));
        } catch (Exception e) {
            fail();
        }
    }

    private void setupPropertyData() {
        HashMap<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put(ResourcePropertyReader.BASE_URL_PARAM, "testUrl");
        propertyMap.put(ResourcePropertyReader.API_KEY_PARAM, "testKey");
        ResourcePropertyReader.setApiProperties(setupPropertiesData(propertyMap));
    }

    @Override
    protected ConfigurationService createService() {
        return new ConfigurationService();
    }

    @Override
    protected ConfigurationService getService() {
        return (ConfigurationService) super.getService();
    }
}
