package lv.neueda.testing.framework.reesponse;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.logging.Logger;

public class HttpResponseAssertionImpl implements ResponseAssertion {
	private static final Logger LOGGER = Logger.getLogger(HttpResponseAssertionImpl.class.getName());

	@Override
	public boolean assertExpected(HttpResponse response, String expectedResult) throws IOException {
		int statusCode = response.getStatusLine().getStatusCode();
		String contentAsString = getContentAsString(response);
		boolean statusOK = 200 == statusCode;
		logBadComparisonResult(statusOK, String.format("Expected status code: 200, actual: %d. Response: %s", statusCode, contentAsString));

		String result = extractResult(contentAsString);
		boolean resultAsExpected = expectedResult.equals(result);
		logBadComparisonResult(resultAsExpected, String.format("Expected result: %s, actual: %s", expectedResult, result));

		return statusOK && resultAsExpected;
	}

	private void logBadComparisonResult(boolean comparisonSucceeded, String msg) {
		if (!comparisonSucceeded) {
			LOGGER.severe(msg);
		}
	}


	private String extractResult(String contentAsString) throws IOException {
		if (StringUtils.isNotEmpty(contentAsString)) {
			if (isValidJson(contentAsString)) {
				JsonElement result = new JsonParser().parse(contentAsString).getAsJsonObject().get("result");
				if (result != null) {
					return result.getAsString();
				}
			}
		}
		return contentAsString;
	}

	private String getContentAsString(HttpResponse httpResponse) throws IOException {
		return IOUtils.toString(httpResponse.getEntity().getContent());
	}

	private boolean isValidJson(String input) {
		try {
			new Gson().fromJson(input, Object.class);
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			return false;
		}
	}
}
