package lv.neueda.testing.framework.reesponse;

import org.apache.commons.io.input.ReaderInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.StringReader;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpResponseAssertionImplUnitTest {
	private HttpResponseAssertionImpl httpResponseAssertion = new HttpResponseAssertionImpl();

	@Mock
	private HttpResponse httpResponse;

	@Mock
	private StatusLine statusLine;

	@Mock
	private HttpEntity httpEntity;

	@Test
	public void testAssertExpectedAllValid() throws Exception {
		StringReader result = new StringReader("{'result':5}");
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(200);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpEntity.getContent()).thenReturn(new ReaderInputStream(result));
		Assert.assertTrue(httpResponseAssertion.assertExpected(httpResponse, "5"));
	}

	@Test
	public void testAssertExpectedInvalidContent() throws Exception {
		StringReader result = new StringReader("{'result':4}");
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(200);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpEntity.getContent()).thenReturn(new ReaderInputStream(result));
		Assert.assertFalse(httpResponseAssertion.assertExpected(httpResponse, "5"));
	}

	@Test
	public void testAssertExpectedInvalidStatus() throws Exception {
		StringReader result = new StringReader("{'result':5}");
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(300);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpEntity.getContent()).thenReturn(new ReaderInputStream(result));
		Assert.assertFalse(httpResponseAssertion.assertExpected(httpResponse, "5"));
	}

	@Test(expected = IllegalStateException.class)
	public void testAssertExpectedInvalidResponseFormat() throws Exception {
		StringReader result = new StringReader("asdsa");
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(300);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpEntity.getContent()).thenReturn(new ReaderInputStream(result));
		Assert.assertFalse(httpResponseAssertion.assertExpected(httpResponse, "5"));
	}

	@Test
	public void testAssertExpectedValidResponseFormatButMissingResult() throws Exception {
		StringReader result = new StringReader("{}");
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(300);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpEntity.getContent()).thenReturn(new ReaderInputStream(result));
		Assert.assertFalse(httpResponseAssertion.assertExpected(httpResponse, "5"));
	}
}