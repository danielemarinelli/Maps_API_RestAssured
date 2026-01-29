
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

@Test
public class Maps {
    String Id;
    JsonPath js;

    public void AddLocationFromExternalFile() throws IOException {
        // C:\Users\dmarinel\API\RSA_Maps_API\GoogleMaps\src\main\resources\AddLocation.json
        // content of the file to String -> content of file can convert into Byte -> Byte data to string
        RestAssured.baseURI="https://rahulshettyacademy.com";
        RestAssured.useRelaxedHTTPSValidation();   // ---> to bypass SSL verification
        String payload = new String(Files.readAllBytes(Paths.get(".\\src\\main\\resources\\AddLocation.json")));
        String response = given().log().all().queryParam("key","qaclick123")
                .headers("Content-Type","application/json")
                .body(payload)
                .when()
                .post("/maps/api/place/add/json")
                .then()
                .assertThat().statusCode(200)
                .extract().response().asString();
        System.out.println("######### Response: " + response);

        // Parse JSON response
        try {
            js = BaseLibrary.rawFromJSONResponse(response);
            /* POST Response from SWAGGER is below:
            {
            "status": "OK",
            "place_id": "928b51f64aed18713b0d164d9be8d67f",
            "scope": "APP",
            "reference": "736f3c9bec384af62a184a1936d42bb0736f3c9bec384af62a184a1936d42bb0",
            "id": "736f3c9bec384af62a184a1936d42bb0"
            }
            */
            // Extract values from response
            Id = js.getString("id");
            System.out.println("location ID is ---> "+Id);
            System.out.println("<--- ##### ---> ");
            String respStatus =  Id = js.getString("status");
            String respScope =  Id = js.getString("scope");
            // Assertions
            Assert.assertEquals(respStatus,"OK");
            Assert.assertEquals(respScope,"APP");
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            System.out.println("Raw response: " + response);
            throw e; // Re-throw to fail the test
        }
    }


}
