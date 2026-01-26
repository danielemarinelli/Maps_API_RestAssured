import files.payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        //given() - all input details
        //when() - submit the API  -- endpoint and method
        //then() - validate the response

        RestAssured.useRelaxedHTTPSValidation();   // ---> to bypass SSL verification
        RestAssured.baseURI="https://rahulshettyacademy.com/";
        given().log().all().queryParam("key","qaclick123")
                        .headers("Content-Type","application/json")
                                .body(payload.AddPlace())
                                        .when()
                                                .post("/maps/api/place/add/json")
                                                        .then().log().all()
                                                                .assertThat().statusCode(200)
                        .body("scope",equalTo("APP"))
                                .header("server", "Apache/2.4.52 (Ubuntu)");  //header of the response (you can see it from Postman tool)


        System.out.println("Hello world!");
    }
}