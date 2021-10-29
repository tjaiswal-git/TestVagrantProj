package tests;

import actions.APIActions;
import actions.AssertActions;
import com.testvagrant.project.apiobjects.TempDataFromApi;
import com.testvagrant.project.customexcpetion.TempNotInRangeException;
import com.testvagrant.project.pageobjects.TempCollectorObject;
import com.testvagrant.project.utils.ConversionUtil;
import com.testvagrant.project.utils.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Properties;

public class TestVagrantTempCheckTest extends TestBase
{

	private static Logger Logger = org.apache.log4j.Logger.getLogger(TestVagrantTempCheckTest.class.getName());
	TempCollectorObject tempCollectorObject;

	@BeforeTest
	public void setUp()
	{
		Properties properties = getProp();
		String URL1 = properties.getProperty("URL");
		init(URL1);
	}

	@Test()
	@Owner("Promode")
	@Severity(SeverityLevel.NORMAL)
	@Description("Verify the temperature from two source")
	public void testTempFromTwoSources() throws TempNotInRangeException
	{

		//api class object
		TempDataFromApi tempDataFromApi = new TempDataFromApi();
		Response response = tempDataFromApi.getRequestResponse();

		AssertActions assertActions = new AssertActions();
		String tempFromAPI = APIActions.getActualPath(APIActions.rawJSON(response),"main.temp");
		tempFromAPI = ConversionUtil.degreeToCelsius(tempFromAPI);

		logger.info("Temperature's from API side "+ tempFromAPI +" Degree Celsius");

		//ui class object
		tempCollectorObject = new TempCollectorObject(driver);
		String tempfromWeb = tempCollectorObject.getTempFromUI();
		logger.info("temperature's from web app is "+tempfromWeb +" Degree Celsius");

		boolean tempStatus = tempCollectorObject.tempCompare(tempFromAPI, tempfromWeb);

		if(!tempStatus)
		{
			assertActions.verifyStatusCode(response);
			throw new TempNotInRangeException("From two sources the temperature's are not in specified range");
		}
		else
			{
			assertActions.verifyStatusCode(response);
			assertActions.verifyResponseBody(true, "Temperature's is in specified range");
			}
	}

	@AfterTest
	public void tearDown()
	{
		 quitDriver();
	}

}
