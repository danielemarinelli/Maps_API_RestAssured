import io.restassured.path.json.JsonPath;
import files.payload;

/*

{
"dashboard": {
        "purchaseAmount": 910,
        "website": "rahulshettyacademy.com"
        },
"courses": [
        {
        "title": "Selenium Python",
        "price": 50,
        "copies": 6
        },
        {
        "title": "Cypress",
        "price": 40,
        "copies": 4
        },
        {
        "title": "RPA",
        "price": 45,
        "copies": 10
        }
        ]
}

        TC1. Print Number of courses returned by API

        TC2.Print Purchase Amount

        TC3. Print Title of the first course

        TC4. Print All course titles and their respective Prices

        TC5. Print no of copies sold by RPA Course

        TC6. Verify if Sum of all Course prices matches with Purchase Amount

*/


public class ComplexJSON_Parse {

    public static void main(String[] args) {

        JsonPath js = BaseLibrary.rawFromJSONResponse(payload.practiceParseJSON());
        //navigate to website --> https://jsonpathfinder.com/  to find out the json path
        //TC1.
        int numberOfCourses = js.getInt("courses.size()");    //courses is an array [] , so method size() can be used
        System.out.println("Number of courses returned by API is: " +numberOfCourses);
        //TC2.
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Total Purchase Amount is: " +totalAmount);
        //TC3.
        String titleSecondCourse = js.getString("courses[1].title");
        System.out.println("The second course title is: " +titleSecondCourse);


    }

}


