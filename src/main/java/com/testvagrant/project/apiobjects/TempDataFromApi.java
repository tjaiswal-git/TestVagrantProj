package com.testvagrant.project.apiobjects;

import com.testvagrant.project.endpoints.APIConstants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TempDataFromApi
{
    public Response getRequestResponse()
    {

        Response response = given().
                queryParam(APIConstants.ZIP, APIConstants.ZIP_VALUE).
                queryParam(APIConstants.APP_ID, APIConstants.token).
                when().
                get(APIConstants.getWeather).
                then().
                extract().
                response();
        return response;
    }

}
