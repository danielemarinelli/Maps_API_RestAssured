import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;



public class Bookselling {
    String Id;
    JsonPath js;

    @Test(priority = 1)
    public void AddBookDeleteBook(){
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
        System.out.println("<--- ##### ---> ");

        String responseDelete = given().log().all()
                .headers("Content-Type","application/json")
                .body(payload.DeleteBookWithId(Id))
                .when()
                .post("/Library/Deletebook.php")
                .then()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js_del = BaseLibrary.rawFromJSONResponse(responseDelete);
        String msg = js_del.getString("msg");
        System.out.println(msg);
        System.out.println("<--- ##### ---> ");
    }


    @Test(priority = 2,dataProvider = "manyBooks")
    public void AddBooksFromDataProvider(String title, String isbn, String aisle, String author){
        RestAssured.baseURI="http://216.10.245.166";

        String response = given().log().all()
                .headers("Content-Type","application/json")
                .body(payload.AddBook(title,isbn,aisle,author))
                .when()
                .post("/Library/Addbook.php")
                .then()
                .assertThat().statusCode(200)
                .extract().response().asString();
        js = BaseLibrary.rawFromJSONResponse(response);

        Id = js.getString("ID");
        System.out.println("Book Id is ---> "+Id);

    }

@DataProvider(name="manyBooks")
    public Object [][] getDataForAllBooks(){
        // ARRAY --> it's a collections of elements
        // MULTIDIMENSIONAL ARRAY --> it's a collection of arrays
        return new Object [][]{
            {"Learn RobotFramework","1","aa","D.M."},
            {"Playwright","2","bb","T.M."},
            {"Selenium WebDriver","3","cc","J.J."},
            {"Java Course","4","dd","E.E."}
        };
    }

}
