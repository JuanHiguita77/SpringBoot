import io.restassured.response.Response;

import static org.hamcrest.Matchers.notNullValue;
import static io.restassured.RestAssured.given;

public class TokenExtract {

    public String extractToken(int statusCode) {
        
        String loginPayload = """
                    {
                        "email":"testuser@test.com",
                        "password":"password123"
                    }
                """;

        String response = given()
                            .contentType("application/json")
                            .body(loginPayload)
                            .when()
                            .post("/auth/login")
                            .then()
                            .statusCode(statusCode)
                            .extract()
                            .jsonPath()
                            .getString("accessToken");

        System.out.println("Token" + response);
        
        return response;
    }

}
