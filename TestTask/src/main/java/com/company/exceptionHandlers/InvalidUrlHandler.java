package com.company.exceptionHandlers;

import com.company.exceptions.InvalidUrlException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidUrlHandler implements ExceptionMapper<InvalidUrlException> {

    @Override
    public Response toResponse(InvalidUrlException e) {
        return Response.status(404).type("text/plain").entity(e.getMessage()).build();
    }
}
