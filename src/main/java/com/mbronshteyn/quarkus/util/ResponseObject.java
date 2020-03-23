package com.mbronshteyn.quarkus.util;

import com.mbronshteyn.quarkus.entity.Fruit;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseObject {
    String msg;
    Fruit fruit;
    List<Fruit> fruitList;
}
