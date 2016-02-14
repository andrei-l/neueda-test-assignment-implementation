package lv.neueda.testing;


import com.google.common.collect.Lists;

import java.util.List;

public class WebServiceTestGroup {
	private String groupName;
	private String httpMethod;
	private String path;

	private List<WebServiceTestCase> webServiceTestCases = Lists.newArrayList();

	public WebServiceTestGroup(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<WebServiceTestCase> getWebServiceTestCases() {
		return webServiceTestCases;
	}

	public void add(WebServiceTestCase webServiceTestCase) {
		webServiceTestCases.add(webServiceTestCase);
	}
}
