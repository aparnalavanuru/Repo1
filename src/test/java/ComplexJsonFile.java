import org.testng.Assert;
import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    JsonPath js=new JsonPath(payload.complexJson());
    int count= js.getInt("courses.size()");
    System.out.println(count);
    String purAmou=js.get("dashboard.purchaseAmount").toString();
    System.out.println(purAmou);
    String FirstCourseTit=js.get("courses[0].title");
    System.out.println(FirstCourseTit);
    for(int i=0;i<count;i++) {
    	System.out.println(js.get("courses["+i+"].price").toString()); 
    	System.out.println(js.get("courses["+i+"].title").toString());  
    }
	for (int i = 0; i < count; i++) {
		String title=js.get("courses[" + i + "].title");
		if (title.equalsIgnoreCase("RPA")) {
			System.out.println(js.get("courses[" + i + "].copies").toString());
			break;
		}
	}
	int sum=0;
	int totAmount=js.getInt("dashboard.purchaseAmount");
	for (int i = 0; i < count; i++) {
		int price=js.getInt("courses[" + i + "].price");
		int copies=js.getInt("courses[" + i + "].copies");
		sum=sum + (price*copies);
	  }
	Assert.assertEquals(sum, totAmount);
	}

}
