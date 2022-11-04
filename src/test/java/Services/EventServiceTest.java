package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Request.EventRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {
    private EventService eService;
    private EventRequest eRequest;
    private Database db;
    private EventDao eDao;
    private AuthTokenDao aDao;
    private AuthToken authToken;
    private String authTokenString;
    private Event event;
    private Event event2;


    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        db = new Database();
        Connection conn = db.getConnection();
        eDao = new EventDao(conn);
        aDao = new AuthTokenDao(conn);

        eDao.clear();
        aDao.clear();

        event = new Event("eventID", "username", "personID", 0, 0, "country", "city", "eventType", 0);
        event2 = new Event("eventID2", "username", "personID", 0, 0, "country", "city", "eventType", 0);

        eDao.insert(event);
        eDao.insert(event2);

        authToken = new AuthToken("authToken", "username");
        authTokenString = authToken.getAuthToken();

        aDao.insert(authToken);
        conn.commit();
    }

    @AfterEach
    void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void getEventPass() {
        eRequest = new EventRequest(authTokenString, "eventID");
        eService = new EventService();
        assertEquals(event.getEventID(), eService.getEvent(eRequest).getEventID());
    }

    @Test
    void getEventFail() {
        eRequest = new EventRequest(authTokenString, "eventID3");
        eService = new EventService();
        assertEquals("Error: Event not found", eService.getEvent(eRequest).getMessage());
    }

    @Test
    void getEventsPass() {
        eRequest = new EventRequest(authTokenString, null);
        eService = new EventService();
        assertEquals(2, eService.getEvents(eRequest).getData().length);
    }

    @Test
    void getEventsFail(){
        eRequest = new EventRequest("authToken2", null);
        eService = new EventService();
        assertEquals("Error: Invalid auth token", eService.getEvents(eRequest).getMessage());
    }
}