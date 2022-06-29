package files;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class DynamicJson {
     @Test(dataProvider="BooksData")
     public void AddBook(String asid,String isbn) {
			RestAssured.baseURI = "http://216.10.245.166";
			String response = given().log().all().header("Content-Type", "application/json").
					body(payload.dynamicJsonAdd(asid,isbn))
					.when().
					post("Library/Addbook.php").
					then().assertThat().statusCode(200).extract().response().asString();
			System.out.println(response);
			JsonPath js=ReUsableMethods.rawToJson(response);
			String ID=js.get("ID");
			System.out.println(ID);
     
     //Delete Book
			String delInputID=ID;
			String response1 = given().log().all().header("Content-Type", "application/json").
					body(payload.dynamicJsonDel(delInputID))
					.when().delete("/Library/DeleteBook.php").
					then().assertThat().statusCode(200).extract().response().asString();
			System.out.println(response1);
			JsonPath js1=ReUsableMethods.rawToJson(response1);
			String msgResponse=js1.get("msg");
			Assert.assertEquals(msgResponse, "book is successfully deleted");
     }
     
     //array- collection of elemnets
     //multidimensional arrays-collection of arrays
     @DataProvider(name="BooksData")
     public Object[][] getData() {
    	return new Object[][] {{"aide","635"},{"fsd","345"},{"dsf","23"},{"tgh","434"}};
     }
}
