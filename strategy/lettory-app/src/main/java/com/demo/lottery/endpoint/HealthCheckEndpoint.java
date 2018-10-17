package com.demo.lottery.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/health")
public class HealthCheckEndpoint {

    @GET
    public Response checkHealth() {
        return Response.ok("OK").build();
    }

}
