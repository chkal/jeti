package de.chkal.jeti.core.servlet;

import de.chkal.jeti.core.TimingRegistry;
import java.io.IOException;
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

    TimingRegistry registry = new TimingRegistry();

    TimingRegistryHolder.bind(registry);
    try {
      filterChain.doFilter(servletRequest, new TimingServletResponse((HttpServletResponse) servletResponse));
    } finally {
      TimingRegistryHolder.release();
    }

  }

  @Override
  public void destroy() {
    // NOOP
  }

}
