package org.dollarhide.androidmovieviewer.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.dollarhide.androidmovieviewer.model.RestResult;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseServiceTestCase {

    @Mock
    protected HttpClient mockHttpClient;

    @Mock
    protected HttpResponse mockHttpResponse;

    @Mock
    protected StatusLine mockStatusLine;

    @Mock
    protected HttpEntity mockHttpEntity;

    private BaseService service;

    @Before
    public void doSetUp() {
        service = createService();
        service.setHttpClient(mockHttpClient);

        when(mockHttpResponse.getEntity()).thenReturn(mockHttpEntity);
        when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
    }

    protected void assertRestResult(RestResult expectedResult, RestResult actualResult) {
        if (expectedResult == null) {
            assertNull(actualResult);
            return;
        }

        assertNotNull(actualResult);
        assertEquals(expectedResult.getSuccessFlag(), actualResult.getSuccessFlag());

        if (expectedResult.getData() == null) {
            assertNull(actualResult.getData());
            return;
        }

        assertNotNull(actualResult.getData());
        assertEquals(expectedResult.getData(), actualResult.getData());
    }

    protected abstract BaseService createService();
    protected BaseService getService() {
        return service;
    }
}
