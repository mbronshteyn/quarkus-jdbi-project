package com.mbronshteyn.quarkus.service;

import com.google.common.flogger.FluentLogger;
import com.mbronshteyn.quarkus.bl.DatabaseConnector;
import com.mbronshteyn.quarkus.dao.FruitDao;
import com.mbronshteyn.quarkus.entity.Fruit;
import org.jdbi.v3.core.Jdbi;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FruitService {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public List<Fruit> list() throws Exception {
        return DatabaseConnector.getJdbi().withExtension(FruitDao.class,
                FruitDao::findAll);
    }

    public Fruit findById(String uuid) throws Exception {
        return DatabaseConnector.getJdbi().withExtension(FruitDao.class, dao -> {
            return dao.findById(uuid);
        });
    }

    public List<Fruit> add(Fruit fruit) throws Exception {

        logger.atInfo().log("Add Fruit: " + fruit);

        Jdbi jdbi = DatabaseConnector.getJdbi();

        /**
         * A convenience method which opens an extension of the given type,
         * yields it to a callback, and returns the result of the callback.
         * A handle is opened if needed by the
         * extension, and closed before returning to the caller.
         */
        return jdbi.withExtension(FruitDao.class, dao -> {
            int result = dao.add(fruit.getUuid(), fruit.getName(), fruit.getDescription());
            // TODO: refactor to return an error object
            logger.atInfo().log("Add result: " + result);
            // TODO: return updated list for now
            return dao.findAll();
        });
    }

    public List<Fruit> update(Fruit fruit) throws Exception {

        logger.atInfo().log("Update Fruit: " + fruit);

        Jdbi jdbi = DatabaseConnector.getJdbi();
        return jdbi.withExtension(FruitDao.class, dao -> {
            int result = dao.update(fruit.getUuid(), fruit.getName(), fruit.getDescription());
            // TODO: refactor to return an error object
            logger.atInfo().log("Update result: " + result);
            // TODO: return updated list for now
            return dao.findAll();
        });
    }

    public List<Fruit> delete(String uuid) throws Exception {

        logger.atInfo().log("Delete Fruit by UUID: " + uuid);

        Jdbi jdbi = DatabaseConnector.getJdbi();
        return jdbi.withExtension(FruitDao.class, dao -> {
            int result = dao.deleteById(uuid);
            logger.atInfo().log("Delete by id result: " + result);
            if (result != 1) {
                // TODO: refactor to return an error object
                logger.atSevere().log("Delete by UUID: Not found by uuid: " + uuid);
            }
            // TODO: return the updated list for now
            return dao.findAll();
        });
    }
}
