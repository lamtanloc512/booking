package org.hrsgroup.dao;

import java.util.Optional;

public interface Dao<T, ID> {
    Iterable<T> findListByName();
    Optional<T> findById(ID id);
    T save(T entity);
    T update(T entity);
    void delete(T entity);
    void deleteById(ID id);
}
