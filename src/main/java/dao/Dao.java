package dao;

import enums.DaoType;

import java.util.List;

public interface Dao<ID, D> {
    D findById(ID id);

    void insert(D entity);

    List<D> findAll();

    void deleteById(ID id);

    void update(D entity);

    DaoType getDaoType();
}
