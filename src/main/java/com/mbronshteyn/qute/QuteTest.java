package com.mbronshteyn.qute;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class QuteTest {

  @Inject
  Engine engine;

  public void applyTemplate(){

    Map<String,String> templateProperties = new HashMap<>();

    templateProperties.put( "firstName", "Jane");
    templateProperties.put( "lastName", "Doe");
    templateProperties.put( "location", "USA" );

    String templateString =
        "Dear {firstName} {lastName}\n"
            + "\n"
            + "Sending Email Using Notification service Smtp Email + Velocity Template from {location} location.\n"
            + "\n"
            + "Thanks";

    System.out.println( "Qute template test: \n");
    Template template = engine.parse( templateString );
    String str = template.data( templateProperties ).render();
    System.out.println( str );


    System.out.println( str );
  }
}
