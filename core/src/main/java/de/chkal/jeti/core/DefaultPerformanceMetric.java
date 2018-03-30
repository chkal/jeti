package de.chkal.jeti.core;

public class DefaultPerformanceMetric extends PerformanceMetric {

  private final String name;
  private final String description;
  private final Number duration;

  public DefaultPerformanceMetric(String name, String description, Number duration) {
    this.name = name;
    this.description = description;
    this.duration = duration;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public Number getDuration() {
    return duration;
  }

}
