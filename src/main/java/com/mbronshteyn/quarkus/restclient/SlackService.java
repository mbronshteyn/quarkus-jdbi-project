package com.mbronshteyn.quarkus.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.inject.Singleton;
import javax.ws.rs.POST;


@Singleton
@RegisterRestClient
public interface SlackService {
  @POST
  void postMessage(String text);
}
