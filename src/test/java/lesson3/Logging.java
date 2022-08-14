package lesson3;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class Logging extends AbstractTest{

    @BeforeAll
    static void setUp(){

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @Test
    void getResponseLogTest(){
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .log().all()
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .prettyPeek();

    }
}
