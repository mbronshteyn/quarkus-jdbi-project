package com.mbronshteyn.qute;

import io.quarkus.qute.TemplateLocator;

import java.util.Optional;

public class StringTemplateLocator implements TemplateLocator {

  @Override
  public Optional<TemplateLocation> locate(String s) {
    return Optional.empty();
  }
}
