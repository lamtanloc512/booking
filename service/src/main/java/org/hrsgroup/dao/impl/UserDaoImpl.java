package org.hrsgroup.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hrsgroup.Booking;
import org.hrsgroup.User;
import org.hrsgroup.dao.Dao;

import java.sql.SQLException;
import java.util.Optional;

@RequestScoped
public class UserDaoImpl implements Dao<User, Long> {

    @PersistenceContext
    EntityManager em;

    @Override
    public Iterable<User> findListByName() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public User save(User entity) throws SQLException {
        return null;
    }

    @Override
    public User update(User entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteById(Long aLong) throws SQLException {

    }
}
