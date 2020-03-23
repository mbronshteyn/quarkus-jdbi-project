package com.mbronshteyn.quarkus.service;

import com.google.common.flogger.FluentLogger;
import com.mbronshteyn.quarkus.bl.DatabaseConnector;
import com.mbronshteyn.quarkus.dao.FruitDao;
import com.mbronshteyn.quarkus.entity.Fruit;
import org.jdbi.v3.core.Jdbi;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
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

    public Integer add(Fruit fruit) throws Exception {

        logger.atInfo().log("Add Fruit: " + fruit);
        Response response = null;

        Jdbi jdbi = DatabaseConnector.getJdbi();

        /**
         * A convenience method which opens an extension of the given type,
         * yields it to a callback, and returns the result of the callback.
         * A handle is opened if needed by the
         * extension, and closed before returning to the caller.
         */
        return jdbi.withExtension(FruitDao.class, dao -> {
            return dao.add(fruit.getUuid(), fruit.getName(), fruit.getDescription());
        });
    }

    public Integer update(Fruit fruit) throws Exception {

        logger.atInfo().log("Update Fruit: " + fruit);

        Jdbi jdbi = DatabaseConnector.getJdbi();
        return jdbi.withExtension(FruitDao.class, dao -> {
            return dao.update(fruit.getUuid(), fruit.getName(), fruit.getDescription());
        });
    }

    public Integer delete(String uuid) throws Exception {

        logger.atInfo().log("Delete Fruit by UUID: " + uuid);

        Jdbi jdbi = DatabaseConnector.getJdbi();
        return jdbi.withExtension(FruitDao.class, dao -> {
            return dao.deleteById(uuid);
        });
    }
}
