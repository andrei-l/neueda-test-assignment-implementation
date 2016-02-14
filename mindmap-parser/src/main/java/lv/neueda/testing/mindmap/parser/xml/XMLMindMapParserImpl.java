package lv.neueda.testing.mindmap.parser.xml;


import lv.neueda.testing.mindmap.parser.MindMapParser;
import lv.neueda.testing.mindmap.pojo.xml.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLMindMapParserImpl implements MindMapParser<Map> {
	@Override
	public Map parseMindMap(File inputFile) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Map.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (Map) jaxbUnmarshaller.unmarshal(inputFile);
		} catch (JAXBException e) {
			throw new RuntimeException("Failed to parse file", e);
		}
	}
}
