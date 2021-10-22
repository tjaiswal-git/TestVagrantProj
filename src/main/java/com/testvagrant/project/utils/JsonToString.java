package com.testvagrant.project.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class JsonToString {

    public static JsonPath rawJSON(Response res)
    {
        String resStr = res.asString();
        JsonPath jsdata = new JsonPath(resStr);
        return jsdata;
    }

    public static XmlPath rawXML(Response res)
    {
        String resStr = res.asString();
        XmlPath jsdata = new XmlPath(resStr);
        return jsdata;
    }

}
