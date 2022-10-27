package tests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private Database db;
    private User user;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        user = new User("Tanner", "Tanner123A", "tanner@gmail.com", "Tanner", "Montgomery", "m", "Tanner123A");
        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
        uDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }


    @Test
    void insertPass() throws DataAccessException {
        try {
            uDao.insert(user);
            User compareTest = uDao.find(user.getUserName());
            assertNotNull(compareTest);
            assertEquals(user.getUserName(), compareTest.getUserName());
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void insertFail() throws DataAccessException {
        uDao.insert(user);
        assertThrows(DataAccessException.class, ()-> uDao.insert(user));
    }

    @Test
    void testFindPass()  throws DataAccessException{
        try {
            uDao.insert(user);
            User compareTest = uDao.find(user.getUserName());
            assertNotNull(compareTest);
            assertEquals(user.getUserName(), compareTest.getUserName());
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
        try{
            uDao.find("Tanner123A");
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void testFindFail() throws DataAccessException{
        try{
            uDao.find(null);
            fail("Should have thrown an exception");
        }catch (DataAccessException e){
            assertNotNull(e);
        }

    }

    @Test
    void testClear() throws DataAccessException{
        try {
            uDao.insert(user);
            uDao.clear();
            User compareTest = uDao.find(user.getUserName());
            assertNull(compareTest);
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

}
