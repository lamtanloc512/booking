package org.hrsgroup.dao.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hrsgroup.dao.Dao;
import org.hrsgroup.model.Address;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AddressDaoImpl implements Dao<Address, Long> {

    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    public List<Address> findAll() {
        TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a", Address.class);
        query.setMaxResults(10);
        return query.getResultList();
    }

    @Override
    public Iterable<Address> findListByName(String name) {
        return null;
    }

    @Override
    public Optional<Address> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Address save(Address entity) {
        return null;
    }

    @Override
    public Address update(Address entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
