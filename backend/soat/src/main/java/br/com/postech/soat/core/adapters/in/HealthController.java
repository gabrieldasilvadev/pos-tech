package br.com.postech.soat.core.adapters.in;

import org.springframework.boot.actuate.health.CompositeHealth;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {
  private final HealthEndpoint healthEndpoint;

  public HealthController(HealthEndpoint healthEndpoint) {
    this.healthEndpoint = healthEndpoint;
  }

  @GetMapping("/health")
  public Map<String, String> customHealth() {
    CompositeHealth health = (CompositeHealth) healthEndpoint.health();
    Map<String, String> response = new LinkedHashMap<>();

    response.put("status", health.getStatus().getCode());

    health.getComponents().forEach((name, component) -> {
      response.put(name, component.getStatus().getCode());
    });


    return response;
  }

}


