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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@QuarkusTest
public class FruitResourceTest {

    @Inject
    FruitResource resource;

    ObjectMapper objectMapper = new ObjectMapper();

    // create mock service
    FruitService fruitServiceMock = Mockito.mock(FruitService.class);

    List<Fruit> fruitList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        // init data
        fruitList.add(new Fruit(UUID.randomUUID().toString(), "Apple", "Summer fruit"));
        fruitList.add(new Fruit(UUID.randomUUID().toString(), "Orange", "Winter fruit"));
        fruitList.add(new Fruit(UUID.randomUUID().toString(), "Pineapple", "Fall fruit"));

        // inject mocks
        resource.setService(fruitServiceMock);
    }

    @Test
    public void testList() throws Exception {

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

        Fruit pear = new Fruit("test", "Pear", "Winter fruit");

        // we don't need to pass actual Fruit, just an object
        Mockito.when(fruitServiceMock.add(any(Fruit.class)))
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

