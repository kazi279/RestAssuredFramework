package userOperations;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;

public class GetUserByUserNameTest {

    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static final String USER_NAME = "karim123";
    public static final String FIRST_NAME = "Mohammad";
    public static final String LAST_NAME = "Karim";
    public static final String EMAIL = "test@gmail.com";
    public static final String PASSWORD = "123@3";
    public static final String PHONE = "0168566523";

    @Test
    public void getUserByUsername() {

        Response response = given().
                pathParam("username", USER_NAME).
                when().
                get(BASE_URL + "/user/{username}").
                then().
                assertThat().
                statusCode(200).
                log().all().
                extract().response();

//		{
//		    "id": 2000,
//		    "username": "karim123",
//		    "firstName": "Mohammad",
//		    "lastName": "Karim",
//		    "email": "test@gmail.com",
//		    "password": "123@3",
//		    "phone": "0168566523",
//		    "userStatus": 1
//		}

        ValidatableResponse validatableResponse = response.then();

        // Are there 8 keys such as "id", "username", "firstName", "lastName", "email", "password", "phone" and "userStatus"
        validatableResponse.body("$", hasKey("id"));
        validatableResponse.body("$", hasKey("username"));
        validatableResponse.body("$", hasKey("firstName"));
        validatableResponse.body("$", hasKey("lastName"));
        validatableResponse.body("$", hasKey("email"));
        validatableResponse.body("$", hasKey("password"));
        validatableResponse.body("$", hasKey("phone"));
        validatableResponse.body("$", hasKey("userStatus"));

        // Are there 8 values for 8 keys
        validatableResponse.body("id", is(notNullValue()));
        validatableResponse.body("username", is(notNullValue()));
        validatableResponse.body("firstName", is(notNullValue()));
        validatableResponse.body("lastName", is(notNullValue()));
        validatableResponse.body("email", is(notNullValue()));
        validatableResponse.body("password", is(notNullValue()));
        validatableResponse.body("phone", is(notNullValue()));
        validatableResponse.body("userStatus", is(notNullValue()));

        JsonPath jsonPathEvaluator = response.jsonPath();

        // Are the values for the particular keys are matching or valid?
        // assertEquals(jsonPathEvaluator.get("id"), ID);
        assertEquals(jsonPathEvaluator.get("username"), USER_NAME);
        assertEquals(jsonPathEvaluator.get("firstName"), FIRST_NAME);
        assertEquals(jsonPathEvaluator.get("lastName"), LAST_NAME);
        assertEquals(jsonPathEvaluator.get("email"), EMAIL);
        assertEquals(jsonPathEvaluator.get("password"), PASSWORD);
        assertEquals(jsonPathEvaluator.get("phone"), PHONE);
        // assertEquals(jsonPathEvaluator.get("userStatus"), USER_STATUS);
    }
}
