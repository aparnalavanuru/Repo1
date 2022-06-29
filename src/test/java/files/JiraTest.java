package files;
import static io.restassured.RestAssured.*;

import java.io.File;

import javax.xml.stream.events.Comment;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
public class JiraTest {
  public static void main(String args[]) {
   //Login API Session
	  RestAssured.baseURI="http://localhost:8080";
	  SessionFilter session=new SessionFilter();
	  String response= given().log().all().header("Content-Type","application/json").body("{\r\n"
	  		+ "    \"username\": \"aparna\",\r\n"
	  		+ "    \"password\": \"Apu@1998\"\r\n"
	  		+ "}").filter(session).post("/rest/auth/1/session")
			  .then().log().all().extract().response().asString();
	  System.out.println(response);
	  JsonPath js=ReUsableMethods.rawToJson(response);
	  String Commentid=js.get("id");
	  System.out.println(Commentid);
	  //AddComment
	 String commentContent="I am adding first Comment in Jira Project.";
     given().log().all().pathParam("key", "10002").
	  header("Content-Type","application/json")
	  .body("{\r\n"
	  		+ "    \"body\": \"I am adding first Comment in Jira Project.\",\r\n"
	  		+ "    \"visibility\": {\r\n"
	  		+ "        \"type\": \"role\",\r\n"
	  		+ "        \"value\": \"Administrators\"\r\n"
	  		+ "    }\r\n"
	  		+ "}")
	 .filter(session) .when().post("/rest/api/2/issue/{key}/comment")
	  .then().log().all().assertThat().statusCode(201);
	  //Add attachment
	  given().log().all().pathParam("key", "10002").
	  header("X-Atlassian-Token","no-check")
	  .header("Content-Type","multipart/form-data")
	  .filter(session)
	  .multiPart("file",new File("JiraAttachmentFile.txt"))
	  .when().post("/rest/api/2/issue/{key}/attachments")
	  .then().log().all().assertThat().statusCode(200);
	  
	  //Get Issue
	  String IssueDetails=given().log().all().filter(session).pathParam("key", "10002")
			  .queryParam("fields", "comment")
	  .when().get("/rest/api/2/issue/{key}")
	  .then().log().all().extract().response()
	  .asString();
	  System.out.println(IssueDetails);
	  JsonPath js1=new JsonPath(IssueDetails);
	  int count=js1.getInt("fields.comment.comments[0].size()");
	  System.out.println(count);
	
	  for(int i=0;i<count;i++) {
		 
		  
		  String id= js1.get("fields.comment.comments["+i+"].id").toString();		  
		  if(id.equalsIgnoreCase(Commentid)) {
			 String comment= js1.get("fields.comment.comments["+i+"].body").toString();
			 System.out.println(comment);
			 Assert.assertEquals(comment, commentContent);
		  }
	  }
	
  }
}
