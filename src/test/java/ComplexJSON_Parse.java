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

        //TC4.
        for(int i=0; i<js.getInt("courses.size()"); i++){
            System.out.println(js.getString("courses["+i+"].title") + " ---> " +js.getString("courses["+i+"].price"));
        }

        //TC5.
        String course = "RPA";
        for(int i=0; i<js.getInt("courses.size()"); i++){
            if(js.getString("courses["+i+"].title").equalsIgnoreCase(course)){
                System.out.println("Number of copies sold by RPA Course: "+js.getString("courses["+i+"].copies"));
                break;
            }
        }

        //TC6.
        int partialPrize;
        int totalPrize = 0;
        for(int i=0; i<js.getInt("courses.size()"); i++){

            int p = js.getInt("courses["+i+"].price");
            int c = js.getInt("courses["+i+"].copies");
            partialPrize = p*c;
            totalPrize = totalPrize + partialPrize;

        }
        //System.out.println("Total price for all courses: "+totalPrize);
        if(js.getInt("dashboard.purchaseAmount")==totalPrize){
            System.out.println("Total purchase amount is equal to: "+totalPrize);
        }else{
            System.out.println("Total purchase amount is NOT equal to: "+totalPrize);
        }


    }

}


