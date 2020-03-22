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

    public Fruit findById(String uuid) throws Exception {
        return databaseConnector.getConnection().withExtension(FruitDao.class, dao -> {
            return dao.findById(uuid);
        });
    }

    public List<Fruit> add(Fruit fruit) throws Exception {

        logger.atInfo().log("Add Fruit: " + fruit);

        Jdbi jdbi = databaseConnector.getConnection();
        List<Fruit> fruits = jdbi.withExtension(FruitDao.class, dao -> {
            int result = dao.add(fruit.getUuid(), fruit.getName(), fruit.getDescription());
            // TODO: refactor to return an error object
            logger.atInfo().log("Add result: " + result);
            // TODO: return updated list for now
            return dao.findAll();
        });

        jdbi.useHandle(Handle::close);

        return fruits;
    }

    public List<Fruit> delete(String uuid) throws Exception {

        logger.atInfo().log("Delete Fruit by UUID: " + uuid);

        Jdbi jdbi = databaseConnector.getConnection();
        List<Fruit> fruits = jdbi.withExtension(FruitDao.class, dao -> {
            int result = dao.deleteById(uuid);
            logger.atInfo().log("Delete by id result: " + result);
            if (result != 1) {
                // TODO: refactor to return an error object
                logger.atSevere().log("Delete by UUID: Not found by uuid: " + uuid);
            }
            // TODO: return the updated list for now
            return dao.findAll();
        });

        jdbi.useHandle(Handle::close);

        return fruits;
    }
}
