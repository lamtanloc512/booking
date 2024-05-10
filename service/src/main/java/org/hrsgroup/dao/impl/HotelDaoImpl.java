package org.hrsgroup.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hrsgroup.Booking;
import org.hrsgroup.Hotel;
import org.hrsgroup.dao.Dao;

import java.util.Optional;

@RequestScoped
public class HotelDaoImpl implements Dao<Hotel, Long> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Iterable<Hotel> findListByName() {
        return null;
    }

    @Override
    public Optional<Hotel> findById(Long id) {
        TypedQuery<Hotel> query = em.createQuery("SELECT h FROM Hotel h WHERE h.id = :id", Hotel.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Hotel save(Hotel entity) {
        return null;
    }

    @Override
    public Hotel update(Hotel entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
