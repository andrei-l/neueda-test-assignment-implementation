package lv.neueda.testing.framework.execution;


import com.google.common.collect.Lists;
import lv.neueda.testing.WebServiceTestCase;
import lv.neueda.testing.framework.reesponse.HttpResponseAssertionImpl;
import lv.neueda.testing.framework.reesponse.ResponseAssertion;
import lv.neueda.testing.framework.request.HttpRequestFactory;
import lv.neueda.testing.framework.request.HttpRequestFactoryImpl;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.logging.Logger;

public class FailSafeWebServiceTestCaseExecutorImpl implements WebServiceTestCaseExecutor {
	private static final Logger LOGGER = Logger.getLogger(FailSafeWebServiceTestCaseExecutorImpl.class.getName());
	private CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	private HttpRequestFactory httpRequestFactory = new HttpRequestFactoryImpl();
	private ResponseAssertion responseAssertion = new HttpResponseAssertionImpl();

	@Override
	public boolean executeTestCase(WebServiceTestCase testCase, String endpoint, String path, String method) {
		try {
			//HttpPost requestBase = new HttpPost(endpoint + path);
			HttpUriRequest request = httpRequestFactory.createRequest(
					method,
					endpoint + path,
					Lists.newArrayList(
							buildPair(testCase.getFirstParamName(), testCase.getFirstParamValue()),
							buildPair(testCase.getSecondParamName(), testCase.getSecondParamValue())
					)
			);
			request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
			CloseableHttpResponse httpResponse = httpClient.execute(request);
			httpResponse.close();
			return responseAssertion.assertExpected(httpResponse, testCase.getExpectedResult());
		} catch (IllegalStateException e) {
			LOGGER.severe(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private Pair<String, String> buildPair(String paramName, String paramValue) {
		return new ImmutablePair<String, String>(paramName, paramValue);
	}
}
