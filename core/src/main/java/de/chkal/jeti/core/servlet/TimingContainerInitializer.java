package de.chkal.jeti.core.servlet;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class TimingContainerInitializer implements ServletContainerInitializer {

  private static final Logger log = Logger.getLogger(TimingContainerInitializer.class.getName());

  @Override
  public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

    if (!isFilterAlreadyRegistered(servletContext)) {
      log.log(Level.INFO, "Automatically registering {0}...", TimingServletFilter.class.getSimpleName());
      servletContext.addFilter(TimingServletFilter.class.getSimpleName(), TimingServletFilter.class)
          .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
    }

  }

  private boolean isFilterAlreadyRegistered(ServletContext servletContext) {
    return servletContext.getFilterRegistrations().values().stream()
        .anyMatch(r -> Objects.equals(r.getClassName(), TimingServletFilter.class.getName()));
  }

}
