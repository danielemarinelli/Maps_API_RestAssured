import io.restassured.path.json.JsonPath;

public class BaseLibrary {

    public static JsonPath rawFromJSONResponse(String response){
        JsonPath jsp = new JsonPath(response);   // for parsing JSON structure
        return jsp;
    }

}
