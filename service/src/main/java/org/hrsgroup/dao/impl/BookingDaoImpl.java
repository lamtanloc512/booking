package org.hrsgroup.dao.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hrsgroup.*;
import org.hrsgroup.dao.Dao;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class BookingDaoImpl implements Dao<Booking, Long> {
    public static final Logger log = Logger.getLogger(BookingDaoImpl.class.getName());
    @Inject
    UserDaoImpl userDao;

    @Inject
    HotelDaoImpl hotelDao;
    @Inject
    RoomDaopImpl roomDao;

    @PersistenceContext
    EntityManager em;

    public List<Booking> getSomeBookings() {
        TypedQuery<Booking> query = em.createQuery("SELECT n FROM Booking n", Booking.class);
        query.setMaxResults(10);
        return query.getResultList();
    }

    @Override
    public Iterable<Booking> findListByName(String name) {
        return null;
    }

    @Override
    public Optional<Booking> findById(Long id) {
        TypedQuery<Booking> query = em.createQuery("SELECT n FROM Booking n WHERE n.id = :id", Booking.class);
        query.setParameter("id", id);
        try {
            Booking booking = query.getSingleResult();
            return Optional.ofNullable(booking);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Booking save(Booking entity) throws SQLException {
        Long hotelId = entity.getHotel().getId();
        Long userId = entity.getUser().getId();
        Set<Long> rooms = entity.getRooms().stream().map(Room::getId).collect(Collectors.toSet());

        User userFromDb = userDao.findById(userId).orElse(null);
        Hotel hotelFromDb = hotelDao.findById(hotelId).orElse(null);
        if (userFromDb == null || hotelFromDb == null) {
            throw new SQLException("User Or Hotel not found");
        }
        Set<Long> hotelRoomIds = hotelFromDb.getRooms().stream().map(Room::getId).collect(Collectors.toSet());
        for (Long roomId : rooms) {
            if (!hotelRoomIds.contains(roomId)) {
                throw new SQLException("Rooms don't belong to Hotel");
            }
        }

        Booking newBooking = new Booking();
        newBooking.setHotel(hotelFromDb);
        newBooking.setUser(userFromDb);
        newBooking.setRooms(entity.getRooms());

        Double totalPrice = 0d;
        for (Room room : hotelFromDb.getRooms()) {
            totalPrice += room.getPrice();
        }
        newBooking.setTotalPrice(totalPrice);
        log.info("Calculated totalPrice: " + totalPrice + " , Booking totalPrice: " + entity.getTotalPrice());

        em.persist(newBooking);
        return newBooking;
    }

    @Transactional
    public Booking update(Long id, Booking updateBooking) throws SQLException {

        Optional<Booking> optionalBooking = findById(id);
        if (optionalBooking.isEmpty()) {
            throw new SQLException("No booking found!");
        }

        Long hotelId = updateBooking.getHotel() != null ? updateBooking.getHotel().getId() : null;
        Long userId = updateBooking.getUser() != null ? updateBooking.getUser().getId() : null;
        Set<Long> roomIds = updateBooking.getRooms() != null
                ? updateBooking.getRooms().stream().map(Room::getId).collect(Collectors.toSet())
                : Collections.emptySet();

        User userFromDb = userDao.findById(userId).orElse(null);
        Hotel hotelFromDb = hotelDao.findById(hotelId).orElse(null);
        if (userFromDb == null || hotelFromDb == null) {
            throw new SQLException("User Or Hotel not found");
        }

        Set<Long> hotelRoomIds = hotelFromDb.getRooms().stream().map(Room::getId).collect(Collectors.toSet());
        for (Long roomId : roomIds) {
            if (!hotelRoomIds.contains(roomId)) {
                throw new SQLException("Rooms don't belong to Hotel");
            }
        }
        Set<Room> setRoom = roomDao.queryRoomsById(roomIds);

        Booking booking = optionalBooking.get();
        booking.setRooms(setRoom);
        Double totalPrice = 0d;
        for (Room room : booking.getRooms()) {
            totalPrice += room.getPrice();
        }
        booking.setTotalPrice(totalPrice);
        return update(booking);
    }

    @Override
    public Booking update(Booking entity) throws SQLException {
        return em.merge(entity);
    }


    @Override
    @Transactional
    public void deleteById(Long id) throws SQLException {
        Optional<Booking> optionalBooking = this.findById(id);
        if (optionalBooking.isPresent()) {
            em.remove(optionalBooking.get());
        } else {
            throw new SQLException("No Booking found");
        }
    }

}
