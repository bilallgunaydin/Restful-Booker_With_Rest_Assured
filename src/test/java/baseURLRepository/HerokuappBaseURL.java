package baseURLRepository;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

public class HerokuappBaseURL {
    protected RequestSpecification specHerokuapp;

    @BeforeMethod
    public void setUp(){

        specHerokuapp = new RequestSpecBuilder().
                setBaseUri("https://restful-booker.herokuapp.com").build();

    }

}
