package dao;

import java.util.List;

public interface Dao<D> {
    D findById(int id);

    void insert(D entity);

    List<D> findAll();

    void deleteById(int id);

}