package de.chkal.jeti.core.servlet;

import de.chkal.jeti.core.ProviderRegistry;
import java.util.Objects;

public class ProviderRegistryHolder {

  private static final ThreadLocal<ProviderRegistry> threadLocale = new ThreadLocal<>();

  protected static void bind(ProviderRegistry registry) {
    threadLocale.set(registry);
  }

  protected static void release() {
    threadLocale.set(null);
  }

  public static ProviderRegistry get() {
    return Objects.requireNonNull(threadLocale.get(), "ProviderRegistry not bound");
  }

}
