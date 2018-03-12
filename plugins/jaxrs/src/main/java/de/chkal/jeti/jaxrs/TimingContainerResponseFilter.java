package de.chkal.jeti.jaxrs;

import de.chkal.jeti.core.servlet.TimingRegistryHolder;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class TimingContainerResponseFilter implements ContainerResponseFilter {

  @Override
  public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext)
      throws IOException {

    TimingRegistryHolder.get()
        .getProviderByType(JaxRsTimingMetricsProvider.class)
        .ifPresent(provider -> provider.notifyEnd());

  }

}
