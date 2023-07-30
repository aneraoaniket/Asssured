package sample;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;

public class Basic {

	// given - all input details
	// when - Submit the API - means we are giving here http methods, resource
	// Then - validate the response

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.41 (Ubuntu)").extract().response()
				.asString();
		System.out.println(response);
		// json path is a class which take input as string and convert it into Json
		// In jsonpath there is lot of method exposed by jsonpath which helps to parse
		// json
		JsonPath js = new JsonPath(response); // parsing Json
		String placeid = js.getString("place_id");
		System.out.println(placeid);
		String id = js.getString("id");
		System.out.println(id);
     
		//Update place
		String newAddress = "kokne";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\""+placeid +"\",\r\n" + "\"address\":\""+newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("maps/api/place/update/json")
				.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
		//get place
		
	String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
	
	JsonPath js1 = new JsonPath(getPlaceResponse); // parsing Json
	String actualAddress = js1.getString("address");
	System.out.println(actualAddress);
	
	Assert.assertEquals(actualAddress, newAddress);
	//Junit framework, Testngframework
	}
}
