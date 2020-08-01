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

import com.mbronshteyn.qute.QuteTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@QuarkusTest
class TemplateEngineTest {

  @Inject
  TemplateEngine templateEngine;

  @Inject
  QuteTest cuteTest;

  @Test
  void map() throws Exception {
    Map<String,String> templateProperties = new HashMap<>();
    templateProperties.put( "firstName", "Jane" );
    templateProperties.put( "lastName", "Doe" );
    templateProperties.put( "location", "San Diego" );

    String result = templateEngine.map( templateProperties );
    System.out.println(result);


    cuteTest.applyTemplate();


  }
}
