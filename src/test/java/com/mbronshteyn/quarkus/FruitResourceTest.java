package com.mbronshteyn.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
public class FruitResourceTest {

    @Test
    public void testList() {
        given()
                .when().get("/fruits")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));
    }

    // TODO: figure out how to do POST with actual object instead of hardcoding strings
//    @Test
//    public void testAdd() {
//        given()
//                .body("{\"name\": \"Pear\", \"description\": \"Winter fruit\"}")
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .when()
//                .post("/fruits")
//                .then()
//                .statusCode(200)
//                .body("$.size()", is(3),
//                        "name", containsInAnyOrder("Apple", "Pineapple", "Pear"),
//                        "description", containsInAnyOrder("Winter fruit", "Tropical fruit", "Winter fruit"));
//
//    }
}

