package com.mbronshteyn.quarkus.service;

import com.google.common.flogger.FluentLogger;
import com.mbronshteyn.quarkus.bl.DatabaseConnector;
import com.mbronshteyn.quarkus.dao.FruitDao;
import com.mbronshteyn.quarkus.entity.Fruit;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FruitService {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private DatabaseConnector databaseConnector = new DatabaseConnector();

    // TODO: refactor to use DB shortly
    private List<Fruit> fruits = new ArrayList<>();

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

    public List<Fruit> list() throws Exception {
        logger.atInfo().log("Fruits: " + fruits);
        return databaseConnector.getConnection().withExtension(FruitDao.class,
                FruitDao::findAll);
    }

    public Fruit findById(String uuid) {
        return new Fruit(UUID.randomUUID().toString(), "Apple", "Winter fruit");
    }

    public List<Fruit> add(Fruit fruit) throws Exception {

        logger.atInfo().log("Add Fruit: " + fruit);

        Jdbi jdbi = databaseConnector.getConnection();
        List<Fruit> fruits = jdbi.withExtension(FruitDao.class, dao -> {
            int result = dao.add(fruit.getUuid(), fruit.getName(), fruit.getDescription());
            // TODO: not sure if this the behavior we want to keep going forward
            return dao.findAll();
        });

        jdbi.useHandle(Handle::close);

        return fruits;
    }

    public List<Fruit> delete(Fruit fruit) {
        fruits.removeIf(existingFruit -> existingFruit.name.contentEquals(fruit.name));
        return fruits;
    }
}
