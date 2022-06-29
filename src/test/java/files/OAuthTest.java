package files;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;
public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
            String[] arrayCourses={"Selenium Webdriver Java","Cypress","Protractor"};
//		    WebDriverManager.firefoxdriver().setup();
//			WebDriver driver= new FirefoxDriver();
//			driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
//			driver.findElement(By.cssSelector("input[type='email']")).sendKeys("fdfd@gmail.com");
//			driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//			Thread.sleep(3000);
//			driver.findElement(By.cssSelector("input[type='password']")).sendKeys("fxfe");
//			driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//			Thread.sleep(4000);
//			String url=driver.getCurrentUrl();
            String url="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AX4XfWhLtdR-9wKLzpjapTlcN0QXTDRAKDeNL-Zm7_TExR2w9Smgu_srfpxOW6DVHnU4Fg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
			String partialcode=url.split("code=")[1];
			String code=partialcode.split("&scope")[0];
			System.out.println(code);
			
			
			//   tagname[attribute='value']
			
	String accessTokenResponse=	given().urlEncodingEnabled(false)
		.queryParams("code",code)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
	JsonPath js=new JsonPath(accessTokenResponse);
	String accessToken=js.getString("access_token");
		
		
		
		
		
		GetCourse gc=given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		System.out.println(gc.getLinkedin());
		System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
        List<Api>  api= gc.getCourses().getApi();
		for(int i=0;i<api.size();i++)
		{
			if(api.get(i).getCourseTitle().equalsIgnoreCase("SOAPUI Webservices Testing")) {
				System.out.println(api.get(i).getPrice());
			}
		}
		
		//COmpare data in arrays with adding fetched data into arraylist and compare array and arraylist
		ArrayList<String> al=new ArrayList<String>();		
		List<WebAutomation>  wa= gc.getCourses().getWebAutomation();
		for(int i=0;i<wa.size();i++)
		{
				al.add(wa.get(i).getCourseTitle());
		}
		List<String> expectedList=Arrays.asList(arrayCourses);
		
		Assert.assertTrue(al.equals(expectedList));
	}

}
