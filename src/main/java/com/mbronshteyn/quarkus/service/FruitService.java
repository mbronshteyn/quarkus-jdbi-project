package com.mbronshteyn.quarkus.service;

import com.google.common.collect.Sets;
import com.google.common.flogger.FluentLogger;
import com.mbronshteyn.quarkus.entity.Fruit;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class FruitService {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    // TODO: refactor to use DB shortly
    private Set<Fruit> fruits = Sets.newLinkedHashSet();

    public FruitService() {
        Fruit apple = Fruit.builder()
                .name("Apple ")
                .description("Winter Fruit")
                .build();

        Fruit pineapple = Fruit.builder()
                .name("Pineapple")
                .description("Tropical fruit")
                .build();

        fruits.add(apple);
        fruits.add(pineapple);
    }

    public Set<Fruit> list() {
        logger.atInfo().log( "Fruits: " + fruits );
        return fruits;
    }

    public Fruit one(){
        return new Fruit("Apple", "Winter fruit");
    }

    public Set<Fruit> add(Fruit fruit) {
        fruits.add(fruit);
        return fruits;
    }

    public Set<Fruit> delete(Fruit fruit) {
        fruits.removeIf(existingFruit -> existingFruit.name.contentEquals(fruit.name));
        return fruits;
    }
}
