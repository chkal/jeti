package de.chkal.jeti.jaxrs;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

public class TimingContainerRequestFilter extends TimingProviderBase implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext containerRequestContext) throws IOException {

    getMetricsProvider().notifyRequestFilter();

  }

}
