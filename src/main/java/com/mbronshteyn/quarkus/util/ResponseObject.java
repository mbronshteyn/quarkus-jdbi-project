package com.mbronshteyn.quarkus.util;

import com.mbronshteyn.quarkus.entity.Fruit;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@RegisterForReflection
public class ResponseObject {
    String msg;
    Fruit fruit;
    List<Fruit> fruitList;
}
