package de.chkal.jeti.core.servlet;

import de.chkal.jeti.core.TimingRegistry;
import java.util.Objects;

public class TimingRegistryHolder {

  private static final ThreadLocal<TimingRegistry> threadLocale = new ThreadLocal<>();

  protected static void bind(TimingRegistry registry) {
    threadLocale.set(registry);
  }

  protected static void release() {
    threadLocale.set(null);
  }

  public static TimingRegistry get() {
    return Objects.requireNonNull(threadLocale.get(), "TimingRegistry not bound");
  }

}
