package de.chkal.jeti.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TimingRegistry {

  private final List<TimingMetricsProvider> providers = new ArrayList<>();

  public void register(TimingMetricsProvider provider) {
    this.providers.add(provider);
  }

  public List<TimingMetricsProvider> getProviders() {
    return providers;
  }

  public <T extends TimingMetricsProvider> Optional<T> getProviderByType(Class<T> type) {
    return Optional.ofNullable(
        type.cast(
            providers.stream()
                .filter(p -> p.getClass().equals(type))
                .findFirst()
                .orElse(null)
        )
    );
  }

}
