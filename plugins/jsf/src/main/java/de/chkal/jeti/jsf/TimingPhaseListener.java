package de.chkal.jeti.jsf;

import de.chkal.jeti.core.ProviderRegistry;
import de.chkal.jeti.core.servlet.ProviderRegistryHolder;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class TimingPhaseListener implements PhaseListener {

  @Override
  public void beforePhase(PhaseEvent phaseEvent) {
    getMetricsProvider().notifyBefore(phaseEvent.getPhaseId());
  }

  @Override
  public void afterPhase(PhaseEvent phaseEvent) {
    getMetricsProvider().notifyAfter(phaseEvent.getPhaseId());
  }

  @Override
  public PhaseId getPhaseId() {
    return PhaseId.ANY_PHASE;
  }

  private JsfMetricProvider getMetricsProvider() {
    ProviderRegistry registry = ProviderRegistryHolder.get();
    JsfMetricProvider provider = registry.getProviderByType(JsfMetricProvider.class).orElse(null);
    if (provider == null) {
      provider = new JsfMetricProvider();
      registry.register(provider);
    }
    return provider;
  }

}
