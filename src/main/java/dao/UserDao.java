package dao;

import datatypes.user.User;

public interface UserDao extends Dao<Integer, User> {
    User findByUserName(String userName);
}
