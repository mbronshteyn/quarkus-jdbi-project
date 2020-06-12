package com.mbronshteyn.quarkus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.flogger.FluentLogger;
import com.mbronshteyn.quarkus.bl.DatabaseConnector;
import com.mbronshteyn.quarkus.dao.FruitDao;
import com.mbronshteyn.quarkus.entity.Fruit;
import com.mbronshteyn.quarkus.restclient.SlackService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jdbi.v3.core.Jdbi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import java.util.List;

@ApplicationScoped
public class FruitService {

    ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    DatabaseConnector databaseConnector;

    @Inject
    @RestClient
    SlackService slackService;

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public List<Fruit> list() throws Exception {
        return databaseConnector.getJdbi().withExtension(FruitDao.class,
                FruitDao::findAll);
    }

    public Fruit findById(String uuid) throws Exception {
        return databaseConnector.getJdbi().withExtension(FruitDao.class, dao -> {
            return dao.findById(uuid);
        });

    }

    public Integer add(Fruit fruit) throws Exception {

        logger.atInfo().log("Add Fruit: %s", fruit);

        Jdbi jdbi = databaseConnector.getJdbi();

        String jsonFruit = Json.createObjectBuilder()
                .add("text", fruit.toString())
                .build()
                .toString();

        // post message to slack
        slackService.postMessage(jsonFruit);

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

        Jdbi jdbi = databaseConnector.getJdbi();
        return jdbi.withExtension(FruitDao.class, dao -> {
            return dao.update(fruit.getUuid(), fruit.getName(), fruit.getDescription());
        });
    }

    public Integer delete(String uuid) throws Exception {

        logger.atInfo().log("Delete Fruit by UUID: " + uuid);

        Jdbi jdbi = databaseConnector.getJdbi();
        return jdbi.withExtension(FruitDao.class, dao -> {
            return dao.deleteById(uuid);
        });
    }
}
