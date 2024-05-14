package org.hrsgroup.api;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hrsgroup.dao.impl.HotelDaoImpl;
import org.hrsgroup.dto.ResponseDto;

import java.util.Set;

@Path("/hotels")
@Tag(name = "Hotel API")
public class HotelResource {
    @Inject
    HotelDaoImpl hotelDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get hotels by name", description = "Retrieve a list of hotels by name.")
    public Response getHotelsByName(@QueryParam("name") String name) {
        return Response.ok(ResponseDto.builder()
                .code(Response.Status.OK.getStatusCode())
                .message("Ok")
                .data(hotelDao.findListByName(name))
                .build()).build();
    }
}
