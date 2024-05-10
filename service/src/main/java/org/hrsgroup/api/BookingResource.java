package org.hrsgroup.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hrsgroup.Booking;
import org.hrsgroup.dao.impl.BookingDaoImpl;

import java.awt.print.Book;
import java.sql.SQLException;
import java.util.*;

@Path("/bookings")
public class BookingResource {
    @Inject
    BookingDaoImpl bookingDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookings() {
        List<Booking> bookingList = bookingDao.getSomeBookings();
        return Response.ok(bookingList).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingById(@PathParam("id") Long id) {
        Optional<Booking> optionalBooking = bookingDao.findById(id);
        if(optionalBooking.isPresent()) {
            return Response.ok(optionalBooking.orElse(null)).build();
        }
        return Response.ok(Map.of("Message", "Not Found!")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBooking(Booking booking) {
        try {
            return Response.ok(bookingDao.save(booking)).build();
        } catch (SQLException e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBooking(@PathParam("id") Long id, Booking partialBooking) {
        Map<String, String> map = new HashMap<>() {{
            put("code", "200");
        }};
        try {
            bookingDao.update(id, partialBooking);
            return Response.ok(map).build();
        } catch (SQLException e) {
            map.put("code", "400");
            map.put("message", e.getMessage());
            return Response.ok(map).build();
        }
    }


    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteBooking(@PathParam("id") Long id) {
        Map<String, String> map = Map.of("code", "200");
        try {
            bookingDao.deleteById(id);
            return Response.ok(map).build();
        } catch (SQLException e) {
            map.put("code", "400");
            return Response.ok(map).build();
        }
    }

}
