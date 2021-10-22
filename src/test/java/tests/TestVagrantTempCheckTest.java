package tests;

import static io.restassured.RestAssured.given;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.testvagrant.project.customexcpetion.TempNotInRangeException;
import com.testvagrant.project.endpoints.APIConstants;
import com.testvagrant.project.pageobjects.TempCollectorObject;
import com.testvagrant.project.utils.JsonToString;
import com.testvagrant.project.utils.TestBase;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestVagrantTempCheckTest extends TestBase
{

	private static Logger Log = Logger.getLogger(TestVagrantTempCheckTest.class.getName());
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
	@Description("Verify that Status code is 200 when we get GitHub RequestPayloads of User")
	public void testGetRepoOfUser() throws TempNotInRangeException
	{

		Response response = given().
				queryParam(APIConstants.ZIP, APIConstants.ZIP_VALUE).
				queryParam(APIConstants.APP_ID, APIConstants.token).
				when().
				get(APIConstants.getWeather).
				then().
				extract().
				response();

		JsonToString jsonToString = new JsonToString();
		JsonPath path = jsonToString.rawJSON(response);
		Assert.assertEquals(response.getStatusCode(), 200);
		String tempFromAPI = path.getString("main.temp");

		tempCollectorObject = new TempCollectorObject(driver);

		String tempfromWeb = tempCollectorObject.getTempFromUI();

		boolean tempStatus = tempCollectorObject.tempCompare(tempFromAPI, tempfromWeb);

		if(!tempStatus)
		{
			throw new TempNotInRangeException("From two sources the temprature are not in specified range");
		}
		else
		{
			Assert.assertTrue(tempStatus);
		}
	}

	@AfterTest
	public void tearDown()
	{
		quitDriver();
	}

}
