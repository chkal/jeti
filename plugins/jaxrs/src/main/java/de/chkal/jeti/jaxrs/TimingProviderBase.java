package de.chkal.jeti.jaxrs;

import de.chkal.jeti.core.TimingRegistry;
import de.chkal.jeti.core.servlet.TimingRegistryHolder;

abstract class TimingProviderBase {

  protected JaxRsTimingMetricsProvider getMetricsProvider() {

    TimingRegistry registry = TimingRegistryHolder.get();

    return registry.getProviderByType(JaxRsTimingMetricsProvider.class)
        .orElseGet(() -> {
          JaxRsTimingMetricsProvider provider = new JaxRsTimingMetricsProvider();
          registry.register(provider);
          return provider;
        });

  }

}
