package com.mbronshteyn.helpers.podam;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class PodamSandbox {
  public static void main(String[] args) throws Exception {

    ObjectMapper mapper = new ObjectMapper();

    // Simplest scenario. Will delegate to Podam all decisions
    PodamFactory factory = new PodamFactoryImpl();

    // This will use constructor with minimum arguments and
    // then setters to populate POJO
    Pojo myPojo = factory.manufacturePojo(Pojo.class);

    System.out.println(myPojo);

    String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(myPojo);

    System.out.println(jsonString);
  }
}
