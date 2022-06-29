import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMethods;
public class StoreStaticJsonFiles {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
  ///given (query param,header,body)
		//when(resource and request type)
		//Then(validate response)
		RestAssured.baseURI="https://rahulshettyacademy.com";
		//Add Place 
		String response=given().log().all().
		queryParam("key", "qaclick123").
		headers("Content-Type", "application/json").
		body(new String(Files.readAllBytes(Paths.get("C:\\Users\\AparnaL\\Downloads\\RestAssuredAPITesting\\AddPlace.json")))).
		when().post("maps/api/place/add/json").
		then().statusCode(200).body("scope",equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();	
		System.out.println(response);
		//Parse Json to parse in json documents
		JsonPath js1=ReUsableMethods.rawToJson(response);
		String placeId=js1.getString("place_id");
		System.out.println(placeId);
		String newAddress="Google Summer Africa";
		//Update place
		given().log().all().
		queryParam("key", "qaclick123").
		header("Content-Type","application/json").
		body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").
		when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		//Add Place-(Put Place with new Address - Get Place to validate whether address got updated or not
		//Get Place
		String getResponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println(getResponse);
		JsonPath js2=ReUsableMethods.rawToJson(getResponse);
		String actualaddress=js2.getString("address");
		System.out.println(actualaddress);
		Assert.assertEquals(actualaddress, newAddress);
	}

}
