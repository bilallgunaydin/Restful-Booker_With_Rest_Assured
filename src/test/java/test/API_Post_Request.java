package test;

import baseURLRepository.HerokuappBaseURL;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.*;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static pojos.BookingDataBuilder.getBookingData;

public class API_Post_Request extends HerokuappBaseURL {
    private BookingData newBook;

    @Test
    public void createBook() {
        newBook = getBookingData();
        specHerokuapp.pathParam("pp1", "booking");

        Response response = given()
                .contentType(ContentType.JSON).
                spec(specHerokuapp).
                body(newBook).
                when().
                post("{pp1}");
        response.prettyPrint();

        Bookingid responseBody = response.as(Bookingid.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(newBook.getFirstname(), responseBody.getBooking().getFirstname());
        assertEquals(newBook.getLastname(), responseBody.getBooking().getLastname());
        assertEquals(newBook.getTotalprice(), responseBody.getBooking().getTotalprice());
        assertEquals(newBook.isDepositpaid(), responseBody.getBooking().isDepositpaid());
        assertEquals(newBook.getBookingdates().getCheckin(), responseBody.getBooking().getBookingdates().getCheckin());
        assertEquals(newBook.getBookingdates().getCheckout(), responseBody.getBooking().getBookingdates().getCheckout());
    }
}
