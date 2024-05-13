package org.hrsgroup.dao.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hrsgroup.dao.Dao;
import org.hrsgroup.model.Hotel;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequestScoped
public class HotelDaoImpl implements Dao<Hotel, Long> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Hotel> findListByName(String name) {
        TypedQuery<Hotel> query;
        if (name != null && !name.isEmpty()) {
            query = em.createQuery(
                    "SELECT h FROM Hotel h WHERE lower(h.name) LIKE lower(:name)", Hotel.class);
            query.setParameter("name", name + "%");
        } else {
            query = em.createQuery(
                    "SELECT h FROM Hotel h", Hotel.class);
            query.setMaxResults(10);
        }
        return new HashSet<>(query.getResultList());
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
