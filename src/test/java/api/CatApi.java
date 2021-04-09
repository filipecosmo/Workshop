package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class CatApi {
    String idVotacao;

    @BeforeClass
    public static void setup() {
        baseURI = "https://api.thecatapi.com/v1/";
        enableLoggingOfRequestAndResponseIfValidationFails();
        requestSpecification = new RequestSpecBuilder()
                .addHeader("x-api-key", "01800854-dd22-4fc1-bb12-510fd7dd3db9")
                .setContentType(ContentType.JSON)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON).build();
    }


    @Test
    public void consultVote() {
        given()
                .when()
                .get("votes")
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    public void specificVote() {
        given()
                .pathParam("vote_id", "282026")
                .when()
                .get("votes/{vote_id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }


    @Test
    public void createVote() {
        Response response = given()
                .body("{\"image_id\":\"MTc3NTMyNg\",\"value\":true,\"sub_id\":\"demo-a42368\"}")
                .when()
                .post("votes");
        response.then()
                .statusCode(HttpStatus.SC_OK)
                .body("message", Matchers.is("SUCCESS"))
        ;
        idVotacao = response.jsonPath().getString("id");
        System.out.println("ID: " + idVotacao);

    }

    @Test
    public void deleteVote() {
        createVote();
        given()
                .pathParam("vote_id", idVotacao)
                .when()
                .delete("https://api.thecatapi.com/v1/votes/{vote_id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("message", Matchers.is("SUCCESS"))
                .log().all()
        ;
    }

}
