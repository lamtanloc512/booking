package org.hrsgroup.dao.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hrsgroup.model.Room;

import java.util.HashSet;
import java.util.Set;

@RequestScoped
public class RoomDaoImpl {
    @PersistenceContext
    private EntityManager em;

    public Set<Room> queryRoomsById(Set<Long> ids) {
        TypedQuery<Room> query = em.createQuery("SELECT r FROM Room r WHERE r.id IN :setIds", Room.class);
        query.setParameter("setIds", ids);
        return new HashSet<>(query.getResultList());
    }

}
