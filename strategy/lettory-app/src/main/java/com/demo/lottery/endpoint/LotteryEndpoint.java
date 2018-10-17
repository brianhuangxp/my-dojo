package com.demo.lottery.endpoint;

import com.demo.lottery.service.LotteryService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/lottery")
public class LotteryEndpoint {

    @Inject
    LotteryService lotteryService;

    @GET
    @Path("/daily")
    public Response dailyLottery() {
        lotteryService.dailyLottery();
        return Response.ok("OK").build();
    }

    @GET
    @Path("/dropDoorItems")
    public Response dropDoorItems() {
        lotteryService.dropDoorItems();
        return Response.ok("OK").build();
    }

    @GET
    @Path("/dropInstanceItems")
    public Response dropInstanceItems() {
        lotteryService.loadInstanceItems();
        return Response.ok("OK").build();
    }
}
