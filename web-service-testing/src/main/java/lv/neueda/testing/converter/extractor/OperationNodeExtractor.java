package lv.neueda.testing.converter.extractor;


import lv.neueda.testing.WebServiceTestGroup;
import lv.neueda.testing.mindmap.pojo.xml.Node;

import java.util.List;
import java.util.regex.Pattern;

public class OperationNodeExtractor extends AbstractExtractor<WebServiceTestGroup, List> {
	private static final Pattern ONLY_TEXT_PATTERN = Pattern.compile("[a-zA-z]+");

	@Override
	public WebServiceTestGroup doExtract(Node node, Object expectedEntity) {
		WebServiceTestGroup webServiceTestGroup;
		if (isExpectedEntity(expectedEntity)) {
			webServiceTestGroup = new WebServiceTestGroup(node.getTEXT());
			List<WebServiceTestGroup> webServiceTestGroups = castToExpectedEntity(expectedEntity);
			webServiceTestGroups.add(webServiceTestGroup);
			return webServiceTestGroup;
		} else {
			throw createException(node, expectedEntity);
		}
	}

	@Override
	public boolean isApplicable(Object node) {
		return node instanceof Node && ONLY_TEXT_PATTERN.matcher(((Node) node).getTEXT()).matches();
	}

	@Override
	protected Class<List> getExpectedEntityClass() {
		return List.class;
	}
}
