package de.chkal.jeti.engine.servlet3;

import de.chkal.jeti.core.ServerTimingHeader;
import de.chkal.jeti.core.TimingMetric;
import de.chkal.jeti.core.servlet.TimingRegistryHolder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class Servlet3WrappedResponse extends HttpServletResponseWrapper {

  private static final Logger log = Logger.getLogger(Servlet3WrappedResponse.class.getName());

  private boolean headerSent = false;

  protected Servlet3WrappedResponse(HttpServletResponse response) {
    super(response);
  }

  private void writerServerTimingHeader() {

    if (headerSent) {
      return;
    }

    if (isCommitted()) {
      log.warning("Cannot write Server-Timing header, because response is already committed.");
      return;
    }

    List<TimingMetric> metrics = TimingRegistryHolder.get().getProviders().stream()
        .flatMap(provider -> provider.getMetrics().stream())
        .collect(Collectors.toList());

    if (!metrics.isEmpty()) {
      getWrappedResponse().addHeader(
          ServerTimingHeader.HEADER_NAME,
          ServerTimingHeader.serializeMetrics(metrics)
      );
    }
    headerSent = true;

  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    writerServerTimingHeader();
    return super.getOutputStream();
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    writerServerTimingHeader();
    return super.getWriter();
  }

  private HttpServletResponse getWrappedResponse() {
    return (HttpServletResponse) super.getResponse();
  }

}
