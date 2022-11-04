package DaoTest;

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

    @Test
    void insertSpouseIDPass() throws DataAccessException {
        try {
            pDao.insert(person);
            pDao.insertSpouseID("deborah123A", person.getPersonID());
            Person compareTest = pDao.find(person.getPersonID());
            assertNotNull(compareTest);
            assertNotEquals(person.getSpouseID(), compareTest.getSpouseID());
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void insertSpouseIDFail() throws DataAccessException {
        try {
            pDao.insert(person);
            pDao.insertSpouseID("deborah123A", person.getPersonID());
            Person compareTest = pDao.find("Jenna123A");
            assertNull(compareTest);
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void findAllPass() throws DataAccessException {
        try {
            pDao.insert(person);
            Person person1 = new Person("Tanner123AC", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person1);
            Person person2 = new Person("Tanner123AD", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person2);
            Person person3 = new Person("Tanner123AE", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person3);
            Person person4 = new Person("Tanner123AF", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person4);
            Person person5 = new Person("Tanner123AG", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person5);
            pDao.findAll("Tanner123A");
            assert (pDao.findAll("Tanner123A").length == 6);
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void findAllFail() throws DataAccessException {
        try {
            pDao.insert(person);
            Person person1 = new Person("Tanner123AC", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person1);
            Person person2 = new Person("Tanner123AD", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person2);
            Person person3 = new Person("Tanner123AE", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person3);
            Person person4 = new Person("Tanner123AF", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person4);
            Person person5 = new Person("Tanner123AG", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person5);
            pDao.findAll("Tanner123A");
            assertNull(pDao.findAll("Tanner123B"));
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void deleteAllPass(){
        try {
            pDao.insert(person);
            Person person1 = new Person("Tanner123AC", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person1);
            Person person2 = new Person("Tanner123AD", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person2);
            Person person3 = new Person("Tanner123AE", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person3);
            Person person4 = new Person("Tanner123AF", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person4);
            Person person5 = new Person("Tanner123AG", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person5);
            pDao.deleteAll("Tanner123A");
            assertNull (pDao.findAll("Tanner123A"));
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void deleteAllFail(){
        try {
            pDao.insert(person);
            Person person1 = new Person("Tanner123AC", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person1);
            Person person2 = new Person("Tanner123AD", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person2);
            Person person3 = new Person("Tanner123AE", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person3);
            Person person4 = new Person("Tanner123AF", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person4);
            Person person5 = new Person("Tanner123AG", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person5);
            pDao.deleteAll("Tanner123B");
            assertNotNull (pDao.findAll("Tanner123A"));
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void deletePass(){
        try {
            pDao.insert(person);
            Person person1 = new Person("Tanner123AC", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person1);
            Person person2 = new Person("Tanner123AD", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person2);
            Person person3 = new Person("Tanner123AE", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person3);
            Person person4 = new Person("Tanner123AF", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person4);
            Person person5 = new Person("Tanner123AG", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person5);
            pDao.delete("Tanner123AD");
            assertNull (pDao.find("Tanner123AD"));
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }

    @Test
    void deleteFail(){
        try {
            pDao.insert(person);
            Person person1 = new Person("Tanner123AC", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person1);
            Person person2 = new Person("Tanner123AD", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person2);
            Person person3 = new Person("Tanner123AE", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person3);
            Person person4 = new Person("Tanner123AF", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person4);
            Person person5 = new Person("Tanner123AG", "Tanner123A", "Tanner", "Montgomery", "m", "Steve123A", "Deborah123A", "Jenna123A");
            pDao.insert(person5);
            assertThrows(DataAccessException.class, ()-> pDao.delete("Tanner123A"));
        }catch (DataAccessException e){
            fail(e.getMessage());
        }
    }


}