package lv.neueda.testing.converter.extractor;


import com.google.common.collect.Lists;
import lv.neueda.testing.WebServiceTestCase;
import lv.neueda.testing.WebServiceTestGroup;
import lv.neueda.testing.mindmap.pojo.xml.Node;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCaseNodeExtractor extends AbstractExtractor<WebServiceTestCase, WebServiceTestGroup> {
	private static final Pattern TEST_PATTERN = Pattern.compile("Test: (.+)");
	private static final Pattern ARG_PATTERN = Pattern.compile("([a-zA-Z]+): (.+)");
	private static final Pattern RESULT_PATTERN = Pattern.compile("result: (.+)");

	@Override
	public WebServiceTestCase doExtract(Node node, Object expectedEntity) {
		String testName = getFirstGroup(TEST_PATTERN, node.getTEXT());
		List<Object> nodes = node.getNodeOrArrowlinkOrLinktarget();
		List<Object> copyOfNodes = Lists.newArrayList(nodes);
		WebServiceTestCase webServiceTestCase;
		if (isExpectedEntity(expectedEntity)) {
			webServiceTestCase = new WebServiceTestCase();
			castToExpectedEntity(expectedEntity).add(webServiceTestCase);
		} else {
			throw createException(node, expectedEntity);
		}

		webServiceTestCase.setTestName(testName);
		webServiceTestCase.setFirstParamName(extractData(copyOfNodes, ARG_PATTERN, 1, false));
		webServiceTestCase.setFirstParamValue(extractData(copyOfNodes, ARG_PATTERN, 2, true));
		webServiceTestCase.setSecondParamName(extractData(copyOfNodes, ARG_PATTERN, 1, false));
		webServiceTestCase.setSecondParamValue(extractData(copyOfNodes, ARG_PATTERN, 2, true));
		webServiceTestCase.setExpectedResult(extractData(nodes, RESULT_PATTERN, 1, false));
		return webServiceTestCase;
	}

	@Override
	public boolean isApplicable(Object node) {
		return node instanceof Node && TEST_PATTERN.matcher(((Node) node).getTEXT()).matches();
	}

	@Override
	protected Class<WebServiceTestGroup> getExpectedEntityClass() {
		return WebServiceTestGroup.class;
	}

	private String getFirstGroup(Pattern pattern, String text) {
		Matcher matcher = pattern.matcher(text);

		return matcher.matches() ? matcher.group(1) : null;
	}
}
