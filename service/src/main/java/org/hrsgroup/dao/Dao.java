package org.hrsgroup.dao;

import java.sql.SQLException;
import java.util.Optional;

public interface Dao<T, ID> {
    Iterable<T> findListByName(String name);
    Optional<T> findById(ID id);
    T save(T entity) throws SQLException;
    T update(T entity) throws SQLException;
    void deleteById(ID id) throws SQLException;
}
