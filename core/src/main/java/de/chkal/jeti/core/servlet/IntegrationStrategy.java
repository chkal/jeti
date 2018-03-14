package de.chkal.jeti.core.servlet;

import de.chkal.jeti.core.TimingRegistry;
import javax.servlet.http.HttpServletResponse;

public interface IntegrationStrategy {

  HttpServletResponse apply(HttpServletResponse response, TimingRegistry registry);

}
