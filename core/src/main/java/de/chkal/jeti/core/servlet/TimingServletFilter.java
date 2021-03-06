package de.chkal.jeti.core.servlet;

import de.chkal.jeti.core.ProviderRegistry;
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

  public static final String JETI_ENABLED_PROPERTY = "jeti.active";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // NOOP
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletResponse response = (HttpServletResponse) servletResponse;

    ProviderRegistry registry = new ProviderRegistry();

    ProviderRegistryHolder.bind(registry);
    try {

      boolean enabled = Boolean.parseBoolean(System.getProperty(JETI_ENABLED_PROPERTY));

      // JETI active
      if (enabled) {

        IntegrationStrategy strategy = getIntegrationStrategy();

        HttpServletResponse wrappedResponse = strategy.apply(response, registry);
        filterChain.doFilter(servletRequest, wrappedResponse);
        strategy.finish(wrappedResponse, registry);

      }

      // JETI inactive
      else {
        filterChain.doFilter(servletRequest, servletResponse);
      }


    } finally {
      ProviderRegistryHolder.release();
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
