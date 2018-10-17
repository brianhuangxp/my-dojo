package com.demo.lottery.config;

import com.demo.lottery.endpoint.HealthCheckEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import java.util.Set;

@ApplicationPath("/api")
@Component
public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig() {
        Reflections reflections = new Reflections(HealthCheckEndpoint.class.getPackage().getName());
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(Path.class);
        for (Class endpoint : set) {
            register(endpoint);
        }
    }
}
