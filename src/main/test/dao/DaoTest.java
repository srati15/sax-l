package dao;

import dao.dbconnector.MyDBInfoMock;
import database.CreateConnection;
import org.junit.Before;

public class DaoTest {
    @Before
    public void setUp() throws Exception {
        CreateConnection.setTestMyDBInfo(new MyDBInfoMock());
    }
}
