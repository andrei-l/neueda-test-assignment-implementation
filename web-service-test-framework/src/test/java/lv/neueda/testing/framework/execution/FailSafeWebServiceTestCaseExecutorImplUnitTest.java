package lv.neueda.testing.framework.execution;

import lv.neueda.testing.WebServiceTestCase;
import lv.neueda.testing.framework.reesponse.ResponseAssertion;
import lv.neueda.testing.framework.request.HttpRequestFactory;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FailSafeWebServiceTestCaseExecutorImplUnitTest {
	@InjectMocks
	private FailSafeWebServiceTestCaseExecutorImpl executor = new FailSafeWebServiceTestCaseExecutorImpl();

	@Mock
	private CloseableHttpClient httpClient;

	@Mock
	private HttpRequestFactory httpRequestFactory;

	@Mock
	private ResponseAssertion responseAssertion;

	@Mock
	private HttpUriRequest request;

	@Mock
	private CloseableHttpResponse response;

	@Test
	public void testExecuteTestCaseValidCase() throws Exception {
		when(httpRequestFactory.createRequest(eq("method"), eq("endpoint/path"), any(List.class))).thenReturn(request);
		when(httpClient.execute(request)).thenReturn(response);
		when(responseAssertion.assertExpected(response, null)).thenReturn(true);
		WebServiceTestCase testCase = new WebServiceTestCase();
		Assert.assertTrue(executor.executeTestCase(testCase, "endpoint", "/path", "method"));
		verify(request).setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
		verify(response).close();
	}

	@Test
	public void testExecuteTestCaseInvalidCase() throws Exception {
		when(httpRequestFactory.createRequest(eq("method"), eq("endpoint/path"), any(List.class))).thenReturn(request);
		when(httpClient.execute(request)).thenReturn(response);
		when(responseAssertion.assertExpected(response, null)).thenReturn(false);
		WebServiceTestCase testCase = new WebServiceTestCase();
		Assert.assertFalse(executor.executeTestCase(testCase, "endpoint", "/path", "method"));
		verify(request).setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
		verify(response).close();
	}

	@Test
	public void testExecuteTestCaseInvalidCaseAndExceptionOccurred() throws Exception {
		when(httpRequestFactory.createRequest(eq("method"), eq("endpoint/path"), any(List.class))).thenReturn(request);
		when(httpClient.execute(request)).thenReturn(response);
		when(responseAssertion.assertExpected(response, null)).thenThrow(new IllegalStateException());
		WebServiceTestCase testCase = new WebServiceTestCase();
		Assert.assertFalse(executor.executeTestCase(testCase, "endpoint", "/path", "method"));
		verify(request).setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
		verify(response).close();
	}
}