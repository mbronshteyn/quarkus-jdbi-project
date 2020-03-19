package com.mbronshteyn.quarkus;

import com.mbronshteyn.quarkus.controller.FruitResource;
import com.mbronshteyn.quarkus.entity.Fruit;
import com.mbronshteyn.quarkus.service.FruitService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
public class FruitResourceTest {

    @Inject
    FruitResource resource;

    FruitService fruitService = Mockito.mock(FruitService.class);

    @BeforeEach
    public void setup() {
        resource.setService(fruitService);
    }

    @Test
    public void testList() {

        Set<Fruit> fruitList = new HashSet<>();
        fruitList.add(new Fruit("Apple", "Summer fruit"));
        fruitList.add(new Fruit("Orange", "Winter fruit"));
        fruitList.add(new Fruit("Pineapple", "Fall fruit"));

        Mockito.when(fruitService.list()).thenReturn(fruitList);

        given()
                .when().get("/fruits")
                .then()
                .statusCode(200)
                .body("$.size()", is(fruitList.size()));
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

