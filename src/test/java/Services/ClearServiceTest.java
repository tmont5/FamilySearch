package Services;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.ClearRequest;
import Request.LoadRequest;
import Request.LoginRequest;
import Result.ClearResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
    private ClearService cService;
    private ClearRequest cRequest;
    private LoadRequest lRequest;
    private LoadService lService;
    private LoginService loginService;
    private LoginRequest loginRequest;

    private Database db;
    private String message;
    private boolean success;
    private ClearResult cResult;
    private UserDao uDao;
    private PersonDao pDao;
    private EventDao eDao;
    private AuthTokenDao aDao;

    private Person[] persons;
    private Event[] events;
    private User[] users;

    private Person person;
    private Event event;
    private User user;


    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        db = new Database();
        Connection conn = db.getConnection();

        uDao = new UserDao(conn);
        pDao = new PersonDao(conn);
        eDao = new EventDao(conn);
        aDao = new AuthTokenDao(conn);

        uDao.clear();
        pDao.clear();
        eDao.clear();
        aDao.clear();

        conn.commit();

        person = new Person("personID", "username", "firstName", "lastName", "m", "fatherID", "motherID", "spouseID");
        event = new Event("eventID", "username", "personID", 0, 0, "country", "city", "eventType", 0);
        user = new User("username", "password", "email", "firstName", "lastName", "m", "personID");

        persons = new Person[1];
        events = new Event[1];
        users = new User[1];

        persons[0] = person;
        events[0] = event;
        users[0] = user;

        lRequest = new LoadRequest(users, persons, events);
        lService = new LoadService();
        lService.load(lRequest);

        loginRequest = new LoginRequest("username", "password");
        loginService = new LoginService();
        loginService.login(loginRequest);
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void clearPass() throws DataAccessException {
        cRequest = new ClearRequest();
        cService = new ClearService();
        cResult = cService.clear(cRequest);
        message = cResult.getMessage();
        success = cResult.isSuccess();
        assertTrue(success);
        assertEquals("Clear succeeded.", message);
    }
}