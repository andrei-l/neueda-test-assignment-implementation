package lv.neueda.testing.framework.request;


import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.List;

public interface HttpRequestFactory {
	HttpUriRequest createRequest(String method, String finalPath, List<Pair<String, String>> paramNameToValuePairs);
}
