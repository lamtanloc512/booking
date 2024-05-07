package org.hrsgroup.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hrsgroup.Hotel;
import org.hrsgroup.dao.Dao;

import java.util.Optional;

@ApplicationScoped
public class HotelDaoImpl implements Dao<Hotel, Long> {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager entityManager;

    @Override
    public Iterable<Hotel> findListByName() {
        return null;
    }

    @Override
    public Optional<Hotel> findById(Long aLong) {
        return Optional.empty();
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
    public void delete(Hotel entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
