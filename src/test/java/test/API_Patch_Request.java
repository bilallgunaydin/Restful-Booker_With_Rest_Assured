package test;

import baseURLRepository.HerokuappBaseURL;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.BookingData;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static pojos.BookingDataBuilder.getBookingData;

public class API_Patch_Request extends HerokuappBaseURL {
    private BookingData updatedBooking;

    @Test
    public void updateBookWithPatch() {
        updatedBooking = getBookingData();
        Response response = given()
                .spec(specHerokuapp)
                .contentType(ContentType.JSON).get("/booking/1");
        response.prettyPrint();

        given().spec(specHerokuapp)
                .contentType(ContentType.JSON)
                .accept("application/json")
                .auth().preemptive().basic("admin", "password123")
                .body(updatedBooking)
                .when()
                .patch("/booking/1")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("firstname", equalTo(updatedBooking.getFirstname()))
                .body("lastname", equalTo(updatedBooking.getLastname()))
                .log().body();
    }
}
