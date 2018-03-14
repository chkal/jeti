package de.chkal.jeti.engine.servlet4;

import de.chkal.jeti.core.ServerTimingHeader;
import de.chkal.jeti.core.TimingMetric;
import de.chkal.jeti.core.TimingRegistry;
import de.chkal.jeti.core.servlet.IntegrationStrategy;
import de.chkal.jeti.core.servlet.TimingRegistryHolder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

public class Servlet4IntegrationStrategy implements IntegrationStrategy {

  @Override
  public HttpServletResponse apply(HttpServletResponse response, TimingRegistry registry) {

    response.addHeader("Trailer", ServerTimingHeader.HEADER_NAME);

    response.setTrailerFields(() -> {

      List<TimingMetric> metrics = registry.getProviders().stream()
          .flatMap(provider -> provider.getMetrics().stream())
          .collect(Collectors.toList());

      LinkedHashMap<String, String> trailerFields = new LinkedHashMap<>();

      if (!metrics.isEmpty()) {
        trailerFields.put(
            ServerTimingHeader.HEADER_NAME,
            ServerTimingHeader.serializeMetrics(metrics)
        );
      }

      return trailerFields;

    });

    return response;

  }

}
