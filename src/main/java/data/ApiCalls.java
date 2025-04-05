package data;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiCalls {


    public static Response getAllFilms() {
        return RestAssured
                .given()
                .baseUri("https://swapi.dev")
                .basePath("/api/films")
                .when()
                .get();
    }
    public static Response getPeople() {
        return RestAssured
                .given()
                .baseUri("https://swapi.dev")
                .basePath("/api/people")
                .when()
                .get();
    }

}
