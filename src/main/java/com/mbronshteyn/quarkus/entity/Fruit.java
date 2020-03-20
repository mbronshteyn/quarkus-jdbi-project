package com.mbronshteyn.quarkus.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fruit {
    public String uuid;
    public String name;
    public String description;
}
