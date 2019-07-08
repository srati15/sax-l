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
    public void findById1() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann = new Announcement(1, "dato", "", true);
        announcementDao.insert(ann);
        assertEquals(ann.isActive(), announcementDao.findById(ann.getId()).isActive());
        assertEquals(ann.getAnnouncementText(), announcementDao.findById(ann.getId()).getAnnouncementText());
        assertEquals(ann.getId(), announcementDao.findById(ann.getId()).getId());
        assertEquals(ann.getHyperLink(), announcementDao.findById(ann.getId()).getHyperLink());
        announcementDao.deleteById(ann.getId());
    }

    @Test
    public void findById2() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann = new Announcement(1,"dato", "", true);
        Announcement ann2 = new Announcement(1, "dato", "", false);
        announcementDao.insert(ann);
        announcementDao.insert(ann2);
        assertEquals(ann.isActive(), announcementDao.findById(ann.getId()).isActive());
        assertEquals(ann.getAnnouncementText(), announcementDao.findById(ann.getId()).getAnnouncementText());
        assertEquals(ann.getId(), announcementDao.findById(ann.getId()).getId());
        assertEquals(ann.getHyperLink(), announcementDao.findById(ann.getId()).getHyperLink());
        announcementDao.deleteById(ann.getId());
        announcementDao.deleteById(ann2.getId());
    }

    @Test
    public void findById3() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann = new Announcement(1,"dato", "", true);
        announcementDao.insert(ann);
        assertEquals(ann.isActive(), announcementDao.findById(ann.getId()).isActive());
        assertEquals(ann.getAnnouncementText(), announcementDao.findById(ann.getId()).getAnnouncementText());
        assertEquals(ann.getId(), announcementDao.findById(ann.getId()).getId());
        assertEquals(ann.getHyperLink(), announcementDao.findById(ann.getId()).getHyperLink());
        announcementDao.deleteById(ann.getId());
    }

    @Test
    public void findById4() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann = new Announcement(1,"dato", "", true);
        announcementDao.insert(ann);
        assertEquals(ann.isActive(), announcementDao.findById(ann.getId()).isActive());
        assertEquals(ann.getAnnouncementText(), announcementDao.findById(ann.getId()).getAnnouncementText());
        assertEquals(ann.getId(), announcementDao.findById(ann.getId()).getId());
        assertEquals(ann.getHyperLink(), announcementDao.findById(ann.getId()).getHyperLink());
        announcementDao.deleteById(ann.getId());
    }

    @Test
    public void insert1() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann = new Announcement(1, "dato", "", true);
        announcementDao.insert(ann);
        assertEquals(1, announcementDao.findAll().size());
        announcementDao.deleteById(ann.getId());

    }

    @Test
    public void insert2() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann = new Announcement(1,"dato", "", true);
        announcementDao.insert(ann);
        assertEquals(1, announcementDao.findAll().size());
        announcementDao.deleteById(ann.getId());

    }

    @Test
    public void insert3() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann = new Announcement(1,"dato", "", true);
        announcementDao.insert(ann);
        assertEquals(1, announcementDao.findAll().size());
        announcementDao.deleteById(ann.getId());

    }

    @Test
    public void insert4() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann = new Announcement(1,"dato", "", true);
        announcementDao.insert(ann);
        assertEquals(1, announcementDao.findAll().size());
        announcementDao.deleteById(ann.getId());

    }

    @Test
    public void insert5() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann = new Announcement(1,"dato", "", true);
        announcementDao.insert(ann);
        assertEquals(1, announcementDao.findAll().size());
        announcementDao.deleteById(ann.getId());

    }

    @Test
    public void findAll() {
    }

    @Test
    public void deleteById() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann = new Announcement(1,"dato", "", true);
        announcementDao.insert(ann);
        announcementDao.deleteById(ann.getId());
        assertEquals(2, announcementDao.findAll().size());
    }

    @Test
    public void update() {
        AnnouncementDao announcementDao = new AnnouncementDao();
        Announcement ann1 = new Announcement(1,"dato", "", false);
        announcementDao.insert(ann1);
        Announcement ann2 = new Announcement(ann1.getId(), "dato", "", true);
        announcementDao.update(ann2);
        assertEquals(ann2.isActive(), announcementDao.findById(ann1.getId()).isActive());
        announcementDao.deleteById(ann1.getId());
    }

    @Test
    public void getDaoType() {
    }
}