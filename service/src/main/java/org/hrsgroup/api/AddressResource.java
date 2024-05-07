package org.hrsgroup.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hrsgroup.dao.impl.AddressDaoImpl;

@Path("/address")
public class AddressResource {

    @Inject
    AddressDaoImpl addressDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAddresses() {
        return Response.ok(addressDao.findAll()).build();
    }
}
