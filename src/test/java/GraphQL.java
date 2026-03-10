import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;


import javax.xml.transform.Source;

import static io.restassured.RestAssured.*;


@Test
public class GraphQL {

    // Test the GraphQL Query
    public void QueryGraphQL(){
        RestAssured.useRelaxedHTTPSValidation();
        int characterId = 21595;  //value can be retrieve from excel file or dataprovider
        int episId = 19633;       //value can be retrieve from excel file or dataprovider
        String resp = given().log().all().header("Content-Type","application/json")
                //payload to pass as body gotten from the graphQL server at https://rahulshettyacademy.com/gq/graphql
                //inspect the page and under NETWORK there is the payload tab there is
                // the query and variables to pass. Click on VIEW PARSED, Copy it and past it
                // inside the Body
                .body("{\"query\":\"query($charId: Int!, $episodeId: Int!){\\n  character(characterId: $charId) {\\n    name\\n    gender\\n    status\\n    id\\n  }\\n  location(locationId: 8) {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: \\\"Rahul\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters: {episode: \\\"hulu\\\"}) {\\n    result {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\\n\",\"variables\":{\"charId\":"+characterId+",\"episodeId\":"+episId+"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println(resp);
        JsonPath js = new JsonPath(resp);
        String characterName = js.getString("data.character.name");
        Assert.assertEquals(characterName,"Dan");
    }




}
