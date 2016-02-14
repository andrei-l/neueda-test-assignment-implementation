package lv.neueda.testing.converter;

import lv.neueda.testing.WebServiceTest;
import lv.neueda.testing.WebServiceTestCase;
import lv.neueda.testing.WebServiceTestGroup;
import lv.neueda.testing.mindmap.parser.xml.XMLMindMapParserImpl;
import lv.neueda.testing.mindmap.pojo.xml.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class XMLNodeMapToWebServiceTestConverterComponentTest {
	private XMLNodeMapToWebServiceTestConverter xmlNodeMapToWebServiceTestConverter = new XMLNodeMapToWebServiceTestConverter();
	private Map map;

	@Before
	public void setUp() throws Exception {
		map = new XMLMindMapParserImpl().parseMindMap(new File("src/test/resources/calc_tests.mm"));
	}

	@Test
	public void testConvertXMLMap() throws Exception {
		WebServiceTest webServiceTest = xmlNodeMapToWebServiceTestConverter.convertXMLMap(map);
		List<WebServiceTestGroup> webServiceTestGroups = webServiceTest.getWebServiceTestGroups();

		Assert.assertEquals("Calculator tests", webServiceTest.getWebServiceTestsName());
		Assert.assertEquals(4, webServiceTestGroups.size());
		WebServiceTestGroup multiplyTestGroup = getGroupByName(webServiceTestGroups, "Multiply");
		WebServiceTestGroup divideTestGroup = getGroupByName(webServiceTestGroups, "Divide");
		WebServiceTestGroup addTestGroup = getGroupByName(webServiceTestGroups, "Add");
		WebServiceTestGroup subtractTestGroup = getGroupByName(webServiceTestGroups, "Subtract");

		assertTestGroupMetadata(multiplyTestGroup, "GET", "/api/multiply", 2);
		assertTestGroupMetadata(divideTestGroup, "GET", "/api/divide", 3);
		assertTestGroupMetadata(addTestGroup, "GET", "/api/add", 2);
		assertTestGroupMetadata(subtractTestGroup, "GET", "/api/subtract", 2);

		List<WebServiceTestCase> multiplyTestCases = multiplyTestGroup.getWebServiceTestCases();
		List<WebServiceTestCase> divideTestCases = divideTestGroup.getWebServiceTestCases();
		List<WebServiceTestCase> addTestCases = addTestGroup.getWebServiceTestCases();
		List<WebServiceTestCase> subtractTestCases = subtractTestGroup.getWebServiceTestCases();

		assertTestCaseMetadata(
				getTestByName(multiplyTestCases, "simple multiplication"),
				"a",
				"b",
				"5",
				"9",
				"45"
		);

		assertTestCaseMetadata(
				getTestByName(multiplyTestCases, "multiplying negatives"),
				"a",
				"b",
				"-2.3",
				"-6.76",
				"15.548"
		);

		assertTestCaseMetadata(
				getTestByName(divideTestCases, "simple division"),
				"a",
				"b",
				"5",
				"2",
				"2.5"
		);

		assertTestCaseMetadata(
				getTestByName(divideTestCases, "division by negative number"),
				"a",
				"b",
				"22.36",
				"-5",
				"-4.472"
		);

		assertTestCaseMetadata(
				getTestByName(divideTestCases, "division by zero"),
				"a",
				"b",
				"10",
				"0",
				"DIV/0"
		);

		assertTestCaseMetadata(
				getTestByName(addTestCases, "simple addition"),
				"a",
				"b",
				"6",
				"8",
				"14"
		);

		assertTestCaseMetadata(
				getTestByName(addTestCases, "adding a negative number"),
				"a",
				"b",
				"-5.34",
				"3.95",
				"-1.39"
		);

		assertTestCaseMetadata(
				getTestByName(subtractTestCases, "simple subtraction"),
				"a",
				"b",
				"97",
				"58",
				"39"
		);


		assertTestCaseMetadata(
				getTestByName(subtractTestCases, "subtracting a negative number"),
				"a",
				"b",
				"-34.12",
				"-55.7",
				"21.58"
		);
	}

	private WebServiceTestGroup getGroupByName(List<WebServiceTestGroup> testGroups, String groupName) {
		for (WebServiceTestGroup webServiceTestGroup : testGroups) {
			if (groupName.equals(webServiceTestGroup.getGroupName())) {
				return webServiceTestGroup;
			}
		}
		return null;
	}

	private WebServiceTestCase getTestByName(List<WebServiceTestCase> testCases, String testName) {
		for (WebServiceTestCase testCase : testCases) {
			if (testName.equals(testCase.getTestName())) {
				return testCase;
			}
		}
		return null;
	}

	private void assertTestGroupMetadata(WebServiceTestGroup testGroup, String method, String path, int expectedNumberOfTestCases) {
		Assert.assertNotNull(testGroup);
		Assert.assertEquals(method, testGroup.getHttpMethod());
		Assert.assertEquals(path, testGroup.getPath());
		Assert.assertEquals(expectedNumberOfTestCases, testGroup.getWebServiceTestCases().size());
	}

	private void assertTestCaseMetadata(WebServiceTestCase testCase,
	                                    String firstParamName,
	                                    String secondParamName,
	                                    String firstParamValue,
	                                    String secondParamValue,
	                                    String expectedResult) {
		Assert.assertNotNull(testCase);
		Assert.assertEquals(firstParamName, testCase.getFirstParamName());
		Assert.assertEquals(secondParamName, testCase.getSecondParamName());
		Assert.assertEquals(firstParamValue, testCase.getFirstParamValue());
		Assert.assertEquals(secondParamValue, testCase.getSecondParamValue());
		Assert.assertEquals(expectedResult, testCase.getExpectedResult());
	}
}