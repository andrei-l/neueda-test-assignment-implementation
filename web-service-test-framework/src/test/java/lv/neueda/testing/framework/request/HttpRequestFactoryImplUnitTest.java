package lv.neueda.testing.framework.request;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class HttpRequestFactoryImplUnitTest {
	private HttpRequestFactoryImpl httpRequestFactory = new HttpRequestFactoryImpl();

	@Test
	public void testCreateGETRequest() throws Exception {
		HttpUriRequest request = httpRequestFactory.createRequest("GET", "http://google.ru", Arrays.asList(createPair("a", "b")));

		Assert.assertEquals("GET", request.getMethod());
		Assert.assertEquals("http://google.ru?a=b", request.getURI().toString());
	}

	@Test
	public void testCreatePOSTRequest() throws Exception {
		HttpUriRequest request = httpRequestFactory.createRequest("POST", "http://google.ru", Arrays.asList(createPair("a", "b")));
		Assert.assertEquals("POST", request.getMethod());
		Assert.assertTrue(request instanceof HttpPost);
		Assert.assertEquals("{\"a\":\"b\"}", IOUtils.toString(((HttpPost) request).getEntity().getContent()));
	}

	@Test(expected = IllegalStateException.class)
	public void testCreatePUTRequest() throws Exception {
		httpRequestFactory.createRequest("PUT", "http://google.ru", Arrays.asList(createPair("a", "b")));
	}

	private Pair<String, String> createPair(String paramName, String paramValue) {
		return new ImmutablePair<String, String>(paramName, paramValue);
	}
}