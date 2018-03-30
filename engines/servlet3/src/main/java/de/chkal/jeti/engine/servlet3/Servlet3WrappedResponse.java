package de.chkal.jeti.engine.servlet3;

import de.chkal.jeti.core.ServerTimingHeader;
import de.chkal.jeti.core.Metric;
import de.chkal.jeti.core.servlet.ProviderRegistryHolder;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class Servlet3WrappedResponse extends HttpServletResponseWrapper {

  private static final Logger log = Logger.getLogger(Servlet3WrappedResponse.class.getName());

  private enum OutputMode {
    STREAM, WRITER
  }

  private OutputMode outputMode;

  private BufferedServletOutputStream bufferedOutputStream;

  private PrintWriter bufferedWriter;

  protected Servlet3WrappedResponse(HttpServletResponse response) {
    super(response);
  }

  protected void finish() {

    // send timing headers just before the body is emitted
    writerServerTimingHeader();

    try {

      // maybe nobody ever call getOutputStream() or getWriter()
      if (bufferedOutputStream != null) {

        // make sure the writer flushes everything to the underlying output stream
        if (bufferedWriter != null) {
          bufferedWriter.flush();
        }

        // send the buffered response to the client
        bufferedOutputStream.writeBufferTo(getResponse().getOutputStream());

      }

    } catch (IOException e) {
      throw new IllegalStateException("Could not flush response buffer", e);
    }

  }

  private void writerServerTimingHeader() {

    if (isCommitted()) {
      log.warning("Cannot write Server-Timing header, because response is already committed.");
      return;
    }

    List<Metric> metrics = ProviderRegistryHolder.get().getProviders().stream()
        .flatMap(provider -> provider.getMetrics().stream())
        .collect(Collectors.toList());

    if (!metrics.isEmpty()) {
      ((HttpServletResponse) getResponse()).addHeader(
          ServerTimingHeader.HEADER_NAME,
          ServerTimingHeader.serializeMetrics(metrics)
      );
    }

  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {

    if (outputMode == OutputMode.WRITER) {
      throw new IllegalStateException("You cannot call getOutputStream() after getWriter()");
    }

    if (bufferedOutputStream == null) {
      bufferedOutputStream = new BufferedServletOutputStream();
      outputMode = OutputMode.STREAM;
    }
    return bufferedOutputStream;

  }

  @Override
  public PrintWriter getWriter() throws IOException {

    if (outputMode == OutputMode.STREAM) {
      throw new IllegalStateException("You cannot call getWriter() after getOutputStream()");
    }

    if (bufferedWriter == null) {
      bufferedOutputStream = new BufferedServletOutputStream();
      bufferedWriter = new PrintWriter(new OutputStreamWriter(bufferedOutputStream, getCharacterEncoding()));
      outputMode = OutputMode.WRITER;
    }
    return bufferedWriter;

  }

}
