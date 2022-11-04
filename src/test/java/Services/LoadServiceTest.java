package Services;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {
    private LoadService lService;
    private Database db;
    private LoadRequest lRequest;
    private LoadResult lResult;
    private User[] users;
    private Person[] persons;
    private Event[] events;
    private Event event;
    private Person person;
    private User user;
    EventDao eDao;
    PersonDao pDao;
    UserDao uDao;





    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        db = new Database();
        Connection conn = db.getConnection();

        user = new User("username", "password", "email", "firstName", "lastName", "m", "personID");
        person = new Person("personID", "username", "firstName", "lastName", "m", "fatherID", "motherID", "spouseID");
        event = new Event("eventID", "username", "personID", 0, 0, "country", "city", "eventType", 0);

        users = new User[1];
        persons = new Person[1];
        events = new Event[1];

        users[0] = user;
        persons[0] = person;
        events[0] = event;

        uDao = new UserDao(conn);
        pDao = new PersonDao(conn);
        eDao = new EventDao(conn);

        uDao.clear();
        pDao.clear();
        eDao.clear();

        conn.commit();
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void clearPass() throws DataAccessException {
        lRequest = new LoadRequest(users, persons, events);
        lService = new LoadService();
        lResult = lService.load(lRequest);
        lService.clear();
        assertNull(uDao.find(user.getUserName()));
        assertNull(pDao.find(person.getPersonID()));
        assertNull(eDao.find(event.getEventID()));
    }



    @Test
    void loadPass() {
        lRequest = new LoadRequest(users, persons, events);
        lService = new LoadService();
        lResult = lService.load(lRequest);
        assertNotNull(lResult);
        assertEquals(lResult.getMessage(), "Successfully added 1 users, 1 persons, and 1 events to the database.");
    }

    @Test
    void loadFail() {
        persons[0] = null;
        lRequest = new LoadRequest(users, persons, events);
        lService = new LoadService();
        lResult = lService.load(lRequest);
        assertEquals("Error: Request property missing or has invalid value",lResult.getMessage());

    }
}