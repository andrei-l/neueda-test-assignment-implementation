package lv.neueda.testing.framework.request;


import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class HttpRequestFactoryImpl implements HttpRequestFactory {
	@Override
	public HttpUriRequest createRequest(String method, String finalEndpoint, List<Pair<String, String>> paramNameToValuePairs) {
		try {
			if (equalsIgnoreCase("GET", method)) {
				return createGetRequest(finalEndpoint, paramNameToValuePairs);
			} else if (equalsIgnoreCase("POST", method)) {
				return createPostRequest(finalEndpoint, paramNameToValuePairs);
			} else {
				throw new IllegalStateException("Only GET and POST methods are supported");
			}
		} catch (Exception e) {
			throw new IllegalStateException("Failed to build request", e);
		}
	}

	private HttpUriRequest createGetRequest(String finalEndpoint, List<Pair<String, String>> paramNameToValuePairs) throws URISyntaxException {
		URIBuilder builder = new URIBuilder();
		URIBuilder uriBuilder = builder.setPath(finalEndpoint);
		for (Pair<String, String> paramNameToValuePair : paramNameToValuePairs) {
			uriBuilder.setParameter(paramNameToValuePair.getKey(), paramNameToValuePair.getValue());
		}
		return new HttpGet(uriBuilder.build());
	}

	private HttpUriRequest createPostRequest(String finalEndpoint, List<Pair<String, String>> paramNameToValuePairs) throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(finalEndpoint);
		JsonObject jsonObject = new JsonObject();
		for (Pair<String, String> paramNameToValuePair : paramNameToValuePairs) {
			jsonObject.addProperty(paramNameToValuePair.getKey(), paramNameToValuePair.getValue());
		}

		post.setEntity(new StringEntity(jsonObject.toString()));
		return post;
	}
}
