package de.chkal.jeti.jsf;

import de.chkal.jeti.core.TimingRegistry;
import de.chkal.jeti.core.servlet.TimingRegistryHolder;
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

  private JsfTimingMetricsProvider getMetricsProvider() {
    TimingRegistry registry = TimingRegistryHolder.get();
    JsfTimingMetricsProvider provider = registry.getProviderByType(JsfTimingMetricsProvider.class);
    if (provider == null) {
      provider = new JsfTimingMetricsProvider();
      registry.register(provider);
    }
    return provider;
  }

}
