package com.vipshop.microscope.test.app.action;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

/**
 * User: harry.chen
 * Date: 13-11-20
 * Time: 下午4:38
 */
@Path("/rest")
@Component
public class RestResource {
    @GET
    public Response rest() {
        long begin = System.nanoTime();
        return Response.status(200).entity(begin).build();
    }
}
