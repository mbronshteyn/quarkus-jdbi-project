package com.mbronshteyn.quarkus.controller;

import com.google.common.flogger.FluentLogger;
import com.mbronshteyn.quarkus.entity.QueueItem;
import com.mbronshteyn.quarkus.service.TestQueueService;
import com.mbronshteyn.quarkus.util.QueueItemValueGenerator;
import com.mbronshteyn.quarkus.util.QueueResponseObject;
import com.mbronshteyn.quarkus.util.ResponseObject;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@ApplicationScoped
@Path("/queue")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QueueResource {
    public static final String SUCCESS = "Success";
    public static final String NOT_UPDATED = "Not updated";
    public static final String NOT_FOUND = "Not Found";
    public static final String NOT_INSERTED = "Not Inserted";
    public static final String NOT_DELETED = "Not deleted";

    @Inject
    TestQueueService service;

    @Inject
    QueueItemValueGenerator generator;

    private final FluentLogger logger = FluentLogger.forEnclosingClass();

    @GET
    public Response list() {
        try {
            List<QueueItem> items = service.list();
            QueueResponseObject responseObject = QueueResponseObject.builder()
                    .msg(SUCCESS)
                    .queueItemList(items)
                    .build();
            return Response.ok().entity(responseObject).build();
        } catch (Exception e) {
            logger.atSevere().log(e.getMessage());
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/count")
    public Response count() {
        int count = service.count();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("count", count);
        return Response.ok().entity(responseBody).build();
    }

    @POST
    @Path("/consumers")
    public Response start(@QueryParam("count") int consumerCount,
                          @DefaultValue("0") @QueryParam("minDelay") int minDelay,
                          @DefaultValue("100") @QueryParam("maxDelay") int maxDelay) {
        service.startProcessors(consumerCount, minDelay, maxDelay);
        return Response.ok().build();
    }

    @DELETE
    @Path("/consumers")
    public Response stop() {
        service.stopProcessors();
        return Response.ok().build();
    }

    @POST
    public Response pushToQueue(@DefaultValue("1") @QueryParam("count") int itemCount) {
        List<QueueItem> items = generator.nextAsStream(itemCount)
                .map(value -> new QueueItem(UUID.randomUUID().toString(), Instant.now(), value))
                .collect(Collectors.toList());
        if (items.size() == 1) {
            service.add(items.get(0));
        } else {
            service.add(items);
        }
        return Response.ok().build();
    }



}
