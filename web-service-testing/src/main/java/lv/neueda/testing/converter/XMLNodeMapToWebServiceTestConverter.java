package lv.neueda.testing.converter;


import com.google.common.collect.Lists;
import lv.neueda.testing.WebServiceTest;
import lv.neueda.testing.converter.extractor.Extractor;
import lv.neueda.testing.converter.extractor.OperationNodeExtractor;
import lv.neueda.testing.converter.extractor.RequestNodeExtractor;
import lv.neueda.testing.converter.extractor.TestCaseNodeExtractor;
import lv.neueda.testing.mindmap.pojo.xml.Map;
import lv.neueda.testing.mindmap.pojo.xml.Node;

import java.util.List;

public class XMLNodeMapToWebServiceTestConverter {
	private static final List<Extractor> ORDERED_EXTRACTORS =
			Lists.<Extractor>newArrayList(new RequestNodeExtractor(), new TestCaseNodeExtractor(), new OperationNodeExtractor());

	public WebServiceTest convertXMLMap(Map xmlMap) {
		Node rootNode = xmlMap.getNode();
		WebServiceTest webServiceTest = new WebServiceTest(rootNode.getTEXT());
		List<Object> childNodes = rootNode.getNodeOrArrowlinkOrLinktarget();

		extractNodes(childNodes, webServiceTest.getWebServiceTestGroups());

		return webServiceTest;
	}

	private void extractNodes(List<Object> childNodes, Object entity) {
		for (Object child : childNodes) {
			for (Extractor extractor : ORDERED_EXTRACTORS) {
				if (extractor.isApplicable(child)) {
					Object returnedEntity = extractor.extract(child, entity);
					extractNodes(((Node) child).getNodeOrArrowlinkOrLinktarget(), returnedEntity);
					break;
				}
			}
		}
	}
}
