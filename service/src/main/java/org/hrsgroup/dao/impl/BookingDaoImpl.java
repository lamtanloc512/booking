package org.hrsgroup.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.hrsgroup.Booking;
import org.hrsgroup.Hotel;
import org.hrsgroup.dao.Dao;

import java.util.Optional;

@ApplicationScoped
public class BookingDaoImpl implements Dao<Booking, Long> {
    @Override
    public Iterable<Booking> findListByName() {
        return null;
    }

    @Override
    public Optional<Booking> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Booking save(Booking entity) {
        return null;
    }

    @Override
    public Booking update(Booking entity) {
        return null;
    }

    @Override
    public void delete(Booking entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
