package org.dollarhide.androidmovieviewer.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

    protected abstract BaseService createService();
    protected BaseService getService() {
        return service;
    }
}
