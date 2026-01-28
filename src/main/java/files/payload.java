package files;


public class payload {
    public static String AddPlace(){
        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Holiday house\",\n" +
                "  \"phone_number\": \"(+39) 389 893 3900\",\n" +
                "  \"address\": \"280, side linepark, Buffalo\",\n" +
                "  \"types\": [\n" +
                "    \"football\",\n" +
                "    \"NFL\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"English-USA\"\n" +
                "}";
    }

    public static String UpdateAddressPlace(String placeId, String newAddress){

        return "{\n" +
                "\"place_id\":\""+placeId+"\",\n" +
                "\"address\":\""+newAddress+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}";

    }

    public static String practiceParseJSON(){

        /*
        TC1. Print No of courses returned by API

        TC2.Print Purchase Amount

        TC3. Print Title of the first course

        TC4. Print All course titles and their respective Prices

        TC5. Print no of copies sold by RPA Course

        TC6. Verify if Sum of all Course prices matches with Purchase Amount
        */

        return "{\"dashboard\": {\n" +
                "\n" +
                "\"purchaseAmount\": 910,\n" +
                "\n" +
                "\"website\": \"rahulshettyacademy.com\"\n" +
                "\n" +
                "},\n" +
                "\n" +
                "\"courses\": [\n" +
                "\n" +
                "{\n" +
                "\n" +
                "\"title\": \"Selenium Python\",\n" +
                "\n" +
                "\"price\": 50,\n" +
                "\n" +
                "\"copies\": 6\n" +
                "\n" +
                "},\n" +
                "\n" +
                "{\n" +
                "\n" +
                "\"title\": \"Cypress\",\n" +
                "\n" +
                "\"price\": 40,\n" +
                "\n" +
                "\"copies\": 4\n" +
                "\n" +
                "},\n" +
                "\n" +
                "{\n" +
                "\n" +
                "\"title\": \"RPA\",\n" +
                "\n" +
                "\"price\": 45,\n" +
                "\n" +
                "\"copies\": 10\n" +
                "\n" +
                "}\n" +
                "\n" +
                "]}";
    }

    public static String AddBook(String title, String isbn, String aisle, String author){

        String payload = "{\r\n" +
                "\r\n" +
                "\"name\":\""+title+"\",\r\n" +
                "\"isbn\":\""+isbn+"\",\r\n" +
                "\"aisle\": \""+aisle+"\",\r\n" +
                "\"author\":\""+author+"\"\r\n" +
                "}\r\n";
        return payload;

    }

    public static String DeleteBookWithId(String id){

        String payload = "{\r\n" +
                "\r\n" +
                "\"ID\" : \""+id+"\"\r\n"+
                "}\r\n";
        return payload;

    }

}
