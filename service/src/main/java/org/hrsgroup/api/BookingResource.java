package org.hrsgroup.api;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBodySchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hrsgroup.dao.impl.BookingDaoImpl;
import org.hrsgroup.dto.ResponseDto;
import org.hrsgroup.model.Booking;

import java.sql.SQLException;
import java.util.*;

@Path("/bookings")
@Tag(name = "Booking API")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {
    @Inject
    BookingDaoImpl bookingDao;

    @GET
    @PermitAll
    @Operation(summary = "Get some bookings", description = "Retrieve a list of all bookings.")
    @APIResponse(responseCode = "200",
            description = "List of bookings",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    public Response getBookings() {
        return Response.ok(ResponseDto.builder()
                .code(200)
                .message("Ok!")
                .data(bookingDao.getSomeBookings())
                .build()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get booking by ID", description = "Retrieve a booking by its ID.")
    @APIResponse(responseCode = "200", description = "The booking", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    @APIResponse(responseCode = "404", description = "Booking not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    public Response getBookingById(@PathParam("id") Long id) {
        Optional<Booking> optionalBooking = bookingDao.findById(id);
        ResponseDto.ResponseDtoBuilder responseDtoBd = ResponseDto.builder()
                .code(200)
                .message("Ok!")
                .data(optionalBooking.orElse(null));
        if (optionalBooking.isEmpty()) {
            responseDtoBd
                    .code(404)
                    .message("Not Found!")
                    .data(null);
        }
        return Response.ok(responseDtoBd.build()).build();
    }

    @POST
    @Operation(summary = "Create a new booking", description = "Create a new booking.")
    @APIResponse(responseCode = "200", description = "The created booking", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    @APIResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    @RequestBodySchema(Booking.class)
    public Response createBooking(Booking booking) {
        ResponseDto.ResponseDtoBuilder responseDtoBuilder = ResponseDto.builder();
        try {
            responseDtoBuilder
                    .code(201)
                    .message("Created!")
                    .data(bookingDao.save(booking));
        } catch (SQLException e) {
            responseDtoBuilder
                    .code(500)
                    .message("Some thing went wrong!: " + e.getMessage())
                    .data(null);
        }
        return Response.ok(responseDtoBuilder.build()).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a existed booking", description = "Update a existed booking.")
    @APIResponse(responseCode = "200", description = "The updated booking", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    @APIResponse(responseCode = "409", description = "Update fail or conflict", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    @RequestBodySchema(Booking.class)
    public Response updateBooking(@PathParam("id") Long id, Booking incomingBooking) {
        ResponseDto.ResponseDtoBuilder responseDtoBuilder = ResponseDto.builder();
        try {
            responseDtoBuilder
                    .code(200)
                    .message("Updated!")
                    .data(bookingDao.update(id, incomingBooking));
        } catch (SQLException e) {
            responseDtoBuilder
                    .code(409)
                    .message("Some thing went wrong!: " + e.getMessage())
                    .data(null);
        }
        return Response.ok(responseDtoBuilder.build()).build();
    }


    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a booking", description = "Delete a booking by its ID.")
    @APIResponse(responseCode = "200", description = "Booking deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    @APIResponse(responseCode = "409", description = "Bad request or Delete conflict", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    public Response deleteBooking(@PathParam("id") Long id) {
        ResponseDto.ResponseDtoBuilder responseDtoBuilder = ResponseDto.builder();
        try {
            bookingDao.deleteById(id);
            responseDtoBuilder
                    .code(200)
                    .message("Deleted!")
                    .data(null);
        } catch (SQLException e) {
            responseDtoBuilder
                    .code(409)
                    .message("Some thing went wrong!: " + e.getMessage())
                    .data(null);
        }
        return Response.ok(responseDtoBuilder.build()).build();
    }

}
