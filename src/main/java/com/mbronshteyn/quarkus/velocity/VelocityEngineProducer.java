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

import org.apache.velocity.app.VelocityEngine;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class VelocityEngineProducer {

  @Produces
  public VelocityEngine velocityEngine() {
    VelocityEngine velocityEngine = new VelocityEngine();
    velocityEngine.setProperty("resource.loader", "class");
    velocityEngine.setProperty(
            "class.resource.loader.class",
            "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    velocityEngine.init();

    return velocityEngine;
  }
}
