package de.chkal.jeti.core.servlet;

import de.chkal.jeti.core.TimingRegistry;
import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class TimingServletFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // NOOP
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletResponse response = (HttpServletResponse) servletResponse;

    TimingRegistry registry = new TimingRegistry();

    TimingRegistryHolder.bind(registry);
    try {

      HttpServletResponse wrappedResponse = getIntegrationStrategy().apply(response, registry);

      filterChain.doFilter(servletRequest, wrappedResponse);


    } finally {
      TimingRegistryHolder.release();
    }

  }

  @Override
  public void destroy() {
    // NOOP
  }

  private IntegrationStrategy getIntegrationStrategy() {

    Iterator<IntegrationStrategy> iterator =
        ServiceLoader.load(IntegrationStrategy.class).iterator();

    if (!iterator.hasNext()) {
      throw new IllegalStateException("Please include either the Servlet 3 or Servlet 4 engine on your classpath");
    }

    return iterator.next();


  }

}
