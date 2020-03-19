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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

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
    public Set<Fruit> list() {
        logger.atInfo().log("inside GET method");
        return service.list();
    }

    @GET
    @Path( "/one" )
    public Fruit one(){
        logger.atInfo().log("inside GET ONE method");
        return new Fruit("Apple", "Winter fruit");
    }

    @POST
    public Set<Fruit> add(Fruit fruit) {
        return service.add(fruit);
    }

    @DELETE
    public Set<Fruit> delete(Fruit fruit) {
        return service.delete(fruit);
    }
}
