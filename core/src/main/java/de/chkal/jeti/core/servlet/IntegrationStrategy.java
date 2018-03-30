package de.chkal.jeti.core.servlet;

import de.chkal.jeti.core.ProviderRegistry;
import javax.servlet.http.HttpServletResponse;

public interface IntegrationStrategy {

  HttpServletResponse apply(HttpServletResponse response, ProviderRegistry registry);

  void finish(HttpServletResponse wrappedResponse, ProviderRegistry registry);

}
