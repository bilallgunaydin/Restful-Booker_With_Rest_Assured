package test;

import baseURLRepository.HerokuappBaseURL;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static pojos.BookingDataBuilder.getBookingData;

public class API_Put_Request extends HerokuappBaseURL {
    private BookingData updateBook;

    @Test
    public void updateBook() {
        updateBook = getBookingData();
        Response response = given()
                .spec(specHerokuapp)
                .contentType(ContentType.JSON).get("/booking/1");
        response.prettyPrint();

        given()
                .spec(specHerokuapp)
                .contentType(ContentType.JSON)
                .accept("application/json")
                .auth().preemptive().basic("admin", "password123")
                .body(updateBook)
                .when()
                .put("/booking/1")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("firstname", equalTo(updateBook.getFirstname()))
                .body("lastname", equalTo(updateBook.getLastname()))
                .body("totalprice", equalTo(updateBook.getTotalprice()))
                .body("depositpaid", equalTo(updateBook.isDepositpaid()))
                .body("bookingdates.checkin", equalTo(updateBook.getBookingdates()
                        .getCheckin()))
                .body("bookingdates.checkout", equalTo(updateBook.getBookingdates()
                        .getCheckout()))
                .body("additionalneeds", equalTo(updateBook.getAdditionalneeds()))
                .log().body();
    }
}
