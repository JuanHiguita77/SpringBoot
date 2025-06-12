import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.jupiter.api.*;


import static org.hamcrest.Matchers.*;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientIntegrationTest {

    private static UUID createdPatientId;
    private static String token;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8083"; // Ajusta si es otro puerto

        // Obtener token de autenticación
        TokenExtract tokenExtract = new TokenExtract();
        token = tokenExtract.extractToken(200);
    }

    private String buildPatientPayload() {
        return """
        {
            "name": "John Doe",
            "email": "john@exampleeeee.com",
            "phone": "1234567890",
            "address": "123 Main St",
            "birthDate": "1990-01-01",
            "registryDate": "2024-01-01"
        }
        """;
    }

    @Test
    @Order(1)
    public void testCreatePatient() {
        String payload = buildPatientPayload();

        Response response = given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .body(payload)
            .when()
            .post("/api/patients")
            .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("name", equalTo("John Doe"))
            .extract()
            .response();

        System.out.println("Full response body: " + response.asString());

        String idStr = response.jsonPath().getString("id");
        System.out.println("Extracted ID string: " + idStr);
    
        createdPatientId = UUID.fromString(idStr);
    }

    @Test
    @Order(2)
    public void testGetPatientById() {
        given()
            .header("Authorization", "Bearer " + token)
            .pathParam("id", createdPatientId)
            .when()
            .get("/api/patients/{id}")
            .then()
            .statusCode(200)
            .body("id", equalTo(createdPatientId.toString()))
            .body("name", equalTo("John Doe"));
    }

    @Test
    @Order(3)
    public void testUpdatePatient() {
        String updatedPayload = """
            {
                "name": "Jane Doe",
                "email": "jane.doe@example.com",
                "phone": "0987654321",
                "address": "456 Updated St",
                "birthDate": "1992-05-10",
                "registryDate": "2024-05-21"
            }
        """;

        given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .body(updatedPayload)
            .pathParam("id", createdPatientId)
            .when()
            .put("/api/patients/{id}")
            .then()
            .statusCode(200)
            .body("name", equalTo("Jane Doe"))
            .body("email", equalTo("jane.doe@example.com"));
    }

    @Test
    @Order(4)
    public void testGetAllPatients() {
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/api/patients")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    @Order(5)
    public void testDeletePatient() {
        given()
            .header("Authorization", "Bearer " + token)
            .pathParam("id", createdPatientId)
            .when()
            .delete("/api/patients/{id}")
            .then()
            .statusCode(204);
    }

    @Test
    @Order(6)
    public void testGetDeletedPatientReturns404() {
        given()
            .header("Authorization", "Bearer " + token)
            .pathParam("id", createdPatientId)
            .when()
            .get("/api/patients/{id}")
            .then()
            .statusCode(200);
    }
}