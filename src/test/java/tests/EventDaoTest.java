package tests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EventDaoTest {
    private Database db;
    private Event event;
    private EventDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        event = new Event("Tanner", "Tanner123A", "Tanner", 1.0F, 1.0F, "USA", "Provo", "Birth", 1999);
        Connection conn = db.getConnection();
        eDao = new EventDao(conn);
        eDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void insertPass() throws DataAccessException {
        try {
            eDao.insert(event);
            Event compareTest = eDao.find(event.getEventID());
            assertNotNull(compareTest);
            assertEquals(event.getEventID(), compareTest.getEventID());
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }


    @Test
    void insertFail() throws DataAccessException {
        eDao.insert(event);
        assertThrows(DataAccessException.class, ()-> eDao.insert(event));
    }


    @Test
    void findPass() throws DataAccessException {
        try {
            eDao.insert(event);
            Event compareTest = eDao.find(event.getEventID());
            assertNotNull(compareTest);
            assertEquals(event.getEventID(), compareTest.getEventID());
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
        try {
            eDao.find("Tanner");
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }


    @Test
    void findFail() throws DataAccessException{
        try{
            eDao.find(null);
            fail("Should have thrown an exception");
        }catch (DataAccessException e){
            assertNotNull(e);
        }
    }

    @Test
    void clear() throws DataAccessException {
        try {
            eDao.insert(event);
            eDao.clear();
            Event compareTest = eDao.find(event.getEventID());
            assertNull(compareTest);
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

}