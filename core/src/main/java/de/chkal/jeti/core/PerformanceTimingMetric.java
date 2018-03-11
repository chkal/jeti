package de.chkal.jeti.core;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class PerformanceTimingMetric implements TimingMetric {

  public abstract String getDescription();

  public abstract Number getDuration();

  @Override
  public Map<String, Object> getParams() {
    LinkedHashMap<String, Object> params = new LinkedHashMap<>();
    params.put("desc", getDescription());
    params.put("dur", getDuration());
    return params;
  }

}
