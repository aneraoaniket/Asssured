package sample;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.CoursePrice());
		int count = js.getInt("courses.size()");
		System.out.println(count);
		// purchase amount
		int totalamount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalamount);
		String titlFirstCourse = js.get("courses[0].title"); // get method pull out string
		System.out.println(titlFirstCourse);
		for (int i = 0; i < count; i++) {
			String titleCourse = js.get("courses[" + i + "].title");
			System.out.println(titleCourse);
			int coursePrice = js.get("courses[" + i + "].price");
			System.out.println(coursePrice);
			int courseCopies = js.get("courses[" + i + "].copies");
			System.out.println(courseCopies);
		}
		System.out.println("Print no of copies solde by RPA course");
		for (int j = 0; j < count; j++) {
			String ct = js.get("courses["+j+"].title");
			if (ct.equalsIgnoreCase("RPA")) {
				int copie = js.get("courses["+j+"].copies");
				System.out.println(copie);
				break;
			}
		}
	}
}
