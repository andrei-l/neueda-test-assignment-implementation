package lv.neueda.testing;


import lv.neueda.testing.converter.XMLNodeMapToWebServiceTestConverter;
import lv.neueda.testing.framework.WebServiceTestingFramework;
import lv.neueda.testing.mindmap.parser.xml.XMLMindMapParserImpl;
import lv.neueda.testing.mindmap.pojo.xml.Map;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;

public class WebServiceTesting {
	public static void main(String[] args) {
		if (validArgs(args)) {
			Map map = new XMLMindMapParserImpl().parseMindMap(new File(args[0]));
			WebServiceTest webServiceTest = new XMLNodeMapToWebServiceTestConverter().convertXMLMap(map);
			WebServiceTestingFramework webServiceTestingFramework = new WebServiceTestingFramework(args[1]);
			webServiceTestingFramework.addWebServiceTest(webServiceTest);
			webServiceTestingFramework.performTests();
		} else {
			System.out.println("Expecting 2 valid args 1) mindmap file as first arg 2) url to calc web as second arg");
		}
	}

	private static boolean validArgs(String[] args) {
		return args.length >= 2 && new File(args[0]).exists() && UrlValidator.getInstance().isValid(args[1]);
	}
}
