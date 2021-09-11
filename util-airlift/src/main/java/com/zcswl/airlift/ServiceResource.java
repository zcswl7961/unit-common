package com.zcswl.airlift;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author xingyi
 * @date 2021/9/11
 */
@Path("/v1/service")
public class ServiceResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello()
    {
        return "Hello Airlift!";
    }
}
