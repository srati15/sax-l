package dao;

import datatypes.Announcement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AnnouncementDaoTest extends DaoTest{
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void findById() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement announcement = new Announcement("ajs", "jads", true);
        announcementDao.insert(announcement);
        assertEquals(1, announcementDao.findAll().size());


    }

    @Test
    public void insert() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void update() {
    }

    @Test
    public void getDaoType() {
    }
}