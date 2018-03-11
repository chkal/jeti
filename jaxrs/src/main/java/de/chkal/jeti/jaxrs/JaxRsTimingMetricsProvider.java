package de.chkal.jeti.jaxrs;

import de.chkal.jeti.core.PerformanceTimingMetric;
import de.chkal.jeti.core.TimingMetric;
import de.chkal.jeti.core.TimingMetricsProvider;
import java.util.Collections;
import java.util.List;

public class JaxRsTimingMetricsProvider implements TimingMetricsProvider {

  private long beginTime = 0;
  private long endTime = 0;

  public void notifyBegin() {
    this.beginTime = System.currentTimeMillis();
  }

  public void notifyEnd() {
    this.endTime = System.currentTimeMillis();
  }

  @Override
  public List<TimingMetric> getMetrics() {

    if (beginTime > 0 && endTime > 0 && endTime >= beginTime) {
      return Collections.singletonList(new PerformanceTimingMetric() {

        @Override
        public String getName() {
          return "jaxrs-1-resource-method";
        }

        @Override
        public String getDescription() {
          return "JAX-RS resource";
        }

        @Override
        public Number getDuration() {
          return endTime - beginTime;
        }
      });

    }

    return Collections.emptyList();

  }

}
