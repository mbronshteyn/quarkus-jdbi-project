/*-
 * #%L
 * Project Name: Boreas
 * %%
 * Copyright (C) 2019 - 2020 LogRhythm, Inc. All rights reserved.
 * %%
 * Protected by copyright and licenses restricting use, copying, distribution and decompilation.
 *
 * THE SOFTWARE IS PROTECTED BY UNITED STATES COPYRIGHT LAW AND INTERNATIONAL TREATIES.
 * UNAUTHORIZED REPRODUCTION OR DISTRIBUTION IS SUBJECT TO CIVIL AND CRIMINAL PENALTIES.
 * #L%
 */
package com.mbronshteyn.quarkus.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.StringWriter;
import java.util.Map;

@ApplicationScoped
public class TemplateEngine {

  @Inject
  VelocityEngine velocityEngine;

  public String map( Map<String,String> templateProperties ) throws Exception {

    VelocityContext velocityContext = new VelocityContext();

    Template t = velocityEngine.getTemplate("mail.vm");
    templateProperties.forEach( ( key, value ) -> {
      velocityContext.put( key, value );
    } );


    StringWriter sw = new StringWriter();
    t.merge(velocityContext, sw);

    return sw.toString();
  }
}
