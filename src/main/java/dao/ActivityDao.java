package dao;

import datatypes.server.Activity;

import java.util.Collection;

public interface ActivityDao extends Dao<Integer, Activity> {
    void shutDown();
    Collection<Activity> findAllForUser(Integer id);
}
