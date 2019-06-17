package dao;

import enums.DaoType;

import java.util.Collection;

public interface Dao<ID, D> {
    D findById(ID id);

    void insert(D entity);

    Collection<D> findAll();

    void deleteById(ID id);

    void update(D entity);

    DaoType getDaoType();

    void cache();
}
