package de.chkal.jeti.jaxrs;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class TimingFeature implements Feature {

  @Override
  public boolean configure(FeatureContext context) {
    context.register(TimingReaderInterceptor.class);
    context.register(TimingWriterInterceptor.class);
    context.register(TimingContainerRequestFilter.class);
    context.register(TimingContainerResponseFilter.class);
    return true;
  }

}
