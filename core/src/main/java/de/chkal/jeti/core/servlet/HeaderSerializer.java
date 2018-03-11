package de.chkal.jeti.core.servlet;

import de.chkal.jeti.core.TimingMetric;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class HeaderSerializer {

  protected static String serializeMetrics(List<TimingMetric> metrics) {
    return metrics.stream()
        .map(m -> serializeMetric(m))
        .collect(Collectors.joining(", "));
  }

  private static String serializeMetric(TimingMetric m) {

    List<String> components = new ArrayList<>();
    components.add(m.getName());
    components.addAll(
        m.getParams().entrySet().stream()
            .map(e -> serializeParam(e.getKey(), e.getValue()))
            .collect(Collectors.toList())
    );

    return components.stream().collect(Collectors.joining(";"));

  }

  private static String serializeParam(String key, Object value) {

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
