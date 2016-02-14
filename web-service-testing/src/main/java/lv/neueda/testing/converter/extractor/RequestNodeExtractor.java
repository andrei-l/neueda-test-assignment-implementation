package lv.neueda.testing.converter.extractor;


import lv.neueda.testing.WebServiceTestGroup;
import lv.neueda.testing.mindmap.pojo.xml.Node;

import java.util.List;
import java.util.regex.Pattern;

public class RequestNodeExtractor extends AbstractExtractor<WebServiceTestGroup, WebServiceTestGroup> {
	private static final Pattern METHOD_PATTERN = Pattern.compile("Method: ([A-Z]+)");
	private static final Pattern PATH_PATTERN = Pattern.compile("Path: (.+)");

	@Override
	public WebServiceTestGroup doExtract(Node node, Object expectedEntity) {
		List<Object> nodes = node.getNodeOrArrowlinkOrLinktarget();
		String path = extractData(nodes, PATH_PATTERN, 1, false);
		String method = extractData(nodes, METHOD_PATTERN, 1, false);
		WebServiceTestGroup webServiceTestGroup;
		if (isExpectedEntity(expectedEntity)) {
			webServiceTestGroup = castToExpectedEntity(expectedEntity);
			webServiceTestGroup.setPath(path);
			webServiceTestGroup.setHttpMethod(method);
		} else {
			throw createException(node, expectedEntity);
		}
		return webServiceTestGroup;
	}

	@Override
	public boolean isApplicable(Object node) {
		return node instanceof Node && ((Node) node).getTEXT().equals("Request");
	}

	@Override
	protected Class<WebServiceTestGroup> getExpectedEntityClass() {
		return WebServiceTestGroup.class;
	}
}
