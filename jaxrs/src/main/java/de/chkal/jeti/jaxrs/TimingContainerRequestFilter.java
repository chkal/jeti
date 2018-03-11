package de.chkal.jeti.jaxrs;

import de.chkal.jeti.core.servlet.TimingRegistryHolder;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class TimingContainerRequestFilter implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext containerRequestContext) throws IOException {

    JaxRsTimingMetricsProvider provider = new JaxRsTimingMetricsProvider();

    provider.notifyBegin();

    TimingRegistryHolder.get().register(provider);


  }

}
