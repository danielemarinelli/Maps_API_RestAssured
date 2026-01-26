import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {

    public static String placeIdFromPostResponse;
    public static String newPlaceAddress="70 Summer walk, Urbino Italy";

    public static void main(String[] args) {
        //given() - all input details
        //when() - submit the API  -- endpoint and method
        //then() - validate the response

        RestAssured.useRelaxedHTTPSValidation();   // ---> to bypass SSL verification
        RestAssured.baseURI="https://rahulshettyacademy.com/";
        String response = given().log().all().queryParam("key","qaclick123")
                        .headers("Content-Type","application/json")
                                .body(payload.AddPlace())
                                        .when()
                                                .post("/maps/api/place/add/json")
                                                        .then()
                                                                .assertThat().statusCode(200)
                        .body("scope",equalTo("APP"))
                                .header("server", "Apache/2.4.52 (Ubuntu)")  //header of the response (you can see it from Postman tool)
                .extract().response().asString();

        // body response is like this
        /*
        {
        "status": "OK",
        "place_id": "fefdabed63689d7f2fc69dba1d1f3ea3",
        "scope": "APP",
        "reference": "0723fade97ca07f4ad2b83f1727aa3b90723fade97ca07f4ad2b83f1727aa3b9",
        "id": "0723fade97ca07f4ad2b83f1727aa3b9"
        }
        */
        System.out.println(response);
        JsonPath jsp = new JsonPath(response);   // for parsing JSON structure
        placeIdFromPostResponse = jsp.getString("place_id");

        System.out.println("PlaceId created is: " +placeIdFromPostResponse);

        //Chaining --> create a place, update place address and retrieve the place

        given().log().all().queryParam("key","qaclick123")
                .headers("Content-Type","application/json")
                .body(payload.UpdateAddressPlace(placeIdFromPostResponse,newPlaceAddress))
                .when().put("/maps/api/place/update/json")
                .then()
                .assertThat().log().all().statusCode(200)
                /*
                Body of the PUT response is
                {
                    "msg": "Address successfully updated"
                }
                */

                .body("msg",equalTo("Address successfully updated"));


        //GetPlace with new address

        String responseUpdate = given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeIdFromPostResponse)
                .headers("Content-Type","application/json")
                .when().get("/maps/api/place/get/json")
                .then()
                .assertThat().log().all().statusCode(200)
                .extract().response().asString();

        JsonPath jsp_update = new JsonPath(responseUpdate);   // for parsing JSON structure
        String newAddressFromPutResponse = jsp_update.getString("address");

        System.out.println("New Address is: " +newAddressFromPutResponse);
        Assert.assertEquals(newAddressFromPutResponse, newPlaceAddress, "Address sis not update!! Mismatch!!");
    }
}