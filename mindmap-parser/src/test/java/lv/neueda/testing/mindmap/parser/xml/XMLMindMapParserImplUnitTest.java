package lv.neueda.testing.mindmap.parser.xml;

import lv.neueda.testing.mindmap.pojo.xml.Map;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class XMLMindMapParserImplUnitTest {
	private XMLMindMapParserImpl parser = new XMLMindMapParserImpl();
	@Test
	public void testParseMindMap() throws Exception {
		Map mindMap = parser.parseMindMap(new File("src/test/resources/calc_tests.mm"));
		Assert.assertNotNull(mindMap.getNode());
	}
}