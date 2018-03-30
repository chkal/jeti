package de.chkal.jeti.jaxrs;

import de.chkal.jeti.core.ProviderRegistry;
import de.chkal.jeti.core.servlet.ProviderRegistryHolder;

abstract class TimingProviderBase {

  protected JaxRsMetricProvider getMetricsProvider() {

    ProviderRegistry registry = ProviderRegistryHolder.get();

    return registry.getProviderByType(JaxRsMetricProvider.class)
        .orElseGet(() -> {
          JaxRsMetricProvider provider = new JaxRsMetricProvider();
          registry.register(provider);
          return provider;
        });

  }

}
