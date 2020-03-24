package com.mbronshteyn.quarkus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbronshteyn.quarkus.controller.FruitResource;
import com.mbronshteyn.quarkus.entity.Fruit;
import com.mbronshteyn.quarkus.service.FruitService;
import com.mbronshteyn.quarkus.util.ResponseObject;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class FruitResourceTest {

    @Inject
    @InjectMocks
    FruitResource resource;

    // create mock service
    @Mock
    FruitService fruitServiceMock;

    ObjectMapper objectMapper = new ObjectMapper();

    List<Fruit> fruitList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        // init data
        fruitList.add(Fruit.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Apple")
                .description("Summer fruit")
                .build());

        fruitList.add(Fruit.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Orange")
                .description("Winter fruit")
                .build());

        fruitList.add(Fruit.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Pineapple")
                .description("Fall fruit")
                .build());
    }

    @Test
    public void testList() throws Exception {

        // Test success
        ResponseObject responseObject = ResponseObject.builder()
                .msg(FruitResource.SUCCESS)
                .fruitList(fruitList)
                .build();

        Response response = Response.ok()
                .entity(responseObject)
                .build();

        Mockito.when(fruitServiceMock.list()).thenReturn(fruitList);

        given()
                .when().get("/fruits")
                .then()
                .statusCode(200)
                .body("fruitList.size()", is(3))
                .body("msg", is("Success"));

        verify(fruitServiceMock, times(1)).list();

        // Test Exception
        Mockito.when(fruitServiceMock.list()).thenThrow(new Exception());
        given()
                .when().get("/fruits")
                .then()
                .statusCode(500);
    }

//    @Test
//    public void testAdd() throws Exception {
//
//        Fruit pear = new Fruit("test", "Pear", "Winter fruit");
//
//        ResponseObject responseObject = responseObject = ResponseObject.builder()
//                .msg( "Success" )
//                .build();
//
//        Response response = Response.ok().status( Response.Status.OK ).entity( responseObject ).build();
//
//        // we don't need to pass actual Fruit, just an object
//        Mockito.when(fruitServiceMock.add(any(Fruit.class)))
//                .thenReturn( 1 );
//
//        given()
//                .body(objectMapper.writeValueAsString(pear))
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .when()
//                .post("/fruits")
//                .then()
//                .statusCode(200)
//                .body("$.size()", is(fruitList.size()));
//
//        verify(fruitServiceMock, times(1)).add(pear);
//    }
}

