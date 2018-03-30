package de.chkal.jeti.engine.servlet3;

import de.chkal.jeti.core.ProviderRegistry;
import de.chkal.jeti.core.servlet.IntegrationStrategy;
import javax.servlet.http.HttpServletResponse;

public class Servlet3IntegrationStrategy implements IntegrationStrategy {

  @Override
  public HttpServletResponse apply(HttpServletResponse response, ProviderRegistry registry) {
    return new Servlet3WrappedResponse(response);
  }

  @Override
  public void finish(HttpServletResponse response, ProviderRegistry registry) {
    if (response instanceof Servlet3WrappedResponse) {
      ((Servlet3WrappedResponse) response).finish();
    }
  }

}
