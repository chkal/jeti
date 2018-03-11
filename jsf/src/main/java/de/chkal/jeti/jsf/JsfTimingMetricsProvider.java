package de.chkal.jeti.jsf;

import de.chkal.jeti.core.PerformanceTimingMetric;
import de.chkal.jeti.core.TimingMetric;
import de.chkal.jeti.core.TimingMetricsProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.PhaseId;

public class JsfTimingMetricsProvider implements TimingMetricsProvider {

  private static final List<PhaseId> JSF_PHASES = Arrays.asList(
      PhaseId.RESTORE_VIEW,
      PhaseId.APPLY_REQUEST_VALUES,
      PhaseId.PROCESS_VALIDATIONS,
      PhaseId.UPDATE_MODEL_VALUES,
      PhaseId.INVOKE_APPLICATION,
      PhaseId.RENDER_RESPONSE
  );

  private final Map<PhaseId, Long> beforeTimes = new HashMap<>();
  private final Map<PhaseId, Long> afterTimes = new HashMap<>();

  public void notifyBefore(PhaseId phaseId) {
    beforeTimes.put(phaseId, System.currentTimeMillis());
  }

  public void notifyAfter(PhaseId phaseId) {
    afterTimes.put(phaseId, System.currentTimeMillis());
  }

  @Override
  public List<TimingMetric> getMetrics() {

    List<TimingMetric> result = new ArrayList<>();

    for (PhaseId phase : JSF_PHASES) {

      long begin = beforeTimes.getOrDefault(phase, 0L);
      long end = afterTimes.getOrDefault(phase, 0L);

      if (begin > 0 && end > 0 && end >= begin) {

        result.add(new PerformanceTimingMetric() {

          @Override
          public String getName() {
            // use ordinal for correct ordering in Chrome
            return "jsf_" + phase.getOrdinal() + "_" + phase.getName().toLowerCase();
          }

          @Override
          public String getDescription() {
            return phase.getName();
          }

          @Override
          public Number getDuration() {
            return end - begin;
          }

        });

      }

    }

    return result;

  }

}
