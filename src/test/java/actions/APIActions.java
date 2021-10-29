package actions;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class APIActions {

    public static JsonPath rawJSON(Response res) {
        String resStr = res.asString();
        JsonPath jsdata = new JsonPath(resStr);
        return jsdata;
    }

    public static XmlPath rawXML(Response res) {
        String resStr = res.asString();
        XmlPath jsdata = new XmlPath(resStr);
        return jsdata;
    }

    public static String getActualPath(JsonPath jsonPathObject, String jsonPathString)
    {
        return jsonPathObject.getString(jsonPathString);
    }
}
