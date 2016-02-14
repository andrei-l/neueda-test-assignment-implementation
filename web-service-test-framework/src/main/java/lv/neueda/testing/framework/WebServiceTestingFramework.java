package lv.neueda.testing.framework;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lv.neueda.testing.WebServiceTest;
import lv.neueda.testing.WebServiceTestCase;
import lv.neueda.testing.WebServiceTestGroup;
import lv.neueda.testing.framework.execution.FailSafeWebServiceTestCaseExecutorImpl;
import lv.neueda.testing.framework.execution.WebServiceTestCaseExecutor;

import java.util.List;
import java.util.logging.Logger;

public class WebServiceTestingFramework {
	private static final Logger LOGGER = Logger.getLogger(WebServiceTestingFramework.class.getName());
	private WebServiceTestCaseExecutor webServiceTestCaseExecutor;

	private List<WebServiceTest> webServiceTests;
	private String endpoint;

	public WebServiceTestingFramework(List<WebServiceTest> webServiceTests, String endpoint) {
		this.webServiceTests = webServiceTests;
		this.endpoint = endpoint;
		this.webServiceTestCaseExecutor = new FailSafeWebServiceTestCaseExecutorImpl();
	}

	public WebServiceTestingFramework(String endpoint) {
		this(Lists.<WebServiceTest>newArrayList(), endpoint);
	}

	public void addWebServiceTest(WebServiceTest webServiceTest) {
		webServiceTests.add(webServiceTest);
	}

	public void performTests() {
		List<String> failedTests = Lists.newArrayList();
		for (WebServiceTest webServiceTest : webServiceTests) {
			LOGGER.info("Started execution of tests: " + webServiceTest.getWebServiceTestsName());
			List<WebServiceTestGroup> webServiceTestGroups = webServiceTest.getWebServiceTestGroups();
			for (WebServiceTestGroup webServiceTestGroup : webServiceTestGroups) {
				LOGGER.info("Started execution of tests group: " + webServiceTestGroup.getGroupName());
				List<WebServiceTestCase> webServiceTestCases = webServiceTestGroup.getWebServiceTestCases();
				for (WebServiceTestCase webServiceTestCase : webServiceTestCases) {
					LOGGER.info("Started execution of test: " + webServiceTestCase.getTestName());
					boolean testPassed = webServiceTestCaseExecutor.executeTestCase(webServiceTestCase, endpoint, webServiceTestGroup.getPath(), webServiceTestGroup.getHttpMethod());
					if (!testPassed) {
						failedTests.add(webServiceTestCase.toString());
					}
				}
			}
		}
		if (failedTests.isEmpty()) {
			LOGGER.info("All tests passed!");
		} else {
			LOGGER.severe("Failed tests: \r\n" + Joiner.on("\r\n").join(failedTests));
		}
	}

}
