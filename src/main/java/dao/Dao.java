package dao;

import datatypes.promise.Promise;
import enums.DaoType;

import java.util.Collection;

public interface Dao<ID, D> {
    D findById(ID id);

    Promise insert(D entity);

    Collection<D> findAll();

    Promise deleteById(ID id);

    Promise update(D entity);

    DaoType getDaoType();

    void cache();
}
