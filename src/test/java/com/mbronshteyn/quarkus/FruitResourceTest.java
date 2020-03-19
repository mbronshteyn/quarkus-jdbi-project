package com.mbronshteyn.quarkus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbronshteyn.quarkus.controller.FruitResource;
import com.mbronshteyn.quarkus.entity.Fruit;
import com.mbronshteyn.quarkus.service.FruitService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@QuarkusTest
public class FruitResourceTest {

    @Inject
    FruitResource resource;

    ObjectMapper objectMapper = new ObjectMapper();

    // create mock service
    FruitService fruitServiceMock = Mockito.mock(FruitService.class);

    Set<Fruit> fruitList = new HashSet<>();

    @BeforeEach
    public void setup() {
        // init data
        fruitList.add(new Fruit("Apple", "Summer fruit"));
        fruitList.add(new Fruit("Orange", "Winter fruit"));
        fruitList.add(new Fruit("Pineapple", "Fall fruit"));

        // inject mocks
        resource.setService(fruitServiceMock);
    }

    @Test
    public void testList() {

        Mockito.when(fruitServiceMock.list()).thenReturn(fruitList);

        given()
                .when().get("/fruits")
                .then()
                .statusCode(200)
                .body("$.size()", is(fruitList.size()));

        verify(fruitServiceMock, times(1)).list();
    }

    @Test
    public void testAdd() throws Exception {

        Fruit pear = new Fruit("Pear", "Winter fruit");

        // we don't need to pass actual Fruit, just an object
        Mockito.when(fruitServiceMock.add(pear))
                .thenReturn(fruitList);

        given()
                .body(objectMapper.writeValueAsString(pear))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/fruits")
                .then()
                .statusCode(200)
                .body("$.size()", is(fruitList.size()));

        verify(fruitServiceMock, times(1)).add(pear);
    }
}

