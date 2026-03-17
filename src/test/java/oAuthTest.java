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

        RestAssured.useRelaxedHTTPSValidation();
            //step1
            // DEV will share the URL where USER must sign in manually with Google account credentials.
        // once the sign in is successfully, there will be a link as below, of the app to test, where
        // contains the access code that we need. The mentioned code is a queryParameter and you can see it in the url
        // its this one from the URL -->  code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M
        String url ="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";

        String partialcode=url.split("code=")[1];

        String code=partialcode.split("&scope")[0];

        System.out.println(code);

            //step2    params given from the DEVELOPER in documentation
            String accessTokenResponse = given().urlEncodingEnabled(false) //keeps all the special characters in the token without converting them
                    .queryParams("code",code)
                    .queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                    .queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                    .queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                    .queryParams("grant_type","authorization_code")
                    .when().log().all()
                    .post("https://www.googleapis.com/oauth2/v4/token").asString();

            JsonPath js = new JsonPath(accessTokenResponse);
            String accessToken = js.getString("access_token");  // pass the accessToken to step3

            //step3
            String resp = given().queryParam("access token",accessToken)
                    .when()
                    .get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(resp);


    }



}
