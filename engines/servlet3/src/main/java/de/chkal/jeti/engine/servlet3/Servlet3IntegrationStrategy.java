package de.chkal.jeti.engine.servlet3;

import de.chkal.jeti.core.TimingRegistry;
import de.chkal.jeti.core.servlet.IntegrationStrategy;
import javax.servlet.http.HttpServletResponse;

public class Servlet3IntegrationStrategy implements IntegrationStrategy {

  @Override
  public HttpServletResponse apply(HttpServletResponse response, TimingRegistry registry) {
    return new Servlet3WrappedResponse(response);
  }

  @Override
  public void finish(HttpServletResponse response, TimingRegistry registry) {
    if (response instanceof Servlet3WrappedResponse) {
      ((Servlet3WrappedResponse) response).finish();
    }
  }

}
