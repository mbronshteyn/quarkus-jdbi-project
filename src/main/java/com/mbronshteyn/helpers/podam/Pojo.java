package com.mbronshteyn.helpers.podam;

import lombok.Data;
import lombok.ToString;

import java.net.URL;

@Data
@ToString
public class Pojo {
  private String randomString;
  private String postCode;
  private URL url;
}

