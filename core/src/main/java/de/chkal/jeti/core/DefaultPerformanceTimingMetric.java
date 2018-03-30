package de.chkal.jeti.core;

public class DefaultPerformanceTimingMetric extends PerformanceTimingMetric {

  private final String name;
  private final String description;
  private final Number duration;

  public DefaultPerformanceTimingMetric(String name, String description, Number duration) {
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
