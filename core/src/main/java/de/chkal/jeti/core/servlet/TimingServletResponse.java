package de.chkal.jeti.core.servlet;

import de.chkal.jeti.core.TimingMetric;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class TimingServletResponse extends HttpServletResponseWrapper {

  private static final Logger log = Logger.getLogger(TimingServletResponse.class.getName());

  private boolean headersent = false;

  protected TimingServletResponse(HttpServletResponse response) {
    super(response);
  }

  private void writerTimingHeader() {

    if (!headersent) {

      if (isCommitted()) {
        log.warning("Cannot write Server-Timing header because response is already committed.");
        return;
      }

      List<TimingMetric> metrics = TimingRegistryHolder.get().getProviders().stream()
          .flatMap(provider -> provider.getMetrics().stream())
          .collect(Collectors.toList());

      if (!metrics.isEmpty()) {
        getWrappedResponse().addHeader("Server-Timing", HeaderSerializer.serializeMetrics(metrics));
      }
      headersent = true;

    }

  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    writerTimingHeader();
    return super.getOutputStream();
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    writerTimingHeader();
    return super.getWriter();
  }

  private HttpServletResponse getWrappedResponse() {
    return (HttpServletResponse) super.getResponse();
  }

}
