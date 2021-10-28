package com.testvagrant.project.apiobjects;

import com.testvagrant.project.endpoints.APIConstants;
import com.testvagrant.project.utils.JsonToString;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class TempDataFromApi
{
    public String getTempFromAPISide()
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
        return tempFromAPI;
    }

}
