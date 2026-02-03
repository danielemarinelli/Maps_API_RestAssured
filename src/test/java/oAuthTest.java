import POJO.GetCourses;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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


    }


}
