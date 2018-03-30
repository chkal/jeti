package de.chkal.jeti.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServerTimingHeader {

  public static final String HEADER_NAME = "Server-Timing";

  private ServerTimingHeader() {
    // utility class
  }

  public static String serializeMetrics(List<Metric> metrics) {
    return metrics.stream()
        .map(m -> serializeMetric(m))
        .collect(Collectors.joining(", "));
  }

  public static String serializeMetric(Metric m) {

    List<String> components = new ArrayList<>();
    components.add(m.getName());
    components.addAll(
        m.getParams().entrySet().stream()
            .map(e -> serializeParam(e.getKey(), e.getValue()))
            .collect(Collectors.toList())
    );

    return components.stream().collect(Collectors.joining(";"));

  }

  public static String serializeParam(String key, Object value) {

    boolean quote = !(value instanceof Number);

    StringBuilder builder = new StringBuilder();
    builder.append(key);
    builder.append("=");
    if (quote) {
      builder.append("\"");
    }
    builder.append(Objects.toString(value));
    if (quote) {
      builder.append("\"");
    }
    return builder.toString();

  }

}
