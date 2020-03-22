package com.mbronshteyn.quarkus.controller;

import com.google.common.flogger.FluentLogger;
import com.mbronshteyn.quarkus.entity.Fruit;
import com.mbronshteyn.quarkus.service.FruitService;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Setter
@ApplicationScoped
@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

    @Inject
    FruitService service;

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    @GET
    public List<Fruit> list() throws Exception {
        logger.atInfo().log("inside GET method");
        return service.list();
    }

    // TODO: add exception handler
    @GET
    @Path("/{id}")
    public Fruit findById(@PathParam("id") String uuid) throws Exception {
        logger.atInfo().log("inside GET ONE method");
        return service.findById(uuid);
    }

    @POST
    public List<Fruit> add(Fruit fruit) throws Exception {
        // allow client to pass uuid
        if (fruit.getUuid() == null) {
            fruit.setUuid(UUID.randomUUID().toString());
        }
        return service.add(fruit);
    }

    @DELETE
    public List<Fruit> delete(Fruit fruit) throws Exception {
        return service.delete(fruit);
    }
}
