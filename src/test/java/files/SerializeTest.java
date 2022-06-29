package files;
import  static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;
public class SerializeTest {
    public static void main(String args[])
    {
    	AddPlace ap=new AddPlace();
    	ap.setAccuracy(50);
    	ap.setAddress("29, side layout, cohen 09");
    	ap.setLanguage("French-IN");   	
    	ap.setLocation(null);
    	ap.setName("Frontline house");
    	ap.setPhone_number("(+91) 983 893 3937");
    	List<String> li=new ArrayList<String>();
    	li.add("shoe park");
    	li.add("shop");
    	ap.setTypes(li);
    	Location l=new Location();
    	l.setLat(-38.383494);
    	l.setLng(33.427362);
    	ap.setLocation(l);
    	ap.setWebsites("https://rahulshettyacademy.com");
    	RestAssured.baseURI="https://rahulshettyacademy.com";
    	Response res=given().log().all().queryParam("key", "qaclick123")
    	.body(ap)
    	.when().post("/maps/api/place/add/json")
    	.then().extract().response();
    	String response=res.asString();
    	System.out.println(response);
    }
}
