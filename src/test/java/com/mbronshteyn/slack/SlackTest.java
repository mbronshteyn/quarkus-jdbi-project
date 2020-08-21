package com.mbronshteyn.slack;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.mbronshteyn.quarkus.restclient.SlackService;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.Json;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

@QuarkusTest
public class SlackTest {

  private WireMockServer wireMockServer;

  @Inject
  @RestClient
  SlackService slackService;

  @Test
  public void testSlack(){

    wireMockServer = new WireMockServer();

    wireMockServer.start();

    String jsonMessage = Json.createObjectBuilder().add("text", "test").build().toString();

    String str = "/services/T014U3P9M4P/B0160MMCUPJ/Im19VNFCDetzrtomQSsuCUvV";

    stubFor( post(
            urlMatching( str ))
            .withRequestBody( containing(jsonMessage) )
            .withHeader("Content-Type", containing("application/json")));

    slackService.postMessage( jsonMessage );

    verify(postRequestedFor(urlMatching( str ))
            .withHeader("Content-Type", containing("application/json")));
  }
}
