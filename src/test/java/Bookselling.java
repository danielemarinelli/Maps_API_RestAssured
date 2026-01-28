import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;



public class Bookselling {
    String Id;
    JsonPath js;

    @Test(priority = 1)
    public void AddBook(){
        RestAssured.baseURI="http://216.10.245.166";

        String response = given().log().all()
                .headers("Content-Type","application/json")
                .body(payload.AddBook("Lord Of The Rings","00prd999","--xyz--","J.R.R."))
                .when()
                .post("/Library/Addbook.php")
                .then()
                .assertThat().statusCode(200)
                .extract().response().asString();
        //convert the response into json
        js = BaseLibrary.rawFromJSONResponse(response);
        /* POST Response from SWAGGER is below:
        {
                "Msg": "successfully added",
                "ID": "bcd227"
        }
        */
        // let's extract the ID created:
        Id = js.getString("ID");
        System.out.println("Book Id is ---> "+Id);
        
    }

}
