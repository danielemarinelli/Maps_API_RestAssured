import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;

@Test
public class Jira_api {

    // after creating your own cloud space by login, MANDATORY is TOKEN creation under URL:
    // https://developer.atlassian.com/cloud/jira/platform/basic-auth-for-rest-apis/
    // and link -->  Atlassian Account and need this tool -->  https://www.base64encode.org/

    // SWAGGER to create a bug in jira cloud workspace
    // https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/#api-rest-api-3-issue-post
    public void CreateBugInJiraPlusAttachFile() throws IOException {
        RestAssured.baseURI="https://danimarine.atlassian.net";

        String payload = new String(Files.readAllBytes(Paths.get(".\\src\\main\\resources\\JiraBug_1.json")));
        String bugResponse = given()
                .header("Content-Type","application/json")
                .header("Authorization","Basic ZGFuaWVsZW1hcmluZWxsaTEzODdAZ21haWwuY29tOkFUQVRUM3hGZkdGMHIxZVl5TWFDaXpaeXJHa1VlN0xCODNFT2RQZk96VkJUQzh5RXlRWVRyaEM5WVpjZXFtRTNFU1RBV2pBUktTenZ4WHZRdklfMFVTOFJ5SW1hanpYdHFIdzBYM0tPTnZvUUlZdVdNeHZXUmpuVUo0RW1wRnFzTGtqVGlGUmV2bEZNc1pZTTNvcVZrV0JEMENTVXhFQ2t3eVlPU0pNcE9DRzJSOW5MNnhYcnJOZz0xMkMwMjdBRA==")
                .body(payload)
                .log().all()
                .post("/rest/api/3/issue")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath js = BaseLibrary.rawFromJSONResponse(bugResponse);
            /* POST Response from SWAGGER (or POSTMAN) is below:
            {
            "id": "10001",
            "key": "SCRUM-1",
            "self": "https://danimarine.atlassian.net/rest/api/3/issue/10001"
            }
            */
            String issueId = js.getString("id");
            System.out.println("Bug ID ---> "+issueId);

        // ADD ATTACH ON THE BUG
        // SWAGGER to attach a file into created bug
        // https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-attachments/#api-rest-api-3-issue-issueidorkey-attachments-post

        given()
                .header("Authorization","Basic ZGFuaWVsZW1hcmluZWxsaTEzODdAZ21haWwuY29tOkFUQVRUM3hGZkdGMHIxZVl5TWFDaXpaeXJHa1VlN0xCODNFT2RQZk96VkJUQzh5RXlRWVRyaEM5WVpjZXFtRTNFU1RBV2pBUktTenZ4WHZRdklfMFVTOFJ5SW1hanpYdHFIdzBYM0tPTnZvUUlZdVdNeHZXUmpuVUo0RW1wRnFzTGtqVGlGUmV2bEZNc1pZTTNvcVZrV0JEMENTVXhFQ2t3eVlPU0pNcE9DRzJSOW5MNnhYcnJOZz0xMkMwMjdBRA==")
                .header("X-Atlassian-Token","no-check")
                .pathParam("bugNum",issueId)
                .multiPart("file", new File("/Users/dmarinel/API/JIRA_API/immagine.png"))
                .log().all()
                .post("/rest/api/3/issue/{bugNum}/attachments")
                .then()
                .assertThat().statusCode(200);

        /*
        BODY of response of attach
        [
        {
        "self": "https://danimarine.atlassian.net/rest/api/3/attachment/10000",
        "id": "10000",
        "filename": "immagine.png",
        "author": {
            "self": "https://danimarine.atlassian.net/rest/api/3/user?accountId=60a513f15998a6006876bc33",
            "accountId": "60a513f15998a6006876bc33",
            "emailAddress": "danli1387@gmail.com",
            "avatarUrls": {
                "48x48": "https://secure.gravatar.com/avatar/8d03340af5935db6224fde6ca13cb2f0?d=https%3A%2F%2Favatar-management--avatars.us-west-2.prod.public.atl-paas.net%2Finitials%2FDM-4.png",
                "24x24": "https://secure.gravatar.com/avatar/8d03340af5935db6224fde6ca13cb2f0?d=https%3A%2F%2Favatar-management--avatars.us-west-2.prod.public.atl-paas.net%2Finitials%2FDM-4.png",
                "16x16": "https://secure.gravatar.com/avatar/8d03340af5935db6224fde6ca13cb2f0?d=https%3A%2F%2Favatar-management--avatars.us-west-2.prod.public.atl-paas.net%2Finitials%2FDM-4.png",
                "32x32": "https://secure.gravatar.com/avatar/8d03340af5935db6224fde6ca13cb2f0?d=https%3A%2F%2Favatar-management--avatars.us-west-2.prod.public.atl-paas.net%2Finitials%2FDM-4.png"
            },
            "displayName": "Daniele Marinelli",
            "active": true,
            "timeZone": "Europe/Rome",
            "accountType": "atlassian"
        },
        "created": "2026-01-29T17:07:16.746+0100",
        "size": 365999,
        "mimeType": "image/png",
        "content": "https://danimarine.atlassian.net/rest/api/3/attachment/content/10000",
        "thumbnail": "https://danimarine.atlassian.net/rest/api/3/attachment/thumbnail/10000"
        }
]
        */
/*
        String nameFileAttached =  js.getString("[0].filename");
        String WorldZone =  js.getString("[0].author.timeZone");
        // Assertions
        Assert.assertEquals(nameFileAttached,"immagine.png");
        Assert.assertEquals(WorldZone,"Europe/Rome");
*/
    }



}
