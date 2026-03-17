import POJO4oauthTest.Api;
import POJO4oauthTest.GetCourses;
import POJO4oauthTest.WebAutomation;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@Test
public class oAuthTest {


    public void AuthorizationServer()  {
        //RestAssured.baseURI="https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token";
        //info below are retrieve from the swagger
        RestAssured.useRelaxedHTTPSValidation();
        String response = given()
                .formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
                .asString();
            System.out.println(response);
            JsonPath js = BaseLibrary.rawFromJSONResponse(response);
            String token = js.getString("access_token");

            // now use the Token to GET the list of all courses
        String responseCourses = given()
                    .queryParam("access_token",token)
                    .when().log().all()
                    .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
        System.out.println(responseCourses);

    }


    public void GetCoursesWithPojo(){

            RestAssured.useRelaxedHTTPSValidation();
            String response = given()
                    .formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                    .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                    .formParam("grant_type","client_credentials")
                    .formParam("scope","trust")
                    .when().log().all()
                    .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
                    .asString();
            System.out.println(response);
            JsonPath js = BaseLibrary.rawFromJSONResponse(response);
            String token = js.getString("access_token");

            // now use the Token to GET the list of all courses
            GetCourses allCoursesInfo = given()
                    .queryParam("access_token",token)
                    .when().log().all()
                    .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourses.class);

            //  Instead of using JSON Parse, easiest way is with pojos
            System.out.println("---- with POJO-------");
            System.out.println(allCoursesInfo.getLinkedIn());
            System.out.println(allCoursesInfo.getExpertise());
            System.out.println("With fixed index: "+allCoursesInfo.getCourses().getApi().get(1).getCourseTitle());

            //get the price of the API course: 'Rest Assured Automation using Java' without hardcoding the index (JSON file can be always updated)
            List<Api> APIs =  allCoursesInfo.getCourses().getApi();
            for(int i=0; i<APIs.size();i++){
                if(APIs.get(i).getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java")){
                    System.out.println("Price is: "+APIs.get(i).getPrice());
                }

            }

            // get all courses titles of webAutomation array and compare with expected courses titles
            // Let's create array of expected strings titles or list of expected strings titles, what you prefer
            //String[] expectedArrayTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
            List<String> expectedTitles = List.of("Selenium Webdriver Java", "Cypress", "Protractor");
            List<WebAutomation> webAuto =  allCoursesInfo.getCourses().getWebAutomation();
            System.out.println("Courses under WebAutomation are listed below:");
            ArrayList<String> actualTitleInJSON = new ArrayList<>();
            for(int i=0; i<webAuto.size();i++) {
                actualTitleInJSON.add(webAuto.get(i).getCourseTitle());
                System.out.println(webAuto.get(i).getCourseTitle());

            }
            System.out.println(actualTitleInJSON);
            System.out.println(expectedTitles);
            Assert.assertEquals(expectedTitles, actualTitleInJSON);
            //if using Array of Strings remember to convert Array into List and then compare the two Lists
            // List<String> expTitles = Arrays.asList(expectedArrayTitles);
            // Assert.assertEquals(expTitles, actualTitleInJSON);
    }


    // OAuth2.0
    //Grant Type called 'Authorization Code"
    //indepth steps of OAuth "Authorization Code" Grant type
    // example ---> https://in.bookmyshow.com/ when sign-in, use Google account for security (this is OAuth2.0)
    // let's follow bottom to top approach:
    public void GetCoursesOAuth2(){

            //step3
            String resp = given().queryParam("access token","")
                    .when()
                    .get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(resp);


    }



}
