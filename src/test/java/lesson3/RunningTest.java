package lesson3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

public class RunningTest extends AbstractTest {


// Проверка авторизации, проверка возможностей библиотеки.



    @Test
    void getAuthorisationTest() throws InterruptedException {
        long time = System.currentTimeMillis();
        long respTime;
        given()
                .when()
                .get(getBaseUrl()+"recipes/complexSearch?" +
                        "includeNutrition=false&apiKey=" +getApiKey())
                .then()
                .log().all()
                .time(lessThan(getSla()))
                .statusCode( getResponseCode());

        respTime = System.currentTimeMillis() - time;
        if (respTime > getSla())
        { System.out.println(System.currentTimeMillis() - time); }

        time = System.currentTimeMillis();
        given()
                .when()
                .request(Method.GET,getBaseUrl()+"recipes/complexSearch?" +
                        "includeNutrition={Nutrition}&apiKey={apiKey}", false, getApiKey())
                .then()
                .contentType("application/json")
                // .log().all()
                .statusCode(getResponseCode());

        respTime = System.currentTimeMillis() - time;
        if (respTime > getSla())
        { System.out.println(System.currentTimeMillis() - time); }
        else
        { System.out.println(System.currentTimeMillis() - time); }
    }

    @Test
    void getSpecifyingRequestDataTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .queryParam("query", "burger")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .contentType(getContentType())
                .body("offset", is(0))
                .body("number", is(10))
                .statusCode(200);

// excludeIngredients
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .queryParam("excludeIngredients", "burger")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .contentType(getContentType())
                .body("offset", is(0))
                .body("number", is(10))
                .statusCode(200);

// complexCheck

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .queryParam("excludeIngredients", "burger")
                .queryParam("includeIngredients", "eggs")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .contentType(getContentType())
                .body("offset", is(0))
                .body("number", is(10))
                .statusCode(200);
// Sushi Check

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .queryParam("cuisine", "Japanese")
                .queryParam("query", "sushi")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .contentType(getContentType())
                .body("offset", is(0))
                .body("number", is(10))
                .statusCode(200);

// Post check
// Sushi
        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Japanese Sushi")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(200);


// Complex search

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Mango Fried Rice")
                .formParam("ingredientList", "Mango")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(200);

//  All negative check

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("cuisine", "Chinese")
                .formParam("query", "Mango Fried Rice")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(200);

//  Negative check

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Caun Style Sweet Potato Fries")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(200);

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "123")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(200);


//    given().headers("username", "max")
//            .when()
//            .get(getBaseUrl() + "recipes/716429/information?" +
//                    "includeNutrition=false&apiKey=" + getApiKey())
//            .then()
//            .statusCode(200);
//
//    given()
//            .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
//            .queryParam("apiKey", getApiKey())
//            .body("{\n"
//                    + " \"date\": 1644881179,\n"
//                    + " \"slot\": 1,\n"
//                    + " \"position\": 0,\n"
//                    + " \"type\": \"INGREDIENTS\",\n"
//                    + " \"value\": {\n"
//                    + " \"ingredients\": [\n"
//                    + " {\n"
//                    + " \"name\": \"1 banana\"\n"
//                    + " }\n"
//                    + " ]\n"
//                    + " }\n"
//                    + "}")
//            .when()
//            .post(getBaseUrl() + "mealplanner/geekbrains/items")
//            .then()
//            .statusCode(200);

    }

    @Test
    void getResponseData(){

        Cookie someCookie = new Cookie
                .Builder("name_of_user", "Udjin")
                .setSecured(true)
                .setComment("pivo!")
                .build();


        given().cookie("name_of_user", "Udjin")
                .cookie(someCookie)
                .when()
                .get(getBaseUrl() + "recipes/716429/information?" +
                        "includeNutrition=false&apiKey=" + getApiKey())
                .then()
                .statusCode(200);

        Response response = given()
                .when()
                .get(getBaseUrl()+"recipes/complexSearch?" +
                        "includeNutrition=false&apiKey=" +getApiKey());

        // Get all headers
        Headers allHeaders = response.getHeaders();
        // Get a single header value:
        System.out.println("Content-Encoding: " + response.getHeader("Content-Encoding"));

        // Get all cookies as simple name-value pairs
        Map<String, String> allCookies = response.getCookies();
        // Get a single cookie value:
        System.out.println("CookieName: " + response.getCookie("name_of_user"));

        // Get status line
        System.out.println("StatusLine: " + response.getStatusLine());
        // Get status code
        System.out.println("Code: " + response.getStatusCode());


        String cuisine = given()
                .queryParam("apiKey", getApiKey())
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .path("cuisine");

        System.out.println("cuisine: " + cuisine);

        response = given()
                .queryParam("apiKey", getApiKey())
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then().extract().response();

        String confidence = given()
                .queryParam("apiKey", getApiKey())
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then().extract()
                .jsonPath()
                .get("confidence")
                .toString();

        System.out.println("confidence: " + confidence);

    }

    @Test
    void addMealTest() {

        String id = given()
                .queryParam("hash", "d7844e65a5b902a44549553750c394c4e3b72585")
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"date\": 1644881179,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 avocado\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/mealplanner/udjinjane/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .queryParam("hash", "d7844e65a5b902a44549553750c394c4e3b72585")
                .queryParam("apiKey", getApiKey())
                .when()
                .get("https://api.spoonacular.com/mealplanner/udjinjane/shopping-list")
                .then()
                .statusCode(200);

        given()
                .queryParam("hash", "d7844e65a5b902a44549553750c394c4e3b72585")
                .queryParam("apiKey", getApiKey())
                .delete("https://api.spoonacular.com/mealplanner/udjinjane/items/" + id)
                .then()
                .statusCode(200);
    }

}
