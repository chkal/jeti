package de.chkal.jeti.core.servlet;

import javax.servlet.http.HttpServletResponse;

public interface IntegrationStrategy {

  HttpServletResponse apply(HttpServletResponse response);

}
