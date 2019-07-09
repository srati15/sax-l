package dao;

import enums.DaoType;

import java.util.Collection;

public interface Dao<ID, D> {
    D findById(ID id);

    boolean insert(D entity);

    Collection<D> findAll();

    boolean deleteById(ID id);

    boolean update(D entity);

    DaoType getDaoType();

    void cache();
}
