import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;

public class AuthIntegrationTest {

    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:8083";
    }    

    @Test
    public void shouldReturnOKWithToken()
    {
        TokenExtract tokenExtract = new TokenExtract();
        String token = tokenExtract.extractToken(200);
        System.out.println("Token shouldReturnOKWithToken: " + token);
    }

    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin()
    {
        String loginPayload = """
            {
                "email":"invalid_user@test.com",
                "password":"wrongPassword"
            }
        """;

        given()
            .contentType("application/json")
            .body(loginPayload)
            .when()
            .post("/auth/login")
            .then()
            .statusCode(401);
    }
}
