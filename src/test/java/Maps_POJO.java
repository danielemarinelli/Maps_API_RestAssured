import POJO4MapsTest.AddPlace;
import POJO4MapsTest.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Maps_POJO {

    @Test
    public void deserializationTest(){

        RestAssured.baseURI="https://rahulshettyacademy.com";
        RestAssured.useRelaxedHTTPSValidation();   // ---> to bypass SSL verification

        AddPlace ap = new AddPlace();
        ap.setAccuracy(100);
        ap.setAddress("Piazza Venezia 14");
        ap.setLanguage("French");
        ap.setName("Spiderman");
        ap.setPhoneNumber("+(39)0010001533");
        ap.setWebsite("www.nfl.com");
        List<String> ty = new ArrayList<>();
        ty.add("rb");
        ty.add("wr");
        ty.add("lb");
        ap.setTypes(ty);
        Location loc = new Location();
        loc.setLat(-38.383494);
        loc.setLng(53.427362);
        ap.setLocation(loc);

        String response = given().log().all().queryParam("key","qaclick123")
                .headers("Content-Type","application/json")
                .body(ap)
                .when()
                .post("/maps/api/place/add/json")
                .then()
                .assertThat().statusCode(200)
                .extract().response().asString();
        System.out.println("######### Response: " + response);

    }


    @Test
    public void deserializationTestWithSpecBuilder(){

        // Details generic for every Maps API requests, we can collect them in a RequestSpecBuilder method
        // and ResponseSpecBuilder for API generic responses
        RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
                        .setContentType(ContentType.JSON).build();
        ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();
        RestAssured.useRelaxedHTTPSValidation();   // ---> to bypass SSL verification

        AddPlace ap = new AddPlace();
        ap.setAccuracy(100);
        ap.setAddress("Piazza Venezia 14");
        ap.setLanguage("French");
        ap.setName("Spiderman");
        ap.setPhoneNumber("+(39)0010001533");
        ap.setWebsite("www.nfl.com");
        List<String> ty = new ArrayList<>();
        ty.add("rb");
        ty.add("wr");
        ty.add("lb");
        ap.setTypes(ty);
        Location loc = new Location();
        loc.setLat(-38.383494);
        loc.setLng(53.427362);
        ap.setLocation(loc);

        // create RequestSpecification object and append it to when method
        RequestSpecification res = given().log().all().spec(reqSpec)
                .body(ap);
        // The code is more readable
        Response r = res.when()
                .post("/maps/api/place/add/json")
                .then()
                .spec(responseSpec)
                .extract().response();


                String response = r.asString();

        System.out.println("######### Response: " + response);

    }




}
