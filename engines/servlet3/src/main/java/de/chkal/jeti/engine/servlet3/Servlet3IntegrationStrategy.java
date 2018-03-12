package de.chkal.jeti.engine.servlet3;

import de.chkal.jeti.core.servlet.IntegrationStrategy;
import javax.servlet.http.HttpServletResponse;

public class Servlet3IntegrationStrategy implements IntegrationStrategy {

  @Override
  public HttpServletResponse apply(HttpServletResponse response) {
    return new Servlet3WrappedResponse(response);
  }

}
