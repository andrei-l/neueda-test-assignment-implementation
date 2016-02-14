package lv.neueda.testing.framework.execution;


import lv.neueda.testing.WebServiceTestCase;

public interface WebServiceTestCaseExecutor {
	boolean executeTestCase(WebServiceTestCase testCase, String endpoint, String path, String method);
}
