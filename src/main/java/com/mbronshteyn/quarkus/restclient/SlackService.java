package com.mbronshteyn.quarkus.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Singleton
@Path(("/services"))
@RegisterRestClient
public interface SlackService {
  @Path("/T014U3P9M4P/B0160MMCUPJ/Im19VNFCDetzrtomQSsuCUvV")
  @POST
  void postMessage(String text);
}
