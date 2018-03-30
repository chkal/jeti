package de.chkal.jeti.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProviderRegistry {

  private final List<MetricProvider> providers = new ArrayList<>();

  public void register(MetricProvider provider) {
    this.providers.add(provider);
  }

  public List<MetricProvider> getProviders() {
    return providers;
  }

  public <T extends MetricProvider> Optional<T> getProviderByType(Class<T> type) {
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
