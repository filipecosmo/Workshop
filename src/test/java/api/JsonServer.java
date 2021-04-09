package api;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JsonServer {


    @Test
    public void consultPost(){
        given().when().get("http://localhost:3000/posts").then().log().all();
    }



    @Test
    public void createPost(){
        given().contentType(ContentType.JSON)
                .body("{\"id\": 4,\"title\": \"renato\",\"author\": \"novo\"}")
                .when().post("http://localhost:3000/posts")
                .then().statusCode(HttpStatus.SC_CREATED)
                .log().all();
    }

    @Test
    public void createPostMap(){
        Map<String,Object> newPost = new HashMap<>();
        newPost.put("id","5");
        newPost.put("title","carlos");
        newPost.put("author","novo");

        given().contentType(ContentType.JSON)
                .body(newPost)
                .when().post("http://localhost:3000/posts")
                .then().statusCode(HttpStatus.SC_CREATED)
                .log().all();
    }

    @Test
    public void updatePost(){
        given().contentType(ContentType.JSON)
                .body("{\"id\": 3,\"title\": \"renato\",\"author\": \"typicode\"}")
                .pathParam("id","3")
                .when().put("http://localhost:3000/posts/{id}")
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deletePost(){
        given()
                .pathParam("id","3")
                .when().delete("http://localhost:3000/posts/{id}")
                .then().log().all();
    }

    @Test
    public void consultProfile(){
        given().when().get("http://localhost:3000/profile").then().log().all();
    }

    @Test
    public void createProfile(){
        given().contentType(ContentType.JSON)
                .body("{\"name\": \"novo\"}")
                .when().post("http://localhost:3000/profile")
                .then().statusCode(HttpStatus.SC_CREATED)
                .log().all();
    }

}
