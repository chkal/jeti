package de.chkal.jeti.jaxrs;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

public class TimingReaderInterceptor extends TimingProviderBase implements ReaderInterceptor {

  @Override
  public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {

    JaxRsTimingMetricsProvider provider = getMetricsProvider();

    provider.notifyBeforeReadFrom();
    try {

      return context.proceed();

    } finally {
      provider.notifyAfterReadFrom();
    }

  }

}

