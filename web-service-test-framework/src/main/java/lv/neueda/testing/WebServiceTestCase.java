package lv.neueda.testing;


public class WebServiceTestCase {
	private String testName;
	private String firstParamName;
	private String secondParamName;
	private String firstParamValue;
	private String secondParamValue;
	private String expectedResult;

	public String getTestName() {
		return testName;
	}

	public String getFirstParamName() {
		return firstParamName;
	}

	public void setFirstParamName(String firstParamName) {
		this.firstParamName = firstParamName;
	}

	public String getSecondParamName() {
		return secondParamName;
	}

	public void setSecondParamName(String secondParamName) {
		this.secondParamName = secondParamName;
	}

	public String getFirstParamValue() {
		return firstParamValue;
	}

	public String getSecondParamValue() {
		return secondParamValue;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setFirstParamValue(String firstParamValue) {
		this.firstParamValue = firstParamValue;
	}

	public void setSecondParamValue(String secondParamValue) {
		this.secondParamValue = secondParamValue;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	@Override
	public String toString() {
		return "WebServiceTestCase{" +
				"testName='" + testName + '\'' +
				", firstParamName='" + firstParamName + '\'' +
				", secondParamName='" + secondParamName + '\'' +
				", firstParamValue='" + firstParamValue + '\'' +
				", secondParamValue='" + secondParamValue + '\'' +
				", expectedResult='" + expectedResult + '\'' +
				'}';
	}
}
