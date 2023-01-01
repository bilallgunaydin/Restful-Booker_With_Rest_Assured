package test;

import baseURLRepository.HerokuappBaseURL;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class API_Delete_Request extends HerokuappBaseURL {

    @Test
    public void deleteBook() {
        given()
                .spec(specHerokuapp)
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .when()
                .delete("/booking/" + 4)
                .then()
                .assertThat()
                .statusCode(201);
    }
}