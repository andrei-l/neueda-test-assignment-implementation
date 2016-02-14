package lv.neueda.testing;


import com.google.common.collect.Lists;

import java.util.List;

public class WebServiceTest {
	private String webServiceTestsName;
	private List<WebServiceTestGroup> webServiceTestGroups = Lists.newArrayList();

	public WebServiceTest(String webServiceTestsName) {
		this.webServiceTestsName = webServiceTestsName;
	}

	public List<WebServiceTestGroup> getWebServiceTestGroups() {
		return webServiceTestGroups;
	}

	public String getWebServiceTestsName() {
		return webServiceTestsName;
	}
}
