package com.mbronshteyn.quarkus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbronshteyn.quarkus.controller.FruitResource;
import com.mbronshteyn.quarkus.entity.Fruit;
import com.mbronshteyn.quarkus.service.FruitService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
// TODO: refactor later to use Response object
//    @Test
//    public void testList() throws Exception {
//
//        Mockito.when(fruitServiceMock.list()).thenReturn(fruitList);
//
//        given()
//                .when().get("/fruits")
//                .then()
//                .statusCode(200)
//                .body("$.size()", is(fruitList.size()));
//
//        verify(fruitServiceMock, times(1)).list();
//    }

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

