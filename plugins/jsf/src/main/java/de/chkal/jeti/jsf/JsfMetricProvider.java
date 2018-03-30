package de.chkal.jeti.jsf;

import de.chkal.jeti.core.DefaultPerformanceMetric;
import de.chkal.jeti.core.Metric;
import de.chkal.jeti.core.MetricProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.PhaseId;

public class JsfMetricProvider implements MetricProvider {

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
  public List<Metric> getMetrics() {

    List<Metric> result = new ArrayList<>();

    for (PhaseId phase : JSF_PHASES) {

      long begin = beforeTimes.getOrDefault(phase, 0L);
      long end = afterTimes.getOrDefault(phase, 0L);

      if (begin > 0 && end > 0) {

        // Name: use ordinal for correct ordering in Chrome
        String name = "jsf_" + phase.getOrdinal() + "_" + phase.getName().toLowerCase();

        String description = getDescription(phase);

        result.add(new DefaultPerformanceMetric(name, description, end - begin));

      }

    }

    return result;

  }

  private static String getDescription(PhaseId phaseId) {

    StringBuilder builder = new StringBuilder();
    boolean nextUppercase = true;

    for (char c : phaseId.getName().toCharArray()) {
      if (c == '_') {
        nextUppercase = true;
      } else {
        if (nextUppercase) {
          builder.append(String.valueOf(c).toUpperCase());
          nextUppercase = false;
        } else {
          builder.append(String.valueOf(c).toLowerCase());
        }
      }
    }

    return builder.toString();

  }

}
