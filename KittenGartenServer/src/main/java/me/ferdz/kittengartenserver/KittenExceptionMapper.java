package me.ferdz.kittengartenserver;

import me.ferdz.kittengartenserver.exception.CookieExpiredException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by 1452284 on 2016-10-20.
 */
@Provider
public class KittenExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        e.printStackTrace();

        if(e instanceof CookieExpiredException)
            return Response.status(505).entity(e.getMessage()).build();

        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
