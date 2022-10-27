package tests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    private Database db;
    private Person person;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        person = new Person("Tanner123AB", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
        pDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }


    @Test
    void insertPass() throws DataAccessException {
        try {
            pDao.insert(person);
            Person compareTest = pDao.find(person.getPersonID());
            assertNotNull(compareTest);
            assertEquals(person.getPersonID(), compareTest.getPersonID());
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void insertFail() throws DataAccessException {
        pDao.insert(person);
        assertThrows(DataAccessException.class, ()-> pDao.insert(person));
    }


    @Test
    void findPass() throws DataAccessException {
        try {
            pDao.insert(person);
            Person compareTest = pDao.find(person.getPersonID());
            assertNotNull(compareTest);
            assertEquals(person.getPersonID(), compareTest.getPersonID());
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
        try{
            pDao.find("Tanner123AB");
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void findFail() throws DataAccessException {
        try {
            pDao.find(null);
            fail("Should have thrown an exception");
        }catch (DataAccessException e){
            assertNotNull(e);
        }
    }


    @Test
    void clear(){
        try {
            pDao.insert(person);
            pDao.clear();
            Person compareTest = pDao.find(person.getPersonID());
            assertNull(compareTest);
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }
}