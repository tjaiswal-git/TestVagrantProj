package com.testvagrant.project.endpoints;

import com.testvagrant.project.utils.DataUtils;

public class APIConstants
{
	public final static String baseUrl = DataUtils.getTestData("TestData", "BaseUrl", "Value");
	public final static String getWeather = "/data/2.5/weather";
	public final static String token = DataUtils.getTestData("TestData", "Token", "Value");;
	public final static String ZIP = "zip";
	public final static String APP_ID = "appid";
	public final static String ZIP_VALUE = DataUtils.getTestData("TestData",ZIP,"Value");
	public final static  String TEMP_VARIANCE = DataUtils.getTestData("TestData","TempVariance","Value");
}
