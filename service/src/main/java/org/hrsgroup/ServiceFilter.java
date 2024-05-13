package org.hrsgroup;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.IOException;

@ApplicationScoped
@Provider
@Priority(Priorities.AUTHENTICATION)
public class ServiceFilter implements ContainerRequestFilter {
    @Inject
    private JsonWebToken jwt;

    @Context
    private HttpHeaders httpHeaders;
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (jwt == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
