package com.redhat.coolstore.inventory.rest;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;


@Health
@ApplicationScoped
public class HealthCheckResource  implements HealthCheck {
	
	private String env = "dev";

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.named("sessions-check")
				.withData("env", env)
				.withData("lastCheckDate", new Date().toString())
				.state(true)
				.build();
	}

}
