package lv.neueda.testing.framework.reesponse;


import org.apache.http.HttpResponse;

import java.io.IOException;

public interface ResponseAssertion {
	boolean assertExpected(HttpResponse response, String expectedResult) throws IOException;
}
