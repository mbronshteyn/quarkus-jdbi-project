package com.mbronshteyn.quarkus.util;

import com.mbronshteyn.quarkus.entity.Fruit;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class ResponseObject {
    String msg;
    Fruit fruit;
    List<Fruit> fruitList;
}
