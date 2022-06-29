package files;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;
public class OAuthTestAssignment {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
            String[] arrayCourses={"Selenium Webdriver Java","Cypress","Protractor"};
//		    WebDriverManager.chromedriver().setup();
//			WebDriver driver= new ChromeDriver();
//			driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
//			driver.findElement(By.cssSelector("input[type='email']")).sendKeys("fdfd@gmail.com");
//			driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//			Thread.sleep(3000);
//			driver.findElement(By.cssSelector("input[type='password']")).sendKeys("fxfe");
//			driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//			Thread.sleep(4000);
//			String url=driver.getCurrentUrl();
            String url="http://localhost/?state=state_parameter_passthrough_value&code=4%2F0AX4XfWjdPjAWM-n1Yr0hZRUfEEu0p5Eeu9rlyJKBAN2P0Qo1MDEbatoUTlPY3c_e61j12w&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fyoutube+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fyoutube.readonly+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fyoutubepartner+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fyt-analytics-monetary.readonly+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fyt-analytics.readonly";
			String partialcode=url.split("code=")[1];
			String code=partialcode.split("&scope")[0];
			System.out.println(code);

			
	RequestSpecification re=given().urlEncodingEnabled(false)
		.queryParams("code",code)
		.queryParams("client_id","20712959274-un60espv2l509m3lmej2t4anbptin6d4.apps.googleusercontent.com")
		.queryParams("client_secret","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")
		.queryParams("redirect_uri","http://localhost")
		.queryParams("grant_type","authorization_code");
	System.out.println(re);
	  String accessTokenResponse= re.when().log().all()
		.post("https://oauth2.googleapis.com/token").asString();
	JsonPath js=new JsonPath(accessTokenResponse);
	String accessToken=js.getString("access_token");
	System.out.println(accessToken);
	String res=given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when()
		.get("https://youtubeanalytics.googleapis.com/v2/groups").asString();
	System.out.println(res);
	}
}
