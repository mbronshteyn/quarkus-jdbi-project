package com.mbronshteyn.quarkus.controller;

import com.google.common.flogger.FluentLogger;
import com.mbronshteyn.quarkus.entity.Fruit;
import com.mbronshteyn.quarkus.service.FruitService;
import com.mbronshteyn.quarkus.util.ResponseObject;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    private final FluentLogger logger = FluentLogger.forEnclosingClass();

    @GET
    public Response list() {
        logger.atInfo().log("inside GET method");
        try {
            List<Fruit> fruits = service.list();
            ResponseObject responseObject = ResponseObject.builder()
                    .msg("Success")
                    .fruitList(fruits)
                    .build();
            return Response.ok().entity(responseObject).build();
        } catch (Exception e) {
            return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // TODO: add exception handler
    @GET
    @Path("/{uuid}")
    public Response findById(@PathParam("uuid") String uuid) throws Exception {
        logger.atInfo().log("inside GET ONE method");
        Fruit fruit = service.findById(uuid);
        if (fruit != null) {
            ResponseObject responseObject = ResponseObject.builder()
                    .fruit(fruit)
                    .msg("Success")
                    .build();
            return Response.ok().type(MediaType.APPLICATION_JSON).entity(responseObject).build();
        } else {
            ResponseObject responseObject = ResponseObject.builder()
                    .msg("Not Found")
                    .build();
            return Response.ok().status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(responseObject).build();
        }
    }

    @POST
    public Response add(Fruit fruit) throws Exception {
        try {
            // allow client to pass uuid
            if (fruit.getUuid() == null) {
                fruit.setUuid(UUID.randomUUID().toString());
            }

            int result = service.add(fruit);

            ResponseObject responseObject;
            Response.Status status;
            if (result == 1) {
                responseObject = ResponseObject.builder()
                        .msg("Success")
                        .build();
                status = Response.Status.OK;
            } else {
                responseObject = ResponseObject.builder()
                        .msg("Not Inserted")
                        .build();
                status = Response.Status.NOT_MODIFIED;
            }

            return Response.ok().status(status).entity(responseObject).build();
        } catch (Exception e) {
            return Response.serverError()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    public Response update(Fruit fruit) throws Exception {
        int result = service.update(fruit);

        ResponseObject responseObject = ResponseObject.builder()
                .msg(result == 1 ? "Success" : "Not updated")
                .build();

        return Response.ok()
                .entity(responseObject)
                .build();
    }

    @DELETE
    @Path("/{uuid}")
    public Response delete(@PathParam("uuid") String uuid) throws Exception {

        int result = service.delete(uuid);
        ResponseObject responseObject = ResponseObject.builder()
                .msg(result == 1 ? "Success" : "Not deleted")
                .build();

        return Response.ok().entity(responseObject)
                .build();
    }
}
