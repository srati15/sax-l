package dao;

import datatypes.Announcement;
import datatypes.User;
import enums.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDao implements Dao<Announcement> {

    @Override
    public Announcement findById(int id) {
        return null;
    }

    @Override
    public void insert(Announcement entity) {

    }

    @Override
    public List<Announcement> findAll() {
        // TODO: 5/31/19
        return new ArrayList<>();
    }

    @Override
    public void deleteById(int id) {

    }
}
