package dao;

import enums.Achievement;
import enums.DaoType;

import java.util.Collection;

public class AchievementDao implements Dao<Integer, Achievement> {
    @Override
    public Achievement findById(Integer integer) {
        return null;
    }

    @Override
    public void insert(Achievement entity) {

    }

    @Override
    public Collection<Achievement> findAll() {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void update(Achievement entity) {

    }

    @Override
    public DaoType getDaoType() {
        return null;
    }

    @Override
    public void cache() {

    }
}
