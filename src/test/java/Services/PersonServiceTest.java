package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Request.PersonRequest;
import Result.PersonResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {
    private PersonService pService;
    private PersonRequest pRequest;
    private PersonResult pResult;
    private Database db;
    private PersonDao pDao;
    private AuthTokenDao aDao;
    private Person person;
    private Person person2;
    private String authToken;
    private AuthToken token;

    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        db = new Database();
        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
        aDao = new AuthTokenDao(conn);
        pDao.clear();
        aDao.clear();
        token = new AuthToken("authToken", "username");
        person = new Person("personID", "username", "firstName", "lastName", "m", "fatherID", "motherID", "spouseID");
        person2 = new Person("personID2", "username", "firstName", "lastName", "m", "fatherID", "motherID", "spouseID");
        pDao.insert(person);
        pDao.insert(person2);
        aDao.insert(token);
        authToken = "authToken";
        conn.commit();
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void getPersonPass() {
        pRequest = new PersonRequest("authToken", "personID");
        pService = new PersonService();
        pResult = pService.getPerson(pRequest);
        assertEquals(person.getPersonID(), pResult.getPersonID());
    }

    @Test
    void getPersonFail() {
        pRequest = new PersonRequest("authToken", "personID3");
        pService = new PersonService();
        pResult = pService.getPerson(pRequest);
        assertEquals("Error: Person not found", pResult.getMessage());
    }

    @Test
    void getPersonsPass() {
        pRequest = new PersonRequest("authToken", null);
        pService = new PersonService();
        pResult = pService.getPersons(pRequest);
        assertEquals(2, pResult.getData().length);
    }

    @Test
    void getPersonsFail() {
        pRequest = new PersonRequest("authToken2", null);
        pService = new PersonService();
        pResult = pService.getPersons(pRequest);
        assertEquals("Error: Invalid auth token", pResult.getMessage());
    }
}