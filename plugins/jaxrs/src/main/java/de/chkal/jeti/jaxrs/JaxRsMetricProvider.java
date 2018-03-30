package de.chkal.jeti.jaxrs;

import de.chkal.jeti.core.DefaultPerformanceMetric;
import de.chkal.jeti.core.Metric;
import de.chkal.jeti.core.MetricProvider;
import java.util.Arrays;
import java.util.List;

public class JaxRsMetricProvider implements MetricProvider {

  private long requestFilter;
  private long responseFilter;

  private long beforeReadFrom;
  private long afterReadFrom;

  private long afterWriteTo;
  private long beforeWriteTo;

  public void notifyRequestFilter() {
    this.requestFilter = System.currentTimeMillis();
  }

  public void notifyResponseFilter() {
    this.responseFilter = System.currentTimeMillis();
  }

  public void notifyBeforeReadFrom() {
    this.beforeReadFrom = System.currentTimeMillis();
  }

  public void notifyAfterReadFrom() {
    this.afterReadFrom = System.currentTimeMillis();
  }

  public void notifyBeforeWriteTo() {
    this.beforeWriteTo = System.currentTimeMillis();
  }

  public void notifyAfterWriteTo() {
    this.afterWriteTo = System.currentTimeMillis();
  }

  @Override
  public List<Metric> getMetrics() {

    long readerTime = beforeReadFrom > 0 && afterReadFrom > 0
        ? afterReadFrom - beforeReadFrom
        : 0;

    long writerTime = beforeWriteTo > 0 && afterWriteTo > 0
        ? afterWriteTo - beforeWriteTo
        : 0;

    long filterTime = requestFilter > 0 && responseFilter > 0
        ? responseFilter - requestFilter
        : 0;

    // the ReaderInterceptor runs AFTER ContainerRequestFilter
    long resourceTime = filterTime - readerTime;

    return Arrays.asList(
        new DefaultPerformanceMetric("jaxrs-1-reader", "MessageBodyReader", readerTime),
        new DefaultPerformanceMetric("jaxrs-2-resource", "ResourceMethod", resourceTime),
        new DefaultPerformanceMetric("jaxrs-3-writer", "MessageBodyWriter", writerTime)
    );

  }
}
