package de.chkal.jeti.jaxrs;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

public class TimingWriterInterceptor extends TimingProviderBase implements WriterInterceptor {

  @Override
  public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {

    JaxRsMetricProvider provider = getMetricsProvider();

    provider.notifyBeforeWriteTo();
    try {

      context.proceed();

    } finally {
      provider.notifyAfterWriteTo();
    }

  }

}
