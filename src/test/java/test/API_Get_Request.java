package test;

import baseURLRepository.HerokuappBaseURL;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import pojos.BookingData;
import java.util.*;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class API_Get_Request extends HerokuappBaseURL {


    @Test
    public void getBook() {

        Response response = given()
                .spec(specHerokuapp)
                .get("/booking/" + 5);

        BookingData responseBody = response.as(BookingData.class);

        given()
                .spec(specHerokuapp)
                .get("/booking/" + 5)
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("firstname", equalTo(responseBody.getFirstname()))
                .body("lastname", equalTo(responseBody.getLastname()))
                .body("totalprice", equalTo(responseBody.getTotalprice()))
                .body("depositpaid", equalTo(responseBody.isDepositpaid()))
                .body("bookingdates.checkin", equalTo(responseBody.getBookingdates()
                        .getCheckin()))
                .body("bookingdates.checkout", equalTo(responseBody.getBookingdates()
                        .getCheckout()))
                .body("additionalneeds", equalTo(responseBody.getAdditionalneeds()));

        response.prettyPrint();
    }


    @Test
    public void getBooks() {
        List<Integer> bookingDataList =
                given()
                        .spec(specHerokuapp)
                        .when()
                        .get("/booking").jsonPath().get("bookingid");

        Collections.sort(bookingDataList);

        for (int item : bookingDataList.stream()
                .filter(c -> c <= 10).collect(Collectors.toList())) {

            given()
                    .spec(specHerokuapp)
                    .get("/booking/" + item)
                    .then()
                    .assertThat()
                    .statusCode(200);

            Response response = given()
                    .spec(specHerokuapp)
                    .get("/booking/" + item);

            BookingData responseBody = response.as(BookingData.class);

            JSONObject bookingdates = new JSONObject();
            bookingdates.put("checkin", responseBody.getBookingdates().getCheckin());
            bookingdates.put("checkout", responseBody.getBookingdates().getCheckout());

            JSONObject jsonBook = new JSONObject();
            jsonBook.put("bookingid", item);
            jsonBook.put("firstname", responseBody.getFirstname());
            jsonBook.put("lastname", responseBody.getLastname());
            jsonBook.put("totalprice", responseBody.getTotalprice());
            jsonBook.put("depositpaid", responseBody.isDepositpaid());
            jsonBook.put("additionalneeds", responseBody.getAdditionalneeds());
            jsonBook.put("bookingdates", bookingdates);

            String jsonBookToString = jsonBook.toString();
            JsonPath jsonBookPath = JsonPath.from(jsonBookToString);
            jsonBookPath.prettyPrint();
        }
    }
}
