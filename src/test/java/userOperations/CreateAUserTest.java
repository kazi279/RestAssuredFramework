package userOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;

public class CreateAUserTest {

    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static final int ID = 2000;
    public static final String USER_NAME = "karim123";
    public static final String FIRST_NAME = "Mohammad";
    public static final String LAST_NAME = "Karim";
    public static final String EMAIL = "test@gmail.com";
    public static final String PASSWORD = "123@3";
    public static final String PHONE = "0168566523";
    public static final int USER_STATUS = 1;

    public static final String UPDATED_EMAIL = "mohammadkarim@gmail.com";
    public static final String UPDATED_PASSWORD = "ffhs123@3#";

    @Test
    public void createAnUser() {

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("id", ID);
        payload.put("username", USER_NAME);
        payload.put("firstName", FIRST_NAME);
        payload.put("lastName", LAST_NAME);
        payload.put("email", EMAIL);
        payload.put("password", PASSWORD);
        payload.put("phone", PHONE);
        payload.put("userStatus", USER_STATUS);

        JSONObject request = new JSONObject(payload);
        System.out.println(request);

        Response response = given().
                body(request.toJSONString()).
                contentType("application/json").
                when().
                post(BASE_URL + "/user").
                then().
                assertThat().
                statusCode(200).log().all().extract().response();

//		{
//		    "code": 200,
//		    "type": "unknown",
//		    "message": "2000"
//		}

        ValidatableResponse validatableResponse = response.then();

        // Are there 3 keys such as "code", "type" and "message"
        validatableResponse.body("$", hasKey("code"));
        validatableResponse.body("$", hasKey("type"));
        validatableResponse.body("$", hasKey("message"));

        // Are there 3 values for 3 keys
        validatableResponse.body("code", is(notNullValue()));
        validatableResponse.body("type", is(notNullValue()));
        validatableResponse.body("message", is(notNullValue()));

        JsonPath jsonPathEvaluator = response.jsonPath();

        // Are the values for the particular keys are matching or valid?
        // assertEquals(jsonPathEvaluator.get("code"), Integer.toString(200));
        assertEquals(jsonPathEvaluator.get("type"), "unknown");
        assertEquals(jsonPathEvaluator.get("message"), Integer.toString(ID));
    }

}
